from __future__ import annotations

from datetime import date, datetime, timedelta
from pathlib import Path
from typing import Iterable
from urllib.parse import urlsplit, urlunsplit
from zoneinfo import ZoneInfo

import json
import os
import re
import sys


ROOT_README = Path("README.md")
TARGET_ROOT = Path("problem_solve")
HISTORY_FILE = TARGET_ROOT / "PROBLEM_HISTORY.md"
DUTY_CONFIG_FILE = Path("problem-duty.json")

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
    r"^(?:[-*]\s*)?"
    r"\[(?P<title>[^\]]+)\]"
    r"\((?P<url>[^)]+)\)\s*$"
)

MARKDOWN_LINK_FIND_PATTERN = re.compile(
    r"\[(?P<title>[^\]]+)\]"
    r"\((?P<url>https?://[^)\s]+)\)"
)

DAILY_DATE_PATTERN = re.compile(
    r"^(?P<month>\d{2})-(?P<day>\d{2})$"
)

MONTH_FOLDER_PATTERN = re.compile(
    r"^(?P<year>\d{4})-(?P<month>\d{2})$"
)

KST = ZoneInfo("Asia/Seoul")


def sanitize(name: str) -> str:
    invalid = r'<>:"/\\|?*'
    for character in invalid:
        name = name.replace(character, "-")
    name = re.sub(r"\s+", " ", name).strip()
    return name.rstrip(".")


def normalize_problem_url(url: str) -> str:
    """URL 끝의 슬래시, fragment, http/https 차이를 정규화한다."""
    url = url.strip()
    parsed = urlsplit(url)

    if not parsed.netloc:
        return url.rstrip("/")

    path = parsed.path.rstrip("/") or "/"
    return urlunsplit(
        (
            "https",
            parsed.netloc.lower(),
            path,
            parsed.query,
            "",
        )
    )


def extract_daily_blocks(text: str) -> list[tuple[str, str]]:
    blocks = [
        (
            match.group("title").strip(),
            match.group("body"),
        )
        for match in DAILY_BLOCK_PATTERN.finditer(text)
    ]

    if not blocks:
        raise ValueError(
            "README에서 '### 🟨 {날짜} 데일리 문제' 형식의 블록을 찾지 못했습니다."
        )

    return blocks


def extract_links(body: str) -> list[tuple[str, str]]:
    links: list[tuple[str, str]] = []

    for line in body.splitlines():
        match = MARKDOWN_LINK_PATTERN.match(line.strip())
        if match:
            links.append(
                (
                    match.group("title").strip(),
                    match.group("url").strip(),
                )
            )

    if not links:
        raise ValueError(
            "데일리 문제 블록에서 문제 링크를 찾지 못했습니다."
        )

    return links


def extract_all_links(text: str) -> list[tuple[str, str]]:
    return [
        (
            match.group("title").strip(),
            match.group("url").strip(),
        )
        for match in MARKDOWN_LINK_FIND_PATTERN.finditer(text)
    ]


def detect_platform_tag(url: str) -> str:
    lower_url = url.lower()

    if "acmicpc.net" in lower_url:
        return "BOJ"
    if "programmers.co.kr" in lower_url:
        return "PGS"
    if "swexpertacademy.com" in lower_url:
        return "SWEA"
    if "leetcode.com" in lower_url:
        return "LTC"

    return "ETC"


def build_problem_folder_name(
    problem_title: str,
    problem_url: str,
) -> str:
    return sanitize(
        f"[{detect_platform_tag(problem_url)}] {problem_title}"
    )


def build_daily_readme(
    title: str,
    links: Iterable[tuple[str, str]],
) -> str:
    lines = [
        f"# {title} 데일리 문제",
        "",
        "## 문제 목록",
        "",
    ]

    for problem_title, problem_url in links:
        lines.append(f"- [{problem_title}]({problem_url})")

    lines.append("")
    return "\n".join(lines)


