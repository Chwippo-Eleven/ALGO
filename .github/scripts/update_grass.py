from __future__ import annotations

from datetime import date, datetime
from pathlib import Path
from urllib.parse import quote
from zoneinfo import ZoneInfo
import html
import json
import os
import re
import subprocess

from generate_daily import build_code_template, daily_date_from_path, resolve_daily_date
from update_duty import build_active_order, get_active_members, load_config


README = Path("README.md")
PROBLEM_ROOT = Path("problem_solve")
GRASS_SVG = Path("assets/algorithm-grass.svg")

START = "<!-- ALGORITHM_ACTIVITY:START -->"
END = "<!-- ALGORITHM_ACTIVITY:END -->"


# 파일별 GitHub 커밋 작성자 조회 결과 캐시
COMMIT_AUTHOR_CACHE: dict[str, str | None] = {}


# =========================================================
# 점수 정책
# =========================================================

# 문제를 풀었을 때 기본 점수
BASE_POINT = 10

# 연속 풀이가 이어질 때마다 다음 풀이 획득 점수 +2
STREAK_BONUS_PER_DAY = 2

# 스트릭으로 받을 수 있는 최대 추가 점수
MAX_STREAK_BONUS = 20


# =========================================================
# 잔디 색상
# =========================================================

EMPTY = "#ebedf0"
SOLVED = "#216e39"


# =========================================================
# 배지 색상
# =========================================================

BADGE_COLORS = {
    "UNRANKED": "#6e7781",
    "BRONZE": "#bc6f3c",
    "SILVER": "#8c959f",
    "GOLD": "#bf8700",
    "PLATINUM": "#8250df",
    "DIAMOND": "#1f6feb",
}


# =========================================================
# 정규식
# =========================================================

DATE_RE = re.compile(
    r"^\d{2}-\d{2}$"
)

# =========================================================
# 확장자 → generate_daily.py 언어명
# =========================================================

EXT_TO_LANG = {
    ".java": "java",
    ".swift": "swift",
    ".py": "python",
    ".cpp": "c++",
    ".cc": "c++",
    ".cxx": "c++",
    ".c": "c",
    ".kt": "kotlin",
    ".js": "javascript",
    ".ts": "typescript",
}


# =========================================================
# 공통
# =========================================================

def normalize(text: str) -> str:
    """
    OS별 개행 차이와 파일 마지막 공백 때문에
    제출 여부가 잘못 판정되는 것을 방지한다.
    """

    return (
        text
        .replace("\r\n", "\n")
        .replace("\r", "\n")
        .strip()
    )


def escape(value: str) -> str:
    return html.escape(
        value,
        quote=True,
    )


def today_kst() -> date:
    return datetime.now(
        ZoneInfo("Asia/Seoul")
    ).date()


# =========================================================
# README 멤버 추출
# =========================================================

def members_from_readme(
    text: str,
) -> list[dict[str, str]]:
    """JSON 순서대로 README에 남아 있는 등록 멤버만 가져온다."""
    active_members = get_active_members(text)
    active_order = build_active_order(load_config()["order"], active_members)
    return [
        {"username": username, "name": active_members[username]}
        for username in active_order
    ]


# =========================================================
# 날짜 처리
# =========================================================

def actual_date(folder: Path) -> date:
    """월별 폴더의 연도를 사용하고, 기존 날짜 폴더만 연도를 추정한다."""
    if folder.parent == PROBLEM_ROOT:
        return resolve_daily_date(folder.name, today_kst())
    return daily_date_from_path(folder / "README.md")


