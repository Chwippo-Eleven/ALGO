from __future__ import annotations

from datetime import date, datetime, timedelta
from pathlib import Path
from zoneinfo import ZoneInfo
from urllib.parse import urlsplit, urlunsplit
import os
import re
import sys
from typing import Iterable


ROOT_README = Path("README.md")
TARGET_ROOT = Path("problem_solve")
HISTORY_FILE = TARGET_ROOT / "PROBLEM_HISTORY.md"

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

KST = ZoneInfo("Asia/Seoul")

def sanitize(name: str) -> str:
    invalid = r'<>:"/\\|?*'

    for character in invalid:
        name = name.replace(character, "-")

    name = re.sub(r"\s+", " ", name).strip()

    return name.rstrip(".")


def normalize_problem_url(url: str) -> str:
    """
    같은 문제 URL이 아래처럼 조금 다르게 들어와도 동일하게 판단한다.

    https://www.acmicpc.net/problem/1000
    https://www.acmicpc.net/problem/1000/

    fragment(#...)는 제거한다.
    http/https 차이도 동일하게 보기 위해 https로 통일한다.
    """

    url = url.strip()

    parsed = urlsplit(url)

    if not parsed.netloc:
        return url.rstrip("/")

    host = parsed.netloc.lower()

    path = parsed.path.rstrip("/")

    if not path:
        path = "/"

    normalized = urlunsplit(
        (
            "https",
            host,
            path,
            parsed.query,
            "",
        )
    )

    return normalized


def extract_daily_blocks(
    text: str,
) -> list[tuple[str, str]]:
    blocks = [
        (
            match.group("title").strip(),
            match.group("body"),
        )
        for match in DAILY_BLOCK_PATTERN.finditer(text)
    ]

    if not blocks:
        raise ValueError(
            "README에서 "
            "'### 🟨 {날짜 또는 회차} 데일리 문제' "
            "형식의 블록을 찾지 못했습니다."
        )

    return blocks


def extract_links(
    body: str,
) -> list[tuple[str, str]]:
    links: list[tuple[str, str]] = []

    for line in body.splitlines():
        match = MARKDOWN_LINK_PATTERN.match(
            line.strip()
        )

        if not match:
            continue

        title = match.group("title").strip()
        url = match.group("url").strip()

        links.append((title, url))

    if not links:
        raise ValueError(
            "데일리 문제 블록에서 "
            "문제 링크를 찾지 못했습니다."
        )

    return links


def extract_all_links(
    text: str,
) -> list[tuple[str, str]]:
    return [
        (
            match.group("title").strip(),
            match.group("url").strip(),
        )
        for match in MARKDOWN_LINK_FIND_PATTERN.finditer(text)
    ]