def build_problem_readme(
    problem_folder_name: str,
    problem_url: str,
) -> str:
    return (
        f"# {problem_folder_name}\n\n"
        f"- 문제 링크: {problem_url}\n"
    )


def resolve_daily_date(
    daily_title: str,
    today: date | None = None,
) -> date:
    """
    MM-DD를 실제 날짜로 변환한다.
    연초에 12월 날짜가 입력된 경우 전년도 날짜로 판단한다.
    """
    current_date = today or datetime.now(KST).date()
    safe_title = sanitize(daily_title)
    match = DAILY_DATE_PATTERN.fullmatch(safe_title)

    if not match:
        raise ValueError(
            f"데일리 문제 날짜는 MM-DD 형식이어야 합니다: {daily_title}"
        )

    month = int(match.group("month"))
    day = int(match.group("day"))

    try:
        target_date = date(current_date.year, month, day)
    except ValueError as error:
        raise ValueError(
            f"올바르지 않은 데일리 날짜입니다: {daily_title}"
        ) from error

    if target_date > current_date + timedelta(days=45):
        target_date = date(current_date.year - 1, month, day)

    return target_date


def validate_daily_dates(
    blocks: list[tuple[str, str]],
) -> None:
    today = datetime.now(KST).date()

    for daily_title, _ in blocks:
        target_date = resolve_daily_date(daily_title, today)

        if target_date < today:
            raise ValueError(
                "과거 날짜에는 데일리 문제를 생성할 수 없습니다. "
                f"입력 날짜: {daily_title}, 오늘: {today:%m-%d}"
            )


def validate_daily_titles(
    blocks: list[tuple[str, str]],
) -> None:
    seen: set[str] = set()

    for daily_title, _ in blocks:
        safe_title = sanitize(daily_title)

        if safe_title in seen:
            raise ValueError(
                f"README에 동일한 데일리 제목이 두 번 존재합니다: {daily_title}"
            )

        seen.add(safe_title)


def daily_date_from_path(daily_readme: Path) -> date:
    """
    기존 problem_solve/MM-DD와 신규 problem_solve/YYYY-MM/MM-DD
    구조를 모두 지원한다.
    """
    daily_title = daily_readme.parent.name
    month_folder = daily_readme.parent.parent.name

    if daily_readme.parent.parent == TARGET_ROOT:
        # 기존 폴더에는 연도 정보가 없으므로 현재 날짜 규칙으로 추정한다.
        return resolve_daily_date(daily_title)

    month_match = MONTH_FOLDER_PATTERN.fullmatch(month_folder)
    if not month_match:
        raise ValueError(
            f"월별 폴더명이 YYYY-MM 형식이 아닙니다: {month_folder}"
        )

    daily_match = DAILY_DATE_PATTERN.fullmatch(daily_title)
    if not daily_match:
        raise ValueError(
            f"데일리 폴더명이 MM-DD 형식이 아닙니다: {daily_title}"
        )

    year = int(month_match.group("year"))
    month = int(month_match.group("month"))
    day = int(daily_match.group("day"))

    if month != int(daily_match.group("month")):
        raise ValueError(
            f"월별 폴더와 데일리 날짜가 일치하지 않습니다: "
            f"{month_folder}/{daily_title}"
        )

    try:
        return date(year, month, day)
    except ValueError as error:
        raise ValueError(
            f"올바르지 않은 날짜 폴더입니다: {month_folder}/{daily_title}"
        ) from error


def iter_daily_readmes() -> list[Path]:
    """
    기존 1단계 날짜 폴더와 신규 2단계 월/날짜 폴더의
    데일리 README만 수집한다. 문제 폴더 내부 README는 읽지 않는다.
    """
    if not TARGET_ROOT.exists():
        return []

    daily_readmes = set(TARGET_ROOT.glob("*/README.md"))
    daily_readmes.update(TARGET_ROOT.glob("*/*/README.md"))

    return sorted(daily_readmes)