def study_days() -> list[Path]:
    """
    problem_solve/MM-DD와 problem_solve/YYYY-MM/MM-DD의 실제 회차를 가져온다.

    조건:
    1. MM-DD 형식
    2. 평일
    3. 해당 날짜 아래 실제 문제 폴더 존재

    주말은 애초에 회차에서 제외하므로
    금요일 → 월요일은 연속 스트릭으로 계산된다.
    """

    days = []

    if not PROBLEM_ROOT.exists():
        return []

    paths = list(PROBLEM_ROOT.iterdir())
    paths.extend(PROBLEM_ROOT.glob("????-??/*"))
    for path in paths:

        if not path.is_dir():
            continue

        if not DATE_RE.fullmatch(
            path.name
        ):
            continue

        try:
            current_date = actual_date(path)

        except ValueError:
            continue

        # -------------------------------------------------
        # 토요일 / 일요일 제외
        # -------------------------------------------------

        if current_date.weekday() >= 5:
            continue

        # -------------------------------------------------
        # 실제 문제 폴더가 존재하는 날짜만 사용
        # -------------------------------------------------

        has_problem = any(
            child.is_dir()
            for child in path.iterdir()
        )

        if not has_problem:
            continue

        days.append(
            (
                current_date,
                path,
            )
        )

    days.sort(
        key=lambda item: item[0]
    )

    return [
        path
        for _, path in days
    ]


# =========================================================
# 제출 여부 판정
# =========================================================

def submitted_file(
    path: Path,
    username: str,
) -> bool:
    """
    generate_daily.py가 만든 기본 템플릿과
    현재 파일 내용을 비교한다.

    같음
        → 미제출

    다름
        → 실제 풀이 제출
    """

    try:
        content = path.read_text(
            encoding="utf-8"
        )

    except UnicodeDecodeError:

        # 텍스트 파일이 아닌 경우
        # 파일 내용이 존재하면 제출로 본다.
        return (
            path.stat().st_size > 0
        )

    language = EXT_TO_LANG.get(
        path.suffix.lower()
    )

    # -----------------------------------------------------
    # generate_daily.py에서 지원하지 않는 확장자
    # -----------------------------------------------------

    if language is None:

        return bool(
            normalize(content)
        )

    # -----------------------------------------------------
    # 기존 자동 생성 템플릿 그대로 재사용
    # -----------------------------------------------------

    template = build_code_template(
        username,
        language,
    )

    return (
        normalize(content)
        != normalize(template)
    )


def last_member_commit_author(
    path: Path,
    member_usernames: set[str],
) -> str | None:
    """
    파일명이 username과 다를 때 GitHub의 파일 커밋 이력을 조회해
    가장 최근에 등장하는 스터디 멤버의 GitHub login을 반환한다.

    예:
        Solution.java
        → 최근 커밋 이력 중 author.login == "hyunji-ch5i15"
        → hyunji-ch5i15의 제출로 판정

    Batch merge의 merge commit이나 github-actions[bot]처럼
    스터디 멤버가 아닌 작성자는 건너뛴다.

    같은 파일을 멤버마다 반복 조회하지 않도록 결과를 캐싱한다.
    """

    key = path.as_posix()

    if key in COMMIT_AUTHOR_CACHE:
        return COMMIT_AUTHOR_CACHE[key]

    repository = os.environ.get("GH_REPO")

    if not repository:
        COMMIT_AUTHOR_CACHE[key] = None
        return None

    try:
        result = subprocess.run(
            [
                "gh",
                "api",
                "--method",
                "GET",
                f"repos/{repository}/commits",
                "-f",
                f"path={key}",
                "-f",
                "per_page=10",
            ],
            capture_output=True,
            text=True,
            check=True,
        )

        commits = json.loads(
            result.stdout
        )

    except (
        subprocess.CalledProcessError,
        json.JSONDecodeError,
        FileNotFoundError,
    ):
        COMMIT_AUTHOR_CACHE[key] = None
        return None

    for commit in commits:

        author = commit.get(
            "author"
        )

        if not author:
            continue

        login = author.get(
            "login"
        )

        if not login:
            continue

        login_lower = login.lower()

        if login_lower in member_usernames:
            COMMIT_AUTHOR_CACHE[key] = login_lower
            return login_lower

    COMMIT_AUTHOR_CACHE[key] = None
    return None