def detect_platform_tag(
    url: str,
) -> str:
    lower_url = url.lower()

    if "acmicpc.net" in lower_url:
        return "BOJ"

    if (
        "programmers.co.kr" in lower_url
        or "school.programmers.co.kr" in lower_url
    ):
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
    platform = detect_platform_tag(
        problem_url
    )

    return sanitize(
        f"[{platform}] {problem_title}"
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
        lines.append(
            f"- [{problem_title}]({problem_url})"
        )

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


def collect_problem_history() -> dict[
    str,
    list[dict[str, str]]
]:
    """
    problem_solve/{날짜}/README.md 를 읽어
    URL 기준으로 기존 풀이 이력을 만든다.

    problem_solve 하위의 바로 한 단계 README만 읽으므로
    각 문제 폴더 내부 README는 검사하지 않는다.
    """

    history: dict[
        str,
        list[dict[str, str]]
    ] = {}

    if not TARGET_ROOT.exists():
        return history

    for daily_readme in sorted(
        TARGET_ROOT.glob("*/README.md")
    ):
        daily_title = daily_readme.parent.name

        text = daily_readme.read_text(
            encoding="utf-8"
        )

        links = extract_all_links(text)

        for problem_title, problem_url in links:
            normalized_url = normalize_problem_url(
                problem_url
            )

            history.setdefault(
                normalized_url,
                [],
            ).append(
                {
                    "date": daily_title,
                    "title": problem_title,
                    "url": problem_url,
                }
            )

    return history

def validate_daily_dates(
    blocks: list[tuple[str, str]],
) -> None:
    """
    README의 데일리 날짜가 KST 기준 과거이면 실패시킨다.

    예:
        오늘 09-16
        09-15 -> 실패
        09-16 -> 허용
        09-17 -> 허용
    """

    today = datetime.now(
        KST
    ).date()

    for daily_title, _ in blocks:

        safe_title = sanitize(
            daily_title
        )

        match = DAILY_DATE_PATTERN.fullmatch(
            safe_title
        )

        if not match:
            raise ValueError(
                "데일리 문제 날짜는 "
                "MM-DD 형식이어야 합니다: "
                f"{daily_title}"
            )

        month = int(
            match.group("month")
        )

        day = int(
            match.group("day")
        )

        try:
            target_date = date(
                today.year,
                month,
                day,
            )

        except ValueError as error:
            raise ValueError(
                "올바르지 않은 데일리 날짜입니다: "
                f"{daily_title}"
            ) from error

        # 연초에 12월 날짜를 입력한 경우
        # 전년도 날짜로 판단한다.
        if (
            target_date
            > today + timedelta(days=45)
        ):
            target_date = date(
                today.year - 1,
                month,
                day,
            )

        if target_date < today:
            raise ValueError(
                "과거 날짜에는 데일리 문제를 "
                "생성할 수 없습니다. "
                f"입력 날짜: {daily_title}, "
                f"오늘: {today:%m-%d}"
            )

def validate_daily_titles(
    blocks: list[tuple[str, str]],
) -> None:
    seen: set[str] = set()

    for daily_title, _ in blocks:
        safe_title = sanitize(
            daily_title
        )

        if safe_title in seen:
            raise ValueError(
                "README에 동일한 데일리 제목이 "
                f"두 번 존재합니다: {daily_title}"
            )

        seen.add(safe_title)


def find_duplicate_problems(
    blocks: list[tuple[str, str]],
    history: dict[
        str,
        list[dict[str, str]]
    ],
) -> list[dict[str, str]]:
    duplicates: list[dict[str, str]] = []

    # 이번 README 변경에서 새로 추가된 문제끼리도
    # 중복인지 확인한다.
    current_urls: dict[
        str,
        dict[str, str]
    ] = {}

    for daily_title, daily_body in blocks:
        safe_daily_title = sanitize(
            daily_title
        )

        links = extract_links(
            daily_body
        )

        for problem_title, problem_url in links:
            normalized_url = normalize_problem_url(
                problem_url
            )

            # -----------------------------------
            # 기존 problem_solve 기록과 비교
            # -----------------------------------
            previous_records = history.get(
                normalized_url,
                [],
            )

            for previous in previous_records:
                # 같은 날짜의 자기 자신의 기록은
                # 중복으로 판단하지 않는다.
                if (
                    previous["date"]
                    == safe_daily_title
                ):
                    continue

                duplicates.append(
                    {
                        "title": problem_title,
                        "url": problem_url,
                        "current_date": (
                            safe_daily_title
                        ),
                        "previous_date": (
                            previous["date"]
                        ),
                        "previous_title": (
                            previous["title"]
                        ),
                    }
                )

            # -----------------------------------
            # 이번 README 내부에서 중복 검사
            # -----------------------------------
            current_previous = current_urls.get(
                normalized_url
            )

            if current_previous:
                duplicates.append(
                    {
                        "title": problem_title,
                        "url": problem_url,
                        "current_date": (
                            safe_daily_title
                        ),
                        "previous_date": (
                            current_previous[
                                "date"
                            ]
                        ),
                        "previous_title": (
                            current_previous[
                                "title"
                            ]
                        ),
                    }
                )
            else:
                current_urls[
                    normalized_url
                ] = {
                    "date": safe_daily_title,
                    "title": problem_title,
                }

    return remove_duplicate_reports(
        duplicates
    )


def remove_duplicate_reports(
    duplicates: list[dict[str, str]],
) -> list[dict[str, str]]:
    result: list[dict[str, str]] = []
    seen: set[
        tuple[str, str, str]
    ] = set()

    for duplicate in duplicates:
        key = (
            normalize_problem_url(
                duplicate["url"]
            ),
            duplicate["current_date"],
            duplicate["previous_date"],
        )

        if key in seen:
            continue

        seen.add(key)
        result.append(duplicate)

    return result


def escape_github_annotation(
    text: str,
) -> str:
    return (
        text.replace("%", "%25")
        .replace("\r", "%0D")
        .replace("\n", "%0A")
    )


def markdown_escape(
    text: str,
) -> str:
    return text.replace("|", "\\|")


def report_duplicates(
    duplicates: list[dict[str, str]],
) -> None:
    print()
    print("=" * 70)
    print("❌ 중복 문제가 발견되었습니다.")
    print("=" * 70)

    for duplicate in duplicates:
        print()
        print(
            f"문제: {duplicate['title']}"
        )
        print(
            f"URL: {duplicate['url']}"
        )
        print(
            "현재 데일리: "
            f"{duplicate['current_date']}"
        )
        print(
            "이전 풀이: "
            f"{duplicate['previous_date']}"
        )
        print()
        print(
            "이전에 푼 적 있는 문제입니다. "
            "다른 문제로 부탁드립니다!"
        )

        annotation_message = (
            f"{duplicate['title']} | "
            f"URL: {duplicate['url']} | "
            f"이전 풀이: "
            f"{duplicate['previous_date']} | "
            "이전에 푼 적 있는 문제입니다. "
            "다른 문제로 부탁드립니다!"
        )

        print(
            "::error title=중복 문제 발견::"
            + escape_github_annotation(
                annotation_message
            )
        )

    print()
    print("=" * 70)

    write_duplicate_summary(
        duplicates
    )


def write_duplicate_summary(
    duplicates: list[dict[str, str]],
) -> None:
    summary_path = os.getenv(
        "GITHUB_STEP_SUMMARY"
    )

    if not summary_path:
        return

    lines = [
        "# ❌ 중복 문제 발견",
        "",
        (
            "이전에 푼 적 있는 문제입니다. "
            "**다른 문제로 부탁드립니다!**"
        ),
        "",
        "| 문제 | 현재 데일리 | 이전 풀이 | URL |",
        "|---|---|---|---|",
    ]

    for duplicate in duplicates:
        lines.append(
            "| "
            + markdown_escape(
                duplicate["title"]
            )
            + " | "
            + markdown_escape(
                duplicate["current_date"]
            )
            + " | "
            + markdown_escape(
                duplicate["previous_date"]
            )
            + " | "
            + markdown_escape(
                duplicate["url"]
            )
            + " |"
        )

    lines.append("")

    with open(
        summary_path,
        "a",
        encoding="utf-8",
    ) as summary:
        summary.write(
            "\n".join(lines)
        )


def build_history_markdown() -> str:
    """
    날짜별 README를 다시 읽어
    PROBLEM_HISTORY.md를 생성한다.
    """

    rows: list[
        tuple[str, str, str, str]
    ] = []

    if TARGET_ROOT.exists():
        for daily_readme in sorted(
            TARGET_ROOT.glob("*/README.md")
        ):
            daily_title = (
                daily_readme.parent.name
            )

            text = daily_readme.read_text(
                encoding="utf-8"
            )

            for (
                problem_title,
                problem_url,
            ) in extract_all_links(text):
                rows.append(
                    (
                        daily_title,
                        detect_platform_tag(
                            problem_url
                        ),
                        problem_title,
                        problem_url,
                    )
                )

    lines = [
        "# 문제 풀이 이력",
        "",
        (
            "> 이 파일은 GitHub Actions가 "
            "자동으로 생성합니다."
        ),
        (
            "> 문제 중복 여부는 URL 기준으로 "
            "판단합니다."
        ),
        "",
        f"총 문제 수: **{len(rows)}**",
        "",
        "| 날짜 | 플랫폼 | 문제 | URL |",
        "|---|---|---|---|",
    ]

    for (
        daily_title,
        platform,
        problem_title,
        problem_url,
    ) in rows:
        lines.append(
            "| "
            + markdown_escape(daily_title)
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


def write_if_changed(
    path: Path,
    content: str,
) -> bool:
    path.parent.mkdir(
        parents=True,
        exist_ok=True,
    )

    if path.exists():
        old_content = path.read_text(
            encoding="utf-8"
        )

        if old_content == content:
            return False

    path.write_text(
        content,
        encoding="utf-8",
    )

    return True


def ensure_file(
    path: Path,
    content: str = "",
) -> bool:
    path.parent.mkdir(
        parents=True,
        exist_ok=True,
    )

    if path.exists():
        return False

    path.write_text(
        content,
        encoding="utf-8",
    )

    return True


def extract_member_section(
    text: str,
) -> str:
    tables = re.findall(
        r"<table>([\s\S]*?)</table>",
        text,
        re.IGNORECASE,
    )

    for table in tables:
        if "github.com/" in table.lower():
            return table

    raise ValueError(
        "README에서 GitHub 링크가 포함된 "
        "스터디 멤버 테이블을 찾지 못했습니다."
    )


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
            r"github\.com/"
            r"([^\"'/<>?#]+)",
            cell,
            re.IGNORECASE,
        )

        if not match:
            continue

        username = match.group(1)

        if username not in usernames:
            usernames.append(username)

    if not usernames:
        raise ValueError(
            "스터디 멤버 테이블에서 "
            "GitHub 사용자명을 찾지 못했습니다."
        )

    language_cells = find_language_cells(
        member_section
    )

    members: list[
        dict[str, object]
    ] = []

    for index, username in enumerate(
        usernames
    ):
        languages: list[str] = []

        if index < len(language_cells):
            languages = (
                extract_languages_from_cell(
                    language_cells[index]
                )
            )

        if not languages:
            languages = (
                DEFAULT_LANGUAGES.copy()
            )

            print(
                f"경고: {username}의 언어 정보가 없어 "
                f"{', '.join(DEFAULT_LANGUAGES)} "
                "사용"
            )

        members.append(
            {
                "username": username,
                "languages": languages,
            }
        )

    return members


def find_language_cells(
    member_section: str,
) -> list[str]:
    rows = re.findall(
        r"<tr\b[^>]*>"
        r"([\s\S]*?)"
        r"</tr>",
        member_section,
        re.IGNORECASE,
    )

    for row in rows:
        cells = re.findall(
            r"<td\b[^>]*>"
            r"[\s\S]*?"
            r"</td>",
            row,
            re.IGNORECASE,
        )

        if not cells:
            continue

        if any(
            extract_languages_from_cell(
                cell
            )
            for cell in cells
        ):
            return cells

    return []


def extract_languages_from_cell(
    cell_html: str,
) -> list[str]:
    languages: list[str] = []

    data_match = re.search(
        r"data-languages?="
        r"[\"']([^\"']+)[\"']",
        cell_html,
        re.IGNORECASE,
    )

    if data_match:
        candidates = re.split(
            r"[,\s]+",
            data_match.group(1),
        )

        for candidate in candidates:
            language = (
                normalize_language_name(
                    candidate
                )
            )

            if (
                language
                and language not in languages
            ):
                languages.append(language)

    badge_matches = re.findall(
        r"badge/([^?\-\s]+)-",
        cell_html,
        re.IGNORECASE,
    )

    for badge in badge_matches:
        language = normalize_language_name(
            badge
        )

        if (
            language
            and language not in languages
        ):
            languages.append(language)

    plain_text = re.sub(
        r"<[^>]+>",
        " ",
        cell_html,
    )

    for candidate in re.split(
        r"[,/\s]+",
        plain_text,
    ):
        language = normalize_language_name(
            candidate
        )

        if (
            language
            and language not in languages
        ):
            languages.append(language)

    return languages


def normalize_language_name(
    raw: str,
) -> str | None:
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


def build_code_template(
    username: str,
    language: str,
) -> str:
    if language == "java":
        return (
            "import java.io.*;\n"
            "import java.util.*;\n\n"
            "class Main {\n"
            "    public static void main"
            "(String[] args) "
            "throws Exception {\n"
            "        BufferedReader br = "
            "new BufferedReader("
            "new InputStreamReader(System.in));\n"
            "    }\n"
            "}\n"
        )

    if language == "swift":
        return (
            "import Foundation\n\n"
            f"// {username}\n"
        )

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
        return (
            "fun main() {\n"
            "}\n"
        )

    if language in {
        "javascript",
        "typescript",
    }:
        return f"// {username}\n"

    return ""


def generate_daily_folder(
    daily_title: str,
    links: list[tuple[str, str]],
    members: list[dict[str, object]],
) -> None:
    safe_title = sanitize(
        daily_title
    )

    if not safe_title:
        raise ValueError(
            "데일리 문제 제목이 비어 있습니다."
        )

    daily_directory = (
        TARGET_ROOT / safe_title
    )

    daily_directory.mkdir(
        parents=True,
        exist_ok=True,
    )

    daily_readme = (
        daily_directory / "README.md"
    )

    if write_if_changed(
        daily_readme,
        build_daily_readme(
            daily_title,
            links,
        ),
    ):
        print(
            "데일리 README 생성/업데이트: "
            f"{daily_readme}"
        )

    for (
        problem_title,
        problem_url,
    ) in links:
        problem_folder_name = (
            build_problem_folder_name(
                problem_title,
                problem_url,
            )
        )

        problem_directory = (
            daily_directory
            / problem_folder_name
        )

        problem_directory.mkdir(
            parents=True,
            exist_ok=True,
        )

        problem_readme = (
            problem_directory
            / "README.md"
        )

        if write_if_changed(
            problem_readme,
            build_problem_readme(
                problem_folder_name,
                problem_url,
            ),
        ):
            print(
                "문제 README 생성/업데이트: "
                f"{problem_readme}"
            )

        for member in members:
            username = str(
                member["username"]
            )

            languages = member[
                "languages"
            ]

            if not isinstance(
                languages,
                list,
            ):
                continue

            for language_value in languages:
                language = str(
                    language_value
                )

                extension = (
                    LANG_EXTENSIONS.get(
                        language
                    )
                )

                if not extension:
                    print(
                        "지원하지 않는 언어 스킵: "
                        f"{username} - "
                        f"{language}"
                    )
                    continue

                code_file = (
                    problem_directory
                    / f"{username}.{extension}"
                )

                created = ensure_file(
                    code_file,
                    build_code_template(
                        username,
                        language,
                    ),
                )

                if created:
                    print(
                        "코드 템플릿 생성: "
                        f"{code_file}"
                    )
                else:
                    print(
                        "기존 코드 파일 보존: "
                        f"{code_file}"
                    )


def write_success_summary(
    history_count: int,
) -> None:
    summary_path = os.getenv(
        "GITHUB_STEP_SUMMARY"
    )

    if not summary_path:
        return

    with open(
        summary_path,
        "a",
        encoding="utf-8",
    ) as summary:
        summary.write(
            "# ✅ 데일리 문제 생성 완료\n\n"
            "중복 문제가 발견되지 않았습니다.\n\n"
            f"누적 문제 수: **{history_count}**\n\n"
            "`problem_solve/PROBLEM_HISTORY.md`가 "
            "갱신되었습니다.\n"
        )


def count_history_entries() -> int:
    history = collect_problem_history()

    return sum(
        len(records)
        for records in history.values()
    )


def main() -> None:
    if not ROOT_README.exists():
        print(
            "루트 README.md가 없습니다."
        )
        sys.exit(1)

    try:
        readme_text = (
            ROOT_README.read_text(
                encoding="utf-8"
            )
        )

        daily_blocks = (
            extract_daily_blocks(
                readme_text
            )
        )
        
        validate_daily_titles(
            daily_blocks
        )
        
        # 과거 날짜 덮어쓰기 방지
        validate_daily_dates(
            daily_blocks
        )

        # ---------------------------------------
        # 기존 날짜 README 기준으로 과거 문제 수집
        # ---------------------------------------
        history = collect_problem_history()

        print(
            "기존 문제 URL 수: "
            f"{len(history)}"
        )

        # ---------------------------------------
        # 폴더를 만들기 전에 중복부터 검사
        # ---------------------------------------
        duplicates = (
            find_duplicate_problems(
                daily_blocks,
                history,
            )
        )

        if duplicates:
            report_duplicates(
                duplicates
            )

            # 실패시키면 이후 GitHub Actions의
            # commit 단계가 실행되지 않는다.
            sys.exit(1)

        # ---------------------------------------
        # 중복이 없을 때만 폴더 생성
        # ---------------------------------------
        member_section = (
            extract_member_section(
                readme_text
            )
        )

        members = extract_members(
            member_section
        )

        print(
            "daily_blocks_count: "
            f"{len(daily_blocks)}"
        )

        for member in members:
            print(
                f"member: "
                f"{member['username']} "
                f"{member['languages']}"
            )

        for (
            daily_title,
            daily_body,
        ) in daily_blocks:
            links = extract_links(
                daily_body
            )

            print()
            print(
                f"daily_title: "
                f"{daily_title}"
            )
            print(
                f"links_count: "
                f"{len(links)}"
            )

            generate_daily_folder(
                daily_title,
                links,
                members,
            )

        # ---------------------------------------
        # 모든 날짜 README를 기준으로
        # 전체 문제 이력 파일 생성
        # ---------------------------------------
        history_content = (
            build_history_markdown()
        )

        if write_if_changed(
            HISTORY_FILE,
            history_content,
        ):
            print(
                "문제 이력 생성/업데이트: "
                f"{HISTORY_FILE}"
            )
        else:
            print(
                "문제 이력 변경 없음: "
                f"{HISTORY_FILE}"
            )

        history_count = (
            count_history_entries()
        )

        write_success_summary(
            history_count
        )

        print()
        print(
            "✅ 중복 검사 및 "
            "데일리 문제 생성 완료"
        )

    except ValueError as error:
        message = str(error)

        print(
            "::error title=문제 생성 실패::"
            + escape_github_annotation(
                message
            )
        )

        print(
            f"생성 실패: {message}"
        )

        sys.exit(1)


if __name__ == "__main__":
    main()
