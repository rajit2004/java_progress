"""
update_problems.py
------------------
Scans JAVA/LeetCode/ for solved problems, fetches
title + difficulty from LeetCode GraphQL API, gets
commit date from git log, then writes PROBLEMS.md.

Place in: java_progress/.github/scripts/
"""

import os
import re
import json
import subprocess
import requests
from datetime import datetime

README_PATH   = "README.md"
PROBLEMS_PATH = "PROBLEMS.md"
LC_BASE = "src/LeetCode"

HEADERS = {
    "Content-Type": "application/json",
    "Referer": "https://leetcode.com",
    "User-Agent": "Mozilla/5.0",
}

DIFFICULTY_EMOJI = {"Easy": "🟢", "Medium": "🟡", "Hard": "🔴"}


def extract_problem_number(filename: str) -> int | None:
    """Extract LeetCode problem number from filename."""
    # Patterns: LeetCode_1_TwoSum.java, LeetCode_704.java, LeetCode_33x5.java
    m = re.search(r"LeetCode[_\-](\d+)", filename, re.IGNORECASE)
    return int(m.group(1)) if m else None


def scan_problems(base_path: str) -> dict[int, dict]:
    """
    Walk JAVA/LeetCode/ and collect unique problem numbers.
    Returns {number: {folder, files, first_file_path}}
    """
    problems: dict[int, dict] = {}
    if not os.path.isdir(base_path):
        print(f"[!] {base_path} not found.")
        return problems

    for folder in os.listdir(base_path):
        folder_path = os.path.join(base_path, folder)
        if not os.path.isdir(folder_path):
            continue
        for fname in os.listdir(folder_path):
            if not fname.endswith(".java"):
                continue
            num = extract_problem_number(fname)
            if num is None:
                continue
            fpath = os.path.join(folder_path, fname)
            if num not in problems:
                problems[num] = {
                    "folder": folder,
                    "file_path": fpath,
                    "file_name": fname,
                }
    return problems


def get_commit_date(file_path: str) -> str:
    """Get date of first commit for a file via git log."""
    try:
        result = subprocess.run(
            ["git", "log", "--follow", "--format=%as", "--", file_path],
            capture_output=True, text=True, timeout=10
        )
        lines = [l.strip() for l in result.stdout.strip().splitlines() if l.strip()]
        # Last line = oldest commit
        return lines[-1] if lines else "—"
    except Exception:
        return "—"


def fetch_problem_details(number: int) -> dict:
    """Fetch problem title and difficulty from LeetCode GraphQL."""
    query = """
    query($titleSlug: String!) {
        question(titleSlug: $titleSlug) {
            title
            difficulty
            titleSlug
        }
    }"""
    # First get titleSlug from problem number
    list_query = """
    query problemsetQuestionList($skip: Int!, $limit: Int!) {
        problemsetQuestionList: questionList(
            categorySlug: ""
            limit: $limit
            skip: $skip
            filters: {}
        ) {
            questions: data {
                questionFrontendId
                title
                titleSlug
                difficulty
            }
        }
    }"""
    try:
        # Use the direct question endpoint by frontendId
        r = requests.post(
            "https://leetcode.com/graphql",
            json={
                "query": """
                query($num: Int!) {
                    question: problemByFrontendId(id: $num) {
                        title difficulty titleSlug
                    }
                }""",
                "variables": {"num": number}
            },
            headers=HEADERS,
            timeout=8,
        )
        data = r.json().get("data", {}).get("question")
        if data:
            return data
    except Exception:
        pass

    # Fallback: try title_slug from the number directly
    return {"title": f"Problem {number}", "difficulty": "Unknown", "titleSlug": str(number)}