def solved_on_day(
    day: Path,
    username: str,
    member_usernames: set[str],
) -> bool:
    """
    평일 1일 1문제 기준.

    1. 파일명이 GitHub username과 같으면 기존 방식으로 판정한다.
    2. 파일명이 Solution.java처럼 username과 다르면,
       코드 파일에 한해서 GitHub 커밋 작성자를 조회해 보정한다.
    3. README.md 같은 비코드 파일은 커밋 기반 보정 대상에서 제외한다.
    """

    username_lower = username.lower()

    for problem in day.iterdir():

        if not problem.is_dir():
            continue

        for file in problem.iterdir():

            if not file.is_file():
                continue

            file_stem = (
                file.stem.lower()
            )

            # -------------------------------------------------
            # 정상 제출:
            # oneul0.java, hyunji-ch5i15.java 등
            # -------------------------------------------------

            if file_stem == username_lower:

                if submitted_file(
                    file,
                    username,
                ):
                    return True

                continue

            # -------------------------------------------------
            # README.md 등 코드 파일이 아닌 것은
            # 커밋 작성자 기반 보정에서 제외
            # -------------------------------------------------

            if (
                file.suffix.lower()
                not in EXT_TO_LANG
            ):
                continue

            # -------------------------------------------------
            # 파일명이 다른 스터디 멤버의 username이면
            # 그 멤버의 정상 제출 파일이므로 조회하지 않는다.
            # -------------------------------------------------

            if file_stem in member_usernames:
                continue

            # -------------------------------------------------
            # Solution.java / Main.java 등:
            # 파일명이 username과 다를 때 커밋 작성자로 보정
            # -------------------------------------------------

            author = last_member_commit_author(
                file,
                member_usernames,
            )

            if author != username_lower:
                continue

            # 작성자가 확인되어도 빈 템플릿 그대로라면
            # 제출로 인정하지 않는다.
            if submitted_file(
                file,
                username,
            ):
                return True

    return False


# =========================================================
# 배지
# =========================================================

def badge_name(
    score: int,
) -> str:
    """
    누적 점수 기준 배지.
    """

    if score >= 800:
        return "DIAMOND"

    if score >= 500:
        return "PLATINUM"

    if score >= 250:
        return "GOLD"

    if score >= 100:
        return "SILVER"

    if score > 0:
        return "BRONZE"

    return "UNRANKED"


# =========================================================
# 점수 / 스트릭 / 이번달 참여 계산
# =========================================================

def calculate(
    members: list[dict[str, str]],
    days: list[Path],
) -> dict:
    """
    멤버별로 다음을 계산한다.

    solved
        날짜별 풀이 여부

    score
        전체 기간 누적 점수

    badge
        누적 점수 기반 배지

    longest_streak
        스터디 시작 이후 전체 기간 최장 스트릭

    month_participation
        현재 달에 문제를 푼 횟수
    """

    result = {}

    member_usernames = {
        member["username"].lower()
        for member in members
    }

    today = today_kst()

    current_year = today.year
    current_month = today.month

    for member in members:

        username = member[
            "username"
        ]

        solved_list = []

        # =================================================
        # 전체 기간 점수 / 스트릭
        # =================================================

        score = 0

        current_streak = 0
        longest_streak = 0

        # =================================================
        # 이번 달 참여
        # =================================================

        month_participation = 0

        for day in days:

            done = solved_on_day(
                day,
                username,
                member_usernames,
            )

            solved_list.append(
                done
            )

            # =================================================
            # 전체 기간 스트릭
            # =================================================

            if done:

                current_streak += 1

                longest_streak = max(
                    longest_streak,
                    current_streak,
                )

                # ---------------------------------------------
                # 점수
                #
                # 1일차 10
                # 2일차 12
                # 3일차 14
                # ...
                # 최대 하루 30점
                # ---------------------------------------------

                bonus = min(
                    (
                        current_streak - 1
                    )
                    * STREAK_BONUS_PER_DAY,

                    MAX_STREAK_BONUS,
                )

                score += (
                    BASE_POINT
                    + bonus
                )

            else:

                # 실제 스터디 회차에서 문제를 풀지 않았으므로
                # 스트릭 초기화
                current_streak = 0

            # =================================================
            # 이번 달 참여 횟수
            # =================================================

            day_date = actual_date(day)

            if (
                done
                and day_date.year
                == current_year
                and day_date.month
                == current_month
            ):
                month_participation += 1

        result[
            username
        ] = {
            "solved": solved_list,

            "score": score,

            "badge": badge_name(
                score
            ),

            "longest_streak": (
                longest_streak
            ),

            "month_participation": (
                month_participation
            ),
        }

    return result