def collect_problem_history() -> dict[str, list[dict[str, str]]]:
    history: dict[str, list[dict[str, str]]] = {}
    seen_records: set[tuple[str, str]] = set()

    for daily_readme in iter_daily_readmes():
        target_date = daily_date_from_path(daily_readme)
        date_key = target_date.isoformat()
        text = daily_readme.read_text(encoding="utf-8")

        for problem_title, problem_url in extract_all_links(text):
            normalized_url = normalize_problem_url(problem_url)
            record_key = (date_key, normalized_url)

            # 구형/신형 폴더에 동일 날짜의 기록이 함께 있어도 중복 집계하지 않는다.
            if record_key in seen_records:
                continue

            seen_records.add(record_key)
            history.setdefault(normalized_url, []).append(
                {
                    "date": date_key,
                    "title": problem_title,
                    "url": problem_url,
                }
            )

    return history


def find_duplicate_problems(
    blocks: list[tuple[str, str]],
    history: dict[str, list[dict[str, str]]],
) -> list[dict[str, str]]:
    duplicates: list[dict[str, str]] = []
    current_urls: dict[str, dict[str, str]] = {}

    for daily_title, daily_body in blocks:
        current_date = resolve_daily_date(daily_title).isoformat()

        for problem_title, problem_url in extract_links(daily_body):
            normalized_url = normalize_problem_url(problem_url)

            for previous in history.get(normalized_url, []):
                if previous["date"] == current_date:
                    continue

                duplicates.append(
                    {
                        "title": problem_title,
                        "url": problem_url,
                        "current_date": current_date,
                        "previous_date": previous["date"],
                        "previous_title": previous["title"],
                    }
                )

            current_previous = current_urls.get(normalized_url)
            if current_previous:
                duplicates.append(
                    {
                        "title": problem_title,
                        "url": problem_url,
                        "current_date": current_date,
                        "previous_date": current_previous["date"],
                        "previous_title": current_previous["title"],
                    }
                )
            else:
                current_urls[normalized_url] = {
                    "date": current_date,
                    "title": problem_title,
                }

    return remove_duplicate_reports(duplicates)


def remove_duplicate_reports(
    duplicates: list[dict[str, str]],
) -> list[dict[str, str]]:
    result: list[dict[str, str]] = []
    seen: set[tuple[str, str, str]] = set()

    for duplicate in duplicates:
        key = (
            normalize_problem_url(duplicate["url"]),
            duplicate["current_date"],
            duplicate["previous_date"],
        )

        if key in seen:
            continue

        seen.add(key)
        result.append(duplicate)

    return result


def escape_github_annotation(text: str) -> str:
    return (
        text.replace("%", "%25")
        .replace("\r", "%0D")
        .replace("\n", "%0A")
    )


def markdown_escape(text: str) -> str:
    return text.replace("|", "\\|")


def write_duplicate_summary(
    duplicates: list[dict[str, str]],
) -> None:
    summary_path = os.getenv("GITHUB_STEP_SUMMARY")
    if not summary_path:
        return

    lines = [
        "# ❌ 중복 문제 발견",
        "",
        "이전에 푼 적 있는 문제입니다. **다른 문제로 부탁드립니다!**",
        "",
        "| 문제 | 현재 데일리 | 이전 풀이 | URL |",
        "|---|---|---|---|",
    ]

    for duplicate in duplicates:
        lines.append(
            "| "
            + markdown_escape(duplicate["title"])
            + " | "
            + markdown_escape(duplicate["current_date"])
            + " | "
            + markdown_escape(duplicate["previous_date"])
            + " | "
            + markdown_escape(duplicate["url"])
            + " |"
        )

    lines.append("")
    with open(summary_path, "a", encoding="utf-8") as summary:
        summary.write("\n".join(lines))


