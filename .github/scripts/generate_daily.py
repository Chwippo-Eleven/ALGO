from __future__ import annotations

from pathlib import Path
import re
import sys
from typing import Iterable

ROOT_README = Path("README.md")
TARGET_ROOT = Path("v2.2") / "daily"
DEFAULT_LANGUAGES = ["java"]

LANG_EXTENSIONS = {
    "java": "java",
    "swift": "swift",
    "python": "py",
    "c++": "cpp",
    "cpp": "cpp",
    "c": "c",
    "javascript": "js",
    "typescript": "ts",
    "kotlin": "kt",
}

DAILY_BLOCK_PATTERN = re.compile(
    r"^###\s*🟨\s*(?P<title>.+?)\s*데일리\s*문제\s*$"
    r"(?P<body>[\s\S]*?)"
    r"(?=^###\s|^##\s|\Z)",
    re.MULTILINE,
)

MARKDOWN_LINK_PATTERN = re.compile(
    r"^(?:[-*]\s*)?\[(?P<title>.+)]\((?P<url>.+)\)\s*$"
)


def sanitize(name: str) -> str:
    """파일과 디렉터리 이름으로 사용할 수 없는 문자를 치환한다."""
    invalid = r'<>:"/\\|?*'
    for ch in invalid:
        name = name.replace(ch, "-")
    return re.sub(r"\s+", " ", name).strip().rstrip(".")


def extract_daily_blocks(text: str) -> list[tuple[str, str]]:
    """`### 🟨 {날짜/회차} 데일리 문제` 블록을 모두 반환한다."""
    blocks = [
        (match.group("title").strip(), match.group("body"))
        for match in DAILY_BLOCK_PATTERN.finditer(text)
    ]

    if not blocks:
        raise ValueError(
            "README에서 '### 🟨 {날짜 또는 회차} 데일리 문제' 형식의 블록을 찾을 수 없습니다."
        )

    return blocks


def extract_links(body: str) -> list[tuple[str, str]]:
    links: list[tuple[str, str]] = []

    for line in body.splitlines():
        match = MARKDOWN_LINK_PATTERN.match(line.strip())
        if not match:
            continue

        title = match.group("title").strip()
        url = match.group("url").strip()
        links.append((title, url))

    if not links:
        raise ValueError("데일리 문제 블록 안에서 문제 링크를 찾지 못했습니다.")

    return links


def detect_platform_tag(url: str) -> str:
    url_lower = url.lower()

    if "acmicpc.net" in url_lower:
        return "BOJ"
    if "programmers.co.kr" in url_lower or "school.programmers.co.kr" in url_lower:
        return "PGS"
    if "swexpertacademy.com" in url_lower:
        return "SWEA"
    if "leetcode.com" in url_lower:
        return "LTC"

    return "ETC"


def build_daily_readme(title: str, links: Iterable[tuple[str, str]]) -> str:
    lines = [f"# {title} 데일리 문제", "", "## 문제 목록", ""]
    for problem_title, problem_url in links:
        lines.append(f"- [{problem_title}]({problem_url})")
    lines.append("")
    return "\n".join(lines)


def build_problem_folder_name(problem_title: str, url: str) -> str:
    tag = detect_platform_tag(url)
    return sanitize(f"[{tag}] {problem_title}")


def extract_member_section(text: str) -> str:
    tables = re.findall(r"<table>([\s\S]*?)</table>", text, re.IGNORECASE)

    for table in tables:
        if "github.com/" in table.lower():
            return table

    raise ValueError("GitHub 링크가 포함된 스터디 멤버 테이블을 찾을 수 없습니다.")


def extract_members(member_section: str) -> list[dict[str, object]]:
    rows = re.findall(r"<tr>([\s\S]*?)</tr>", member_section, re.IGNORECASE)
    if not rows:
        raise ValueError("스터디 멤버 테이블에서 행을 찾을 수 없습니다.")

    github_cells = re.findall(
        r"<td\b[^>]*>[\s\S]*?github\.com/[\s\S]*?</td>",
        rows[0],
        re.IGNORECASE,
    )
    usernames = []
    for cell in github_cells:
        match = re.search(r"github\.com/([^\"'/<>?#]+)", cell, re.IGNORECASE)
        if match and match.group(1) not in usernames:
            usernames.append(match.group(1))

    if not usernames:
        raise ValueError("스터디 멤버 테이블에서 GitHub 사용자명을 찾을 수 없습니다.")

    language_cells: list[str] = []
    if len(rows) >= 3:
        language_cells = re.findall(
            r"<td\b[^>]*>[\s\S]*?</td>", rows[2], re.IGNORECASE
        )

    members: list[dict[str, object]] = []
    for index, username in enumerate(usernames):
        languages = []
        if index < len(language_cells):
            languages = extract_languages_from_cell(language_cells[index])

        if not languages:
            languages = DEFAULT_LANGUAGES.copy()
            print(
                f"경고: {username}의 언어 정보가 없어 기본값 "
                f"{', '.join(DEFAULT_LANGUAGES)}를 사용합니다."
            )

        members.append({"username": username, "languages": languages})

    return members