# =========================================================
# SVG
# =========================================================

def make_svg(
    members: list[dict[str, str]],
    days: list[Path],
    stats: dict,
) -> str:
    """
    최근 20회차를 표시하고 누적 통계는 전체 기간 기준으로 유지한다.

    한 행:

    이름
    점수
    배지
    잔디
    이번달 참여
    최장 스트릭
    """

    # =====================================================
    # 기본 셀 크기
    # =====================================================

    display_limit = 20
    days = days[-display_limit:]

    cell = 14
    gap = 4

    step = (
        cell
        + gap
    )

    # =====================================================
    # 왼쪽 정보 영역
    #
    # 이름 / 점수 / 배지
    # =====================================================

    left = 270

    # =====================================================
    # 날짜 라벨 영역
    #
    # 회전된 날짜가 잘리지 않도록 넉넉하게 확보
    # =====================================================

    top = 125

    row_height = 28

    # =====================================================
    # 오른쪽 통계 영역
    # =====================================================

    right = 270

    grass_width = (
        len(days)
        * step
    )

    width = max(
        850,

        left
        + grass_width
        + right,
    )

    height = (
        top
        + len(members)
        * row_height
        + 20
    )

    repository = "Chwippo-Eleven/ALGO"

    output = [

        (
            f'<svg '
            f'xmlns="http://www.w3.org/2000/svg" '
            f'xmlns:xlink="http://www.w3.org/1999/xlink" '
            f'width="{width}" '
            f'height="{height}" '
            f'viewBox="0 0 {width} {height}" '
            f'role="img" '
            f'aria-labelledby="title desc">'
        ),

        (
            '<title id="title">'
            '매일알고 알고리즘 잔디'
            '</title>'
        ),

        (
            '<desc id="desc">'
            '최근 20회차의 풀이 현황과 전체 기간 누적 통계'
            '</desc>'
        ),

        "<style>",

        (
            "text{"
            "font-family:"
            "-apple-system,"
            "BlinkMacSystemFont,"
            "'Segoe UI',"
            "Helvetica,"
            "Arial,"
            "sans-serif;"
            "fill:#24292f"
            "}"
        ),

        (
            ".name{"
            "font-size:12px;"
            "font-weight:700"
            "}"
        ),

        (
            ".score{"
            "font-size:11px;"
            "font-weight:600;"
            "fill:#57606a"
            "}"
        ),

        (
            ".badge{"
            "font-size:9px;"
            "font-weight:700;"
            "fill:#ffffff"
            "}"
        ),

        (
            ".date{"
            "font-size:10px;"
            "fill:#57606a"
            "}"
        ),

        (
            ".stat{"
            "font-size:11px;"
            "font-weight:500;"
            "fill:#57606a"
            "}"
        ),

        (
            "@media(prefers-color-scheme:dark){"
            "text{fill:#c9d1d9}"
            ".date,"
            ".score,"
            ".stat{fill:#8b949e}"
            "}"
        ),

        "</style>",
    ]

    # =====================================================
    # 날짜 라벨
    # =====================================================

    for column, day in enumerate(
        days
    ):

        x = (
            left
            + column * step
            + cell / 2
        )

        # 셀보다 충분히 위쪽에서 날짜 시작
        y = (
            top - 22
        )

        output.append(
            (
                f'<text '
                f'x="{x:.1f}" '
                f'y="{y}" '
                f'class="date" '
                f'text-anchor="start" '
                f'transform="'
                f'rotate(-45 {x:.1f} {y})'
                f'">'
                f'{escape(day.name)}'
                f'</text>'
            )
        )

    # =====================================================
    # 멤버별 행
    # =====================================================

    for row, member in enumerate(
        members
    ):

        username = member[
            "username"
        ]

        member_stats = stats[
            username
        ]

        score = member_stats[
            "score"
        ]

        badge = member_stats[
            "badge"
        ]

        longest_streak = (
            member_stats[
                "longest_streak"
            ]
        )

        month_participation = (
            member_stats[
                "month_participation"
            ]
        )

        badge_color = BADGE_COLORS[
            badge
        ]

        y = (
            top
            + row * row_height
        )

        # =================================================
        # 이름
        # =================================================

        profile_url = (
            "https://github.com/"
            + quote(
                username,
                safe="",
            )
        )

        output.extend(
            [
                (
                    f'<a '
                    f'xlink:href="'
                    f'{escape(profile_url)}" '
                    f'target="_blank">'
                ),

                (
                    f'<text '
                    f'x="0" '
                    f'y="{y + 11}" '
                    f'class="name">'
                    f'{escape(member["name"])}'
                    f'</text>'
                ),

                "</a>",
            ]
        )

        # =================================================
        # 점수
        # =================================================

        output.append(
            (
                f'<text '
                f'x="72" '
                f'y="{y + 11}" '
                f'class="score">'
                f'{score}점'
                f'</text>'
            )
        )

        # =================================================
        # 배지
        # =================================================

        badge_x = 125
        badge_y = y - 1

        badge_width = 88
        badge_height = 17

        output.append(
            (
                f'<rect '
                f'x="{badge_x}" '
                f'y="{badge_y}" '
                f'width="{badge_width}" '
                f'height="{badge_height}" '
                f'rx="8.5" '
                f'fill="{badge_color}"'
                f'/>'
            )
        )

        output.append(
            (
                f'<text '
                f'x="{badge_x + badge_width / 2}" '
                f'y="{badge_y + 11.5}" '
                f'class="badge" '
                f'text-anchor="middle">'
                f'{badge}'
                f'</text>'
            )
        )

        # =================================================
        # 날짜별 잔디
        # =================================================

        for column, done in enumerate(
            member_stats["solved"][-display_limit:]
        ):

            day = days[
                column
            ]

            x = (
                left
                + column * step
            )

            color = (
                SOLVED
                if done
                else EMPTY
            )

            url = (
                f"https://github.com/"
                f"{repository}/tree/main/"
                f"problem_solve/"
                f"{quote(day.relative_to(PROBLEM_ROOT).as_posix(), safe='/')}"
            )

            status = (
                "풀이 완료"
                if done
                else "미제출"
            )

            tooltip = (
                f'{member["name"]} · '
                f'{day.name} · '
                f'{status}'
            )

            output.extend(
                [
                    (
                        f'<a '
                        f'xlink:href="'
                        f'{escape(url)}" '
                        f'target="_blank">'
                    ),

                    (
                        f'<rect '
                        f'x="{x}" '
                        f'y="{y}" '
                        f'width="{cell}" '
                        f'height="{cell}" '
                        f'rx="3" '
                        f'fill="{color}">'
                    ),

                    (
                        f'<title>'
                        f'{escape(tooltip)}'
                        f'</title>'
                    ),

                    "</rect>",

                    "</a>",
                ]
            )

        # =================================================
        # 이번달 참여 + 전체 기간 최장 스트릭
        # =================================================

        stat_x = (
            left
            + grass_width
            + 18
        )

        output.append(
            (
                f'<text '
                f'x="{stat_x}" '
                f'y="{y + 11}" '
                f'class="stat">'
                f'이번달 참여 '
                f'{month_participation}회'
                f' · '
                f'최장 스트릭 '
                f'{longest_streak}일'
                f'</text>'
            )
        )

    output.append(
        "</svg>"
    )

    return "\n".join(
        output
    )