def report_duplicates(
    duplicates: list[dict[str, str]],
) -> None:
    print("\n" + "=" * 70)
    print("❌ 중복 문제가 발견되었습니다.")
    print("=" * 70)

    for duplicate in duplicates:
        print()
        print(f"문제: {duplicate['title']}")
        print(f"URL: {duplicate['url']}")
        print(f"현재 데일리: {duplicate['current_date']}")
        print(f"이전 풀이: {duplicate['previous_date']}")
        print("이전에 푼 적 있는 문제입니다. 다른 문제로 부탁드립니다!")

        annotation_message = (
            f"{duplicate['title']} | URL: {duplicate['url']} | "
            f"이전 풀이: {duplicate['previous_date']} | "
            "이전에 푼 적 있는 문제입니다. 다른 문제로 부탁드립니다!"
        )
        print(
            "::error title=중복 문제 발견::"
            + escape_github_annotation(annotation_message)
        )

    print("\n" + "=" * 70)
    write_duplicate_summary(duplicates)


def build_history_markdown() -> str:
    rows: list[tuple[str, str, str, str]] = []
    seen_records: set[tuple[str, str]] = set()

    for daily_readme in iter_daily_readmes():
        target_date = daily_date_from_path(daily_readme)
        date_key = target_date.isoformat()
        text = daily_readme.read_text(encoding="utf-8")

        for problem_title, problem_url in extract_all_links(text):
            record_key = (
                date_key,
                normalize_problem_url(problem_url),
            )
            if record_key in seen_records:
                continue

            seen_records.add(record_key)
            rows.append(
                (
                    date_key,
                    detect_platform_tag(problem_url),
                    problem_title,
                    problem_url,
                )
            )

    rows.sort(key=lambda row: (row[0], row[2]))

    lines = [
        "# 문제 풀이 이력",
        "",
        "> 이 파일은 GitHub Actions가 자동으로 생성합니다.",
        "> 문제 중복 여부는 URL 기준으로 판단합니다.",
        "",
        f"총 문제 수: **{len(rows)}**",
        "",
        "| 날짜 | 플랫폼 | 문제 | URL |",
        "|---|---|---|---|",
    ]

    for daily_date, platform, problem_title, problem_url in rows:
        lines.append(
            "| "
            + markdown_escape(daily_date)
            + " | "
            + markdown_escape(platform)
            + " | "
            + markdown_escape(problem_title)
            + " | "
            + markdown_escape(problem_url)
            + " |"
        )

    lines.append("")
    return "\n".join(lines)


def write_if_changed(path: Path, content: str) -> bool:
    path.parent.mkdir(parents=True, exist_ok=True)

    if path.exists():
        old_content = path.read_text(encoding="utf-8")
        if old_content == content:
            return False

    path.write_text(content, encoding="utf-8")
    return True


def ensure_file(path: Path, content: str = "") -> bool:
    path.parent.mkdir(parents=True, exist_ok=True)

    if path.exists():
        return False

    path.write_text(content, encoding="utf-8")
    return True


def extract_member_section(text: str) -> str:
    tables = re.findall(
        r"<table\b[^>]*>([\s\S]*?)</table>",
        text,
        re.IGNORECASE,
    )

    for table in tables:
        if "github.com/" in table.lower():
            return table

    raise ValueError(
        "README에서 GitHub 링크가 포함된 스터디 멤버 테이블을 찾지 못했습니다."
    )


def extract_languages_from_cell(cell_html: str) -> list[str]:
    languages: list[str] = []
    badge_matches = re.findall(
        r"badge/([^?/\-\s]+)-",
        cell_html,
        re.IGNORECASE,
    )

    for badge in badge_matches:
        language = normalize_language_name(badge)
        if language and language not in languages:
            languages.append(language)

    return languages


def normalize_language_name(raw: str) -> str | None:
    normalized = raw.strip().lower()

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

    return mapping.get(normalized)


def find_language_cells(member_section: str) -> list[str]:
    """
    README 멤버 테이블의 언어 행을 모두 수집한다.
    멤버가 5명씩 여러 행으로 나뉘어 있어도 언어 정보가 누락되지 않는다.
    """
    language_cells: list[str] = []
    rows = re.findall(
        r"<tr\b[^>]*>([\s\S]*?)</tr>",
        member_section,
        re.IGNORECASE,
    )

    for row in rows:
        cells = re.findall(
            r"<td\b[^>]*>[\s\S]*?</td>",
            row,
            re.IGNORECASE,
        )

        if cells and any(
            extract_languages_from_cell(cell)
            for cell in cells
        ):
            language_cells.extend(cells)

    return language_cells