def extract_languages_from_cell(cell_html: str) -> list[str]:
    languages: list[str] = []

    data_match = re.search(
        r"data-languages?=[\"']([^\"']+)[\"']", cell_html, re.IGNORECASE
    )
    if data_match:
        candidates = re.split(r"[,\s]+", data_match.group(1))
        for candidate in candidates:
            language = normalize_language_name(candidate)
            if language and language not in languages:
                languages.append(language)

    badge_matches = re.findall(r"badge/([^?\-\s]+)-", cell_html, re.IGNORECASE)
    for badge in badge_matches:
        language = normalize_language_name(badge)
        if language and language not in languages:
            languages.append(language)

    text = re.sub(r"<[^>]+>", " ", cell_html)
    for candidate in re.split(r"[,/\s]+", text):
        language = normalize_language_name(candidate)
        if language and language not in languages:
            languages.append(language)

    return languages


def normalize_language_name(raw: str) -> str | None:
    raw_lower = raw.strip().lower()

    mapping = {
        "java": "java",
        "swift": "swift",
        "python": "python",
        "py": "python",
        "c++": "c++",
        "c%2b%2b": "c++",
        "cpp": "c++",
        "kotlin": "kotlin",
        "javascript": "javascript",
        "js": "javascript",
        "typescript": "typescript",
        "ts": "typescript",
        "c": "c",
    }

    return mapping.get(raw_lower)


def write_if_changed(path: Path, content: str) -> bool:
    path.parent.mkdir(parents=True, exist_ok=True)
    if path.exists() and path.read_text(encoding="utf-8") == content:
        return False
    path.write_text(content, encoding="utf-8")
    return True


def ensure_file(path: Path, content: str = "") -> bool:
    path.parent.mkdir(parents=True, exist_ok=True)
    if path.exists():
        return False
    path.write_text(content, encoding="utf-8")
    return True


def build_problem_readme(problem_folder_name: str, problem_url: str) -> str:
    return f"# {problem_folder_name}\n\n- 문제 링크: {problem_url}\n"


def build_code_template(username: str, language: str) -> str:
    if language == "java":
        return (
            "import java.io.*;\n"
            "import java.util.*;\n\n"
            "class Main {\n"
            "    public static void main(String[] args) throws Exception {\n"
            "        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));\n"
            "    }\n"
            "}\n"
        )

    if language == "swift":
        return f"import Foundation\n\n// {username}\n"

    if language == "python":
        return f"# {username}\n"

    if language == "c++":
        return (
            "#include <bits/stdc++.h>\n"
            "using namespace std;\n\n"
            "int main() {\n"
            "    ios::sync_with_stdio(false);\n"
            "    cin.tie(nullptr);\n"
            "    return 0;\n"
            "}\n"
        )

    if language == "c":
        return (
            "#include <stdio.h>\n\n"
            "int main(void) {\n"
            "    return 0;\n"
            "}\n"
        )

    if language == "kotlin":
        return "fun main() {\n}\n"

    if language in {"javascript", "typescript"}:
        return f"// {username}\n"

    return ""


def generate_daily_folder(
    daily_title: str,
    links: list[tuple[str, str]],
    members: list[dict[str, object]],
) -> None:
    safe_title = sanitize(daily_title)
    if not safe_title:
        raise ValueError("데일리 문제 제목이 비어 있습니다.")

    daily_dir = TARGET_ROOT / safe_title
    daily_dir.mkdir(parents=True, exist_ok=True)

    daily_readme = daily_dir / "README.md"
    if write_if_changed(daily_readme, build_daily_readme(daily_title, links)):
        print(f"데일리 README 생성/업데이트: {daily_readme}")

    for problem_title, problem_url in links:
        problem_folder_name = build_problem_folder_name(problem_title, problem_url)
        problem_dir = daily_dir / problem_folder_name
        problem_dir.mkdir(parents=True, exist_ok=True)

        problem_readme = problem_dir / "README.md"
        if write_if_changed(
            problem_readme,
            build_problem_readme(problem_folder_name, problem_url),
        ):
            print(f"문제 README 생성/업데이트: {problem_readme}")

        for member in members:
            username = str(member["username"])
            for language in member["languages"]:
                normalized_language = str(language)
                extension = LANG_EXTENSIONS.get(normalized_language)
                if not extension:
                    print(f"지원하지 않는 언어 스킵: {username} - {normalized_language}")
                    continue

                code_file = problem_dir / f"{username}.{extension}"
                if ensure_file(
                    code_file,
                    build_code_template(username, normalized_language),
                ):
                    print(f"코드 템플릿 생성: {code_file}")
                else:
                    print(f"코드 템플릿 이미 존재: {code_file}")


def main() -> None:
    if not ROOT_README.exists():
        print("루트 README.md가 없습니다.")
        sys.exit(1)

    try:
        text = ROOT_README.read_text(encoding="utf-8")
        daily_blocks = extract_daily_blocks(text)
        member_section = extract_member_section(text)
        members = extract_members(member_section)

        print(f"daily_blocks_count: {len(daily_blocks)}")
        print("members:")
        for member in members:
            print(f"  - {member['username']}: {member['languages']}")

        for daily_title, daily_body in daily_blocks:
            links = extract_links(daily_body)
            print(f"daily_title: {daily_title}")
            print(f"links_count: {len(links)}")
            generate_daily_folder(daily_title, links, members)
    except ValueError as error:
        print(f"생성 실패: {error}")
        sys.exit(1)


if __name__ == "__main__":
    main()