# =========================================================
# README 자동 영역
# =========================================================

def activity_block() -> str:
    """
    별도 점수 표를 만들지 않는다.

    모든 사용자 정보는 SVG 내부에 표시한다.
    """

    return "\n".join(
        [
            START,

            "## 🌱 알고리즘 잔디",

            "",

            (
                "> 평일 **1일 1문제** 기준입니다. "
                "초록색은 풀이 완료, 회색은 미제출입니다."
            ),

            "",

            (
                "[![Algorithm Grass]"
                "(./assets/algorithm-grass.svg)]"
                "(./problem_solve)"
            ),

            "",

            (
                "> **점수:** "
                "첫 풀이 10점 · "
                "연속 풀이 시 다음 문제부터 +2점 · "
                "스트릭 보너스 최대 +20점"
            ),

            "",

            (
                "> **배지:** "
                "BRONZE 1+ · "
                "SILVER 100+ · "
                "GOLD 250+ · "
                "PLATINUM 500+ · "
                "DIAMOND 800+"
            ),

            "",

            (
                "> **이번달 참여**는 현재 달의 풀이 횟수이며, "
                "**최장 스트릭**은 스터디 전체 기간 중 "
                "가장 길게 연속으로 풀이한 기록입니다."
            ),

            END,
        ]
    )