def extract_members(
    member_section: str,
) -> list[dict[str, object]]:
    cells = re.findall(
        r"<td\b[^>]*>[\s\S]*?</td>",
        member_section,
        re.IGNORECASE,
    )

    usernames: list[str] = []

    for cell in cells:
        match = re.search(
            r"github\.com/([^\"'/<>?#]+)",
            cell,
            re.IGNORECASE,
        )
        if not match:
            continue

        username = match.group(1).strip()
        if username and username.casefold() not in {
            existing.casefold() for existing in usernames
        }:
            usernames.append(username)

    if not usernames:
        raise ValueError(
            "스터디 멤버 테이블에서 GitHub 사용자명을 찾지 못했습니다."
        )

    language_cells = find_language_cells(member_section)
    members: list[dict[str, object]] = []

    for index, username in enumerate(usernames):
        languages: list[str] = []

        if index < len(language_cells):
            languages = extract_languages_from_cell(
                language_cells[index]
            )

        if not languages:
            languages = DEFAULT_LANGUAGES.copy()
            print(
                f"경고: {username}의 언어 정보가 없어 "
                f"{', '.join(DEFAULT_LANGUAGES)} 사용"
            )

        members.append(
            {
                "username": username,
                "languages": languages,
            }
        )

    return members


def load_duty_order() -> list[str]:
    """problem-duty.json의 order를 실제 코드 파일 생성 대상자로 사용한다."""
    try:
        config = json.loads(
            DUTY_CONFIG_FILE.read_text(encoding="utf-8")
        )
    except (OSError, json.JSONDecodeError) as error:
        raise ValueError(
            f"problem-duty.json을 읽을 수 없습니다: {error}"
        ) from error

    if not isinstance(config, dict):
        raise ValueError(
            "problem-duty.json의 최상위 값은 객체여야 합니다."
        )

    order = config.get("order")
    if not isinstance(order, list):
        raise ValueError(
            "problem-duty.json에 멤버 목록인 'order' 배열이 없습니다."
        )

    usernames: list[str] = []
    seen: set[str] = set()

    for value in order:
        if not isinstance(value, str) or not value.strip():
            raise ValueError(
                "problem-duty.json의 order에는 "
                "비어 있지 않은 GitHub 사용자명만 입력할 수 있습니다."
            )

        username = value.strip()
        normalized = username.casefold()

        if normalized not in seen:
            seen.add(normalized)
            usernames.append(username)

    if not usernames:
        raise ValueError(
            "problem-duty.json의 order에 등록된 멤버가 없습니다."
        )

    return usernames


def select_duty_members(
    members: list[dict[str, object]],
    duty_order: list[str],
) -> list[dict[str, object]]:
    """JSON에 등록된 멤버만 선택하고 JSON의 순서를 유지한다."""
    members_by_username = {
        str(member["username"]).casefold(): member
        for member in members
    }

    selected: list[dict[str, object]] = []

    for username in duty_order:
        member = members_by_username.get(username.casefold())

        if member is None:
            print(
                f"경고: README에서 {username}을(를) 찾지 못해 "
                "기본 언어(Java)를 사용합니다."
            )
            selected.append(
                {
                    "username": username,
                    "languages": DEFAULT_LANGUAGES.copy(),
                }
            )
        else:
            selected.append(member)

    return selected


