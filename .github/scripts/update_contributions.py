"""
update_contributions.py
-----------------------
Fetches all merged PRs by rajit2004 on external repos
and rewrites the README.md between marker comments.

Runs via GitHub Actions every 6 hours.
Requires: GITHUB_TOKEN env variable
"""

import os
import re
import requests
from datetime import datetime
from collections import defaultdict

USERNAME = "rajit2004"
README   = "README.md"
TOKEN    = os.environ.get("GITHUB_TOKEN", "")

HEADERS = {
    "Authorization": f"Bearer {TOKEN}",
    "Accept": "application/vnd.github+json",
    "X-GitHub-Api-Version": "2022-11-28",
}

# Own repos to exclude
OWN_REPOS = {
    f"{USERNAME}/java_progress", f"{USERNAME}/rajit2004",
    f"{USERNAME}/InnerCircle", f"{USERNAME}/DeepSeekWidget",
    f"{USERNAME}/LeetCode-Tracker", f"{USERNAME}/PARKING-MANAGEMENT-SYSTEM-Enhanced-v2.0",
    f"{USERNAME}/animal-disease-predictor", f"{USERNAME}/student-performance-prediction",
    f"{USERNAME}/yt-analytics-tracker", f"{USERNAME}/ModedRepo_ParkingSystem",
    f"{USERNAME}/postgresql-mastery", f"{USERNAME}/hello_devs",
    f"{USERNAME}/local_to_remote", f"{USERNAME}/.github",
    f"{USERNAME}/Rhythma", f"{USERNAME}/ss_ai", f"{USERNAME}/linkid",
    f"{USERNAME}/ChatApp", f"{USERNAME}/Memori",
    f"{USERNAME}/awesome-deepseek-integration", f"{USERNAME}/github-readme-stats",
    f"{USERNAME}/open-source-contributions",
}

# Repo descriptions (manually curated — fallback to empty)
REPO_DESC = {
    "ishita2740/Rhythma":          "Flutter music app — backend, auth, CI/CD (ECSoC26)",
    "vishnukothakapu/linkid":      "Link-in-bio platform — security, DB indexes (ECSoC26)",
    "madhav2348/ss_ai":            "AI-powered screenshot tool — OCR worker (SSoC26)",
    "deepseek-ai/awesome-deepseek-integration": "Official DeepSeek integrations list",
    "rhoopphiuchi/Java_Enlightment":"Java learning repo — README + file contributions",
    "Rishav123918/Parking_Application_C-":      "C++ parking system — forked and extended",
    "AMANkumar0004/ChatApp":       "Full-stack real-time chat app — README contribution",
}


def fetch_all_prs() -> list[dict]:
    """Fetch all merged PRs by the user via GitHub Search API."""
    all_prs = []
    page = 1
    while True:
        r = requests.get(
            "https://api.github.com/search/issues",
            headers=HEADERS,
            params={
                "q": f"type:pr author:{USERNAME} is:merged is:closed",
                "sort": "updated",
                "order": "desc",
                "per_page": 100,
                "page": page,
            },
            timeout=15,
        )
        r.raise_for_status()
        data = r.json()
        items = data.get("items", [])
        if not items:
            break
        for pr in items:
            repo = pr["repository_url"].replace("https://api.github.com/repos/", "")
            if repo not in OWN_REPOS:
                pr["_repo"] = repo
                all_prs.append(pr)
        if len(all_prs) >= data.get("total_count", 0) or not items:
            break
        page += 1
    return all_prs


def get_labels(pr: dict) -> str:
    """Format PR labels as inline badges."""
    labels = [l["name"] for l in pr.get("labels", [])]
    if not labels:
        return "—"
    return " ".join(f"`{l}`" for l in labels[:4])


def build_stats_section(prs: list[dict]) -> str:
    repos = {pr["_repo"] for pr in prs}
    updated = datetime.utcnow().strftime("%d %b %Y, %H:%M UTC")
    return f"""<!-- STATS_START -->
## 📊 Stats

| Metric | Count |
|--------|-------|
| Repositories Contributed To | {len(repos)} |
| Total PRs Merged | {len(prs)} |
| Last Updated | {updated} |
<!-- STATS_END -->"""


def build_contributions_section(prs: list[dict]) -> str:
    """Build per-repo summary table."""
    by_repo = defaultdict(list)
    for pr in prs:
        by_repo[pr["_repo"]].append(pr)

    rows = []
    for repo in sorted(by_repo.keys()):
        repo_prs  = by_repo[repo]
        count     = len(repo_prs)
        desc      = REPO_DESC.get(repo, "—")
        # All unique labels across PRs
        all_labels = []
        for p in repo_prs:
            for l in p.get("labels", []):
                if l["name"] not in all_labels:
                    all_labels.append(l["name"])
        label_str = " ".join(f"`{l}`" for l in all_labels[:5]) or "—"
        # Most recent PR
        latest = sorted(repo_prs, key=lambda p: p.get("closed_at") or "", reverse=True)[0]
        latest_title = latest["title"][:55] + ("…" if len(latest["title"]) > 55 else "")
        latest_date  = (latest.get("closed_at") or "")[:10]
        repo_url = f"https://github.com/{repo}"
        rows.append(
            f"| [{repo}]({repo_url}) | {desc} | {count} | {label_str} | [{latest_title}]({latest['html_url']}) | {latest_date} |"
        )

    table = "\n".join(rows)
    return f"""<!-- CONTRIBUTIONS_START -->
## 📋 Contributions

| Repository | Description | PRs Merged | Labels | Latest PR | Date |
|------------|-------------|:----------:|--------|-----------|------|
{table}
<!-- CONTRIBUTIONS_END -->"""


def build_pr_list_section(prs: list[dict]) -> str:
    """Build full chronological PR list."""
    rows = []
    for i, pr in enumerate(prs, 1):
        repo     = pr["_repo"]
        title    = pr["title"][:65] + ("…" if len(pr["title"]) > 65 else "")
        labels   = get_labels(pr)
        date     = (pr.get("closed_at") or "")[:10]
        repo_url = f"https://github.com/{repo}"
        rows.append(
            f"| {i} | [{repo}]({repo_url}) | [{title}]({pr['html_url']}) | {labels} | {date} |"
        )

    table = "\n".join(rows)
    return f"""<!-- PR_LIST_START -->
## 🔀 All Merged PRs

| # | Repository | PR Title | Labels | Date |
|---|------------|----------|--------|------|
{table}
<!-- PR_LIST_END -->"""


def replace_section(content: str, new_section: str, start: str, end: str) -> str:
    pattern = rf"{re.escape(start)}.*?{re.escape(end)}"
    if re.search(pattern, content, flags=re.DOTALL):
        return re.sub(pattern, new_section, content, flags=re.DOTALL)
    return content


def main():
    print("Fetching merged PRs...")
    prs = fetch_all_prs()
    print(f"  Found {len(prs)} merged PRs on external repos.")

    with open(README, "r", encoding="utf-8") as f:
        content = f.read()

    content = replace_section(content, build_stats_section(prs),         "<!-- STATS_START -->",         "<!-- STATS_END -->")
    content = replace_section(content, build_contributions_section(prs),  "<!-- CONTRIBUTIONS_START -->", "<!-- CONTRIBUTIONS_END -->")
    content = replace_section(content, build_pr_list_section(prs),        "<!-- PR_LIST_START -->",       "<!-- PR_LIST_END -->")

    with open(README, "w", encoding="utf-8") as f:
        f.write(content)

    print(f"[+] README updated — {len(prs)} PRs across {len({p['_repo'] for p in prs})} repos.")


if __name__ == "__main__":
    main()