def update_readme(
    text: str,
    block: str,
) -> str:
    """
    README 내 자동 생성 영역만 갱신한다.
    """

    # =====================================================
    # 기존 블록이 있으면 교체
    # =====================================================

    if (
        START in text
        and END in text
    ):

        pattern = re.compile(
            re.escape(START)
            + r".*?"
            + re.escape(END),
            re.S,
        )

        return pattern.sub(
            lambda _: block,
            text,
        )

    # =====================================================
    # 최초 실행 시 데일리 문제 영역 앞에 삽입
    # =====================================================

    heading = re.search(
        r"^###\s*🟨",
        text,
        re.M,
    )

    if heading:

        index = heading.start()

        return (
            text[:index].rstrip()
            + "\n\n<br />\n\n"
            + block
            + "\n\n<br />\n\n"
            + text[index:].lstrip()
        )

    # =====================================================
    # 데일리 문제 영역이 없다면 맨 아래 추가
    # =====================================================

    return (
        text.rstrip()
        + "\n\n"
        + block
        + "\n"
    )


# =========================================================
# 실행
# =========================================================

def main() -> None:

    if not README.exists():

        raise FileNotFoundError(
            "README.md가 없습니다."
        )

    if not PROBLEM_ROOT.exists():

        raise FileNotFoundError(
            "problem_solve 폴더가 없습니다."
        )

    # =====================================================
    # README / 멤버
    # =====================================================

    readme = README.read_text(
        encoding="utf-8"
    )

    members = members_from_readme(
        readme
    )

    # =====================================================
    # 과거 포함 전체 스터디 회차
    # =====================================================

    days = study_days()

    # =====================================================
    # 점수 / 참여 / 스트릭 계산
    # =====================================================

    stats = calculate(
        members,
        days,
    )

    # =====================================================
    # SVG 생성
    # =====================================================

    GRASS_SVG.parent.mkdir(
        parents=True,
        exist_ok=True,
    )

    GRASS_SVG.write_text(
        make_svg(
            members,
            days,
            stats,
        )
        + "\n",

        encoding="utf-8",
    )

    # =====================================================
    # README 갱신
    # =====================================================

    README.write_text(
        update_readme(
            readme,
            activity_block(),
        ),

        encoding="utf-8",
    )

    # =====================================================
    # GitHub Actions 로그
    # =====================================================

    print(
        f"집계 완료: "
        f"{len(members)}명 / "
        f"{len(days)}회"
    )

    for member in members:

        stat = stats[
            member["username"]
        ]

        print(
            f'- {member["name"]}: '
            f'{stat["score"]}점 / '
            f'{stat["badge"]} / '
            f'이번달 참여 '
            f'{stat["month_participation"]}회 / '
            f'최장 스트릭 '
            f'{stat["longest_streak"]}일'
        )


if __name__ == "__main__":
    main()
