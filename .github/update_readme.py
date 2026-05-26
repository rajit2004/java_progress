"""
update_readme.py
----------------
Counts LeetCode solutions per folder in java_progress,
fetches live stats from LeetCode GraphQL API,
then rewrites the DSA Progress table in both:
  - java_progress/README.md
  - rajit2004/rajit2004 (profile README)

Run automatically via GitHub Actions on every push.
"""

import os
import re
import json
import requests

# ── Config ────────────────────────────────────────────────────
USERNAME       = "rajit2004"
LEETCODE_URL   = "https://leetcode.com/graphql"
HEADERS        = {
    "Content-Type": "application/json",
    "Referer":      "https://leetcode.com",
    "User-Agent":   "Mozilla/5.0",
}

# Map folder names in LeetCode/ to display names in the table
FOLDER_MAP = {
    "Arrays":         "Arrays & ArrayList",
    "BinarySearch":   "Binary Search",
    "Strings":        "Strings",
    "Sorting":        "Searching & Sorting",
    "BitManipulation":"Bit Manipulation",
    "TwoPointers":    "Two Pointers",
    "Math":           "Math",
    "Misc":           "Misc",
}

# Fixed topics that don't come from LeetCode folder counts
FIXED_TOPICS = [
    ("Java Basics", "✅ Done", "4"),
]

# Topics after the LeetCode folder topics
UPCOMING_TOPICS = [
    ("Recursion",          "🔄 In Progress", "—"),
    ("Linked Lists",       "🔜 Up Next",     "—"),
    ("Trees",              "🔜 Upcoming",    "—"),
    ("Graphs",             "🔜 Upcoming",    "—"),
    ("Dynamic Programming","🔜 Upcoming",    "—"),
]

# ── Count solutions per folder ─────────────────────────────────
def count_solutions(base_path: str = ".") -> dict:
    """
    Walks JAVA/LeetCode/<folder>/ and counts .java files.
    Returns dict like {"Arrays": 34, "BinarySearch": 20, ...}
    """
    counts = {}
    lc_path = os.path.join(base_path, "JAVA", "LeetCode")
    if not os.path.isdir(lc_path):
        print(f"[!] LeetCode folder not found at {lc_path}")
        return counts

    for folder in os.listdir(lc_path):
        folder_path = os.path.join(lc_path, folder)
        if not os.path.isdir(folder_path):
            continue
        java_files = [
            f for f in os.listdir(folder_path)
            if f.endswith(".java") or (os.path.isfile(os.path.join(folder_path, f)) and "." not in f)
        ]
        counts[folder] = len(java_files)

    return counts


# ── Fetch live stats from LeetCode API ────────────────────────
def fetch_lc_stats() -> dict:
    query = """
    query($u:String!){
        matchedUser(username:$u){
            submitStats:submitStatsGlobal{
                acSubmissionNum{ difficulty count }
            }
        }
    }"""
    try:
        r = requests.post(
            LEETCODE_URL,
            json={"query": query, "variables": {"u": USERNAME}},
            headers=HEADERS,
            timeout=10,
        )
        data = r.json()
        counts = {
            d["difficulty"]: d["count"]
            for d in data["data"]["matchedUser"]["submitStats"]["acSubmissionNum"]
        }
        return {
            "total":  counts.get("All",    0),
            "easy":   counts.get("Easy",   0),
            "medium": counts.get("Medium", 0),
            "hard":   counts.get("Hard",   0),
        }
    except Exception as e:
        print(f"[!] LeetCode API error: {e}")
        return {"total": 0, "easy": 0, "medium": 0, "hard": 0}


# ── Build the DSA table markdown ──────────────────────────────
def build_table(folder_counts: dict, lc_stats: dict) -> str:
    rows = []

    # Fixed topics first
    for name, status, count in FIXED_TOPICS:
        rows.append(f"| {name} | {status} | {count} |")

    # LeetCode folder topics — only those in FOLDER_MAP, in defined order
    folder_order = ["Arrays", "BinarySearch", "Strings", "Sorting",
                    "BitManipulation", "TwoPointers", "Math", "Misc"]

    for folder in folder_order:
        if folder not in FOLDER_MAP:
            continue
        display_name = FOLDER_MAP[folder]
        count = folder_counts.get(folder, 0)
        if count > 0:
            rows.append(f"| {display_name} | ✅ Done | {count} |")

    # Upcoming topics
    for name, status, count in UPCOMING_TOPICS:
        rows.append(f"| {name} | {status} | {count} |")

    total  = lc_stats["total"]
    easy   = lc_stats["easy"]
    medium = lc_stats["medium"]
    hard   = lc_stats["hard"]

    # Milestones — auto-check based on total
    milestones = [10, 25, 50, 75, 100, 200, 300, 500]
    milestone_lines = []
    for m in milestones:
        check = "x" if total >= m else " "
        milestone_lines.append(f"- [{check}] {m} Problems Solved")

    table = "\n".join(rows)
    milestones_md = "\n".join(milestone_lines)

    return f"""## 📈 DSA Progress

| Topic | Status | Problems Solved |
|---|---|---|
{table}

**Total: {total} problems solved · Easy {easy} · Med {medium} · Hard {hard}**

---

## 🎯 DSA Milestones

{milestones_md}

---"""


# ── Replace section in README ──────────────────────────────────
def update_readme(content: str, new_section: str) -> str:
    """
    Replaces everything between ## 📈 DSA Progress
    and the --- after ## 🎯 DSA Milestones.
    """
    pattern = r"(## 📈 DSA Progress.*?---\s*)"
    replacement = new_section + "\n"
    updated, n = re.subn(pattern, replacement, content, flags=re.DOTALL)
    if n == 0:
        print("[!] Pattern not found in README — appending section instead.")
        updated = content + "\n" + new_section
    return updated


# ── Main ──────────────────────────────────────────────────────
def main():
    # Detect if running from repo root
    base = os.environ.get("GITHUB_WORKSPACE", ".")

    print("Counting solutions...")
    folder_counts = count_solutions(base)
    print(f"  Found: {folder_counts}")

    print("Fetching LeetCode stats...")
    lc_stats = fetch_lc_stats()
    print(f"  Stats: {lc_stats}")

    new_section = build_table(folder_counts, lc_stats)

    # ── Update java_progress README ───────────────────────────
    java_readme = os.path.join(base, "README.md")
    if os.path.isfile(java_readme):
        with open(java_readme, "r", encoding="utf-8") as f:
            content = f.read()
        updated = update_readme(content, new_section)
        with open(java_readme, "w", encoding="utf-8") as f:
            f.write(updated)
        print("[+] java_progress README updated.")
    else:
        print(f"[!] README not found at {java_readme}")

    # ── Write shared section for profile README ───────────────
    # The Actions workflow will use this output to update the profile repo
    output_file = os.path.join(base, ".github", "dsa_section.md")
    os.makedirs(os.path.dirname(output_file), exist_ok=True)
    with open(output_file, "w", encoding="utf-8") as f:
        f.write(new_section)
    print(f"[+] DSA section written to {output_file}")


if __name__ == "__main__":
    main()