def fetch_all_details(problem_numbers: list[int]) -> dict[int, dict]:
    """Batch-fetch problem details. Uses a single API call for the full list."""
    details: dict[int, dict] = {}
    print(f"  Fetching details for {len(problem_numbers)} problems...")

    # LeetCode GraphQL — get all problems in one call
    try:
        r = requests.post(
            "https://leetcode.com/graphql",
            json={
                "query": """
                query {
                    allQuestions {
                        questionFrontendId
                        title
                        titleSlug
                        difficulty
                    }
                }"""
            },
            headers=HEADERS,
            timeout=20,
        )
        all_q = r.json().get("data", {}).get("allQuestions", [])
        for q in all_q:
            try:
                num = int(q["questionFrontendId"])
                details[num] = {
                    "title":      q["title"],
                    "difficulty": q["difficulty"],
                    "titleSlug":  q["titleSlug"],
                }
            except (ValueError, KeyError):
                continue
        print(f"  Fetched {len(details)} problems from LeetCode API.")
    except Exception as e:
        print(f"  [!] Batch fetch failed: {e}. Using fallbacks.")

    # Fill missing with individual fetch
    missing = [n for n in problem_numbers if n not in details]
    for num in missing:
        details[num] = fetch_problem_details(num)

    return details


def build_problems_md(
    problems: dict[int, dict],
    details: dict[int, dict],
    dates: dict[int, str],
) -> str:
    """Build the full PROBLEMS.md content."""
    updated = datetime.utcnow().strftime("%d %b %Y, %H:%M UTC")
    total   = len(problems)
    easy    = sum(1 for n in problems if details.get(n, {}).get("difficulty") == "Easy")
    medium  = sum(1 for n in problems if details.get(n, {}).get("difficulty") == "Medium")
    hard    = sum(1 for n in problems if details.get(n, {}).get("difficulty") == "Hard")

    rows = []
    for num in sorted(problems.keys()):
        d         = details.get(num, {})
        title     = d.get("title", f"Problem {num}")
        diff      = d.get("difficulty", "Unknown")
        slug      = d.get("titleSlug", str(num))
        folder    = problems[num]["folder"]
        date      = dates.get(num, "—")
        emoji     = DIFFICULTY_EMOJI.get(diff, "⚪")
        lc_url    = f"https://leetcode.com/problems/{slug}/"
        rows.append(f"| {num} | [{title}]({lc_url}) | {emoji} {diff} | `{folder}` | {date} |")

    table = "\n".join(rows)

    return f"""# 🧠 LeetCode Solutions

> All **{total}** problems solved in Java — auto-updated on every push.
> Last updated: {updated}

## 📊 Summary

| 🟢 Easy | 🟡 Medium | 🔴 Hard | Total |
|:-------:|:---------:|:-------:|:-----:|
| {easy} | {medium} | {hard} | {total} |

---

## 📋 Problem List

| # | Problem | Difficulty | Topic | Date Solved |
|---|---------|------------|-------|-------------|
{table}

---

> Auto-generated by [GitHub Actions](../.github/workflows/update_problems.yml)
> Source: [java_progress/JAVA/LeetCode](JAVA/LeetCode/)
"""


def update_readme_link():
    """Add a link to PROBLEMS.md in README if not already there."""
    if not os.path.isfile(README_PATH):
        return
    with open(README_PATH, "r", encoding="utf-8") as f:
        content = f.read()
    marker = "📋 [View All Solved Problems](PROBLEMS.md)"
    if marker not in content:
        # Insert after the DSA progress header
        content = content.replace(
            "## 📈 DSA Progress",
            f"## 📈 DSA Progress\n\n> {marker}\n"
        )
        with open(README_PATH, "w", encoding="utf-8") as f:
            f.write(content)
        print("[+] README link added.")


def main():
    print("Scanning JAVA/LeetCode/...")
    problems = scan_problems(LC_BASE)
    print(f"  Found {len(problems)} unique problems.")

    numbers = sorted(problems.keys())
    details = fetch_all_details(numbers)

    print("Getting commit dates...")
    dates: dict[int, str] = {}
    for num, info in problems.items():
        dates[num] = get_commit_date(info["file_path"])

    md = build_problems_md(problems, details, dates)

    with open(PROBLEMS_PATH, "w", encoding="utf-8") as f:
        f.write(md)
    print(f"[+] {PROBLEMS_PATH} written — {len(problems)} problems.")

    update_readme_link()


if __name__ == "__main__":
    main()