def build_code_template(username: str, language: str) -> str:
    if language == "java":
        return (
            "import java.io.*;\n"
            "import java.util.*;\n\n"
            "class Main {\n"
            "    public static void main(String[] args) throws Exception {\n"
            "        BufferedReader br = new BufferedReader("
            "new InputStreamReader(System.in));\n"
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

    target_date = resolve_daily_date(daily_title)
    month_directory = TARGET_ROOT / target_date.strftime("%Y-%m")
    daily_directory = month_directory / safe_title

    daily_directory.mkdir(parents=True, exist_ok=True)

    daily_readme = daily_directory / "README.md"
    if write_if_changed(
        daily_readme,
        build_daily_readme(daily_title, links),
    ):
        print(f"데일리 README 생성/업데이트: {daily_readme}")

    for problem_title, problem_url in links:
        problem_folder_name = build_problem_folder_name(
            problem_title,
            problem_url,
        )
        problem_directory = daily_directory / problem_folder_name
        problem_directory.mkdir(parents=True, exist_ok=True)

        problem_readme = problem_directory / "README.md"
        if write_if_changed(
            problem_readme,
            build_problem_readme(
                problem_folder_name,
                problem_url,
            ),
        ):
            print(f"문제 README 생성/업데이트: {problem_readme}")

        for member in members:
            username = str(member["username"])
            languages = member["languages"]

            if not isinstance(languages, list):
                continue

            for language_value in languages:
                language = str(language_value)
                extension = LANG_EXTENSIONS.get(language)

                if not extension:
                    print(
                        f"지원하지 않는 언어 스킵: {username} - {language}"
                    )
                    continue

                code_file = problem_directory / f"{username}.{extension}"
                created = ensure_file(
                    code_file,
                    build_code_template(username, language),
                )

                if created:
                    print(f"코드 템플릿 생성: {code_file}")
                else:
                    print(f"기존 코드 파일 보존: {code_file}")


def write_success_summary(history_count: int) -> None:
    summary_path = os.getenv("GITHUB_STEP_SUMMARY")
    if not summary_path:
        return

    with open(summary_path, "a", encoding="utf-8") as summary:
        summary.write(
            "# ✅ 데일리 문제 생성 완료\n\n"
            "중복 문제가 발견되지 않았습니다.\n\n"
            f"누적 문제 수: **{history_count}**\n\n"
            "`problem_solve/YYYY-MM/MM-DD/` 구조로 생성했습니다.\n"
        )


def count_history_entries() -> int:
    history = collect_problem_history()
    return sum(len(records) for records in history.values())


def main() -> None:
    if not ROOT_README.exists():
        print("루트 README.md가 없습니다.")
        sys.exit(1)

    try:
        readme_text = ROOT_README.read_text(encoding="utf-8")
        daily_blocks = extract_daily_blocks(readme_text)

        validate_daily_titles(daily_blocks)
        validate_daily_dates(daily_blocks)

        history = collect_problem_history()
        print(f"기존 문제 URL 수: {len(history)}")

        duplicates = find_duplicate_problems(daily_blocks, history)
        if duplicates:
            report_duplicates(duplicates)
            sys.exit(1)

        member_section = extract_member_section(readme_text)
        all_members = extract_members(member_section)
        duty_order = load_duty_order()
        members = select_duty_members(all_members, duty_order)

        print(f"daily_blocks_count: {len(daily_blocks)}")
        print(f"duty_members_count: {len(members)}")

        for member in members:
            print(
                f"member: {member['username']} "
                f"{member['languages']}"
            )

        for daily_title, daily_body in daily_blocks:
            links = extract_links(daily_body)

            print()
            print(f"daily_title: {daily_title}")
            print(f"links_count: {len(links)}")

            generate_daily_folder(
                daily_title,
                links,
                members,
            )

        history_content = build_history_markdown()
        if write_if_changed(HISTORY_FILE, history_content):
            print(f"문제 이력 생성/업데이트: {HISTORY_FILE}")
        else:
            print(f"문제 이력 변경 없음: {HISTORY_FILE}")

        write_success_summary(count_history_entries())

        print()
        print("✅ 중복 검사 및 데일리 문제 생성 완료")

    except ValueError as error:
        message = str(error)
        print(
            "::error title=문제 생성 실패::"
            + escape_github_annotation(message)
        )
        print(f"생성 실패: {message}")
        sys.exit(1)


if __name__ == "__main__":
    main()
