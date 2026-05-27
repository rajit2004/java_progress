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
    "Arrays":          "Arrays & ArrayList",
    "BinarySearch":    "Binary Search",
    "Strings":         "Strings",
    "Sorting":         "Searching & Sorting",
    "BitManipulation": "Bit Manipulation",
    "TwoPointers":     "Two Pointers",
    "Math":            "Math",
    "Misc":            "Misc",
}

# Fixed topics that don't come from LeetCode folder counts
FIXED_TOPICS = [
    ("Java Basics", "✅ Done", "4"),
]

# Topics after the LeetCode folder topics
UPCOMING_TOPICS = [
    ("Recursion",           "🔄 In Progress", "—"),
    ("Linked Lists",        "🔜 Up Next",      "—"),
    ("Trees",               "🔜 Upcoming",     "—"),
    ("Graphs",              "🔜 Upcoming",     "—"),
    ("Dynamic Programming", "🔜 Upcoming",     "—"),
]


# ── Count solutions per folder ─────────────────────────────────
def count_solutions(base_path: str = ".") -> dict:
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
        rows.append(f"| {name:<20} | {status:<14} | {count:<15} |")

    # LeetCode folder topics in defined order
    folder_order = ["Arrays", "BinarySearch", "Strings", "Sorting",
                    "BitManipulation", "TwoPointers", "Math", "Misc"]

    for folder in folder_order:
        if folder not in FOLDER_MAP:
            continue
        display_name = FOLDER_MAP[folder]
        count = folder_counts.get(folder, 0)
        if count > 0:
            rows.append(f"| {display_name:<20} | {'✅ Done':<14} | {str(count):<15} |")

    # Upcoming topics
    for name, status, count in UPCOMING_TOPICS:
        rows.append(f"| {name:<20} | {status:<14} | {count:<15} |")

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

    table         = "\n".join(rows)
    milestones_md = "\n".join(milestone_lines)

    return (
        f"## 📈 DSA Progress\n\n"
        f"| Topic                | Status         | Problems Solved |\n"
        f"| -------------------- | -------------- | --------------- |\n"
        f"{table}\n\n"
        f"**Total: {total} problems solved · Easy {easy} · Med {medium} · Hard {hard}**\n\n"
        f"---\n\n"
        f"## 🎯 DSA Milestones\n\n"
        f"{milestones_md}\n\n"
        f"---"
    )


# ── Replace section in README ──────────────────────────────────
def update_readme(content: str, new_section: str) -> str:
    """
    Replaces the ENTIRE block from ## 📈 DSA Progress
    through ## 🎯 DSA Milestones and its trailing ---
    in one atomic replacement to avoid duplicate sections.
    """
    # ── Step 1: Remove any orphaned leftover table blocks ─────
    # These look like lines starting with |---|---| that appear
    # outside a proper table context (leftover from bad replacements)
    content = re.sub(
        r"\n+\|---\|---\|.*?(?=\n## |\Z)",
        "",
        content,
        flags=re.DOTALL
    )

    # ── Step 2: Remove any orphaned ## **Total: ...** headings ─
    content = re.sub(r"\n+## \*\*Total:.*?\*\*\n*", "\n", content)

    # ── Step 3: Replace the full DSA block (Progress + Milestones)
    # Pattern captures from ## 📈 DSA Progress all the way through
    # the --- that follows ## 🎯 DSA Milestones
    pattern = (
        r"## 📈 DSA Progress"   # start anchor
        r".*?"                  # table + total (non-greedy)
        r"---"                  # separator after total
        r".*?"                  # milestones section (non-greedy)
        r"## 🎯 DSA Milestones"
        r".*?"                  # milestone checkboxes (non-greedy)
        r"---"                  # trailing separator
        r"\s*"                  # trailing whitespace
    )
    updated, n = re.subn(pattern, new_section + "\n\n", content, flags=re.DOTALL)

    if n == 0:
        # Fallback: pattern not found, try just the progress section
        print("[!] Full pattern not found — trying progress-only pattern...")
        pattern_short = r"## 📈 DSA Progress.*?---\s*"
        updated, n = re.subn(pattern_short, new_section + "\n\n", content, flags=re.DOTALL)

    if n == 0:
        print("[!] No pattern matched — appending section instead.")
        updated = content + "\n\n" + new_section

    return updated


# ── Main ──────────────────────────────────────────────────────
def main():
    base = os.environ.get("GITHUB_WORKSPACE", ".")

    print("Counting solutions...")
    folder_counts = count_solutions(base)
    print(f"  Found: {folder_counts}")

    print("Fetching LeetCode stats...")
    lc_stats = fetch_lc_stats()
    print(f"  Stats: {lc_stats}")

    new_section = build_table(folder_counts, lc_stats)
    print("\n── Generated section ──")
    print(new_section)
    print("───────────────────────\n")

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
    output_file = os.path.join(base, ".github", "dsa_section.md")
    os.makedirs(os.path.dirname(output_file), exist_ok=True)
    with open(output_file, "w", encoding="utf-8") as f:
        f.write(new_section)
    print(f"[+] DSA section written to {output_file}")


if __name__ == "__main__":
    main()