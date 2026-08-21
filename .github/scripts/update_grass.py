from __future__ import annotations

from datetime import date, datetime, timedelta
from pathlib import Path
from urllib.parse import quote
from zoneinfo import ZoneInfo
import html
import os
import re

from generate_daily import build_code_template


README = Path("README.md")
PROBLEM_ROOT = Path("problem_solve")
GRASS_SVG = Path("assets/algorithm-grass.svg")

START = "<!-- ALGORITHM_ACTIVITY:START -->"
END = "<!-- ALGORITHM_ACTIVITY:END -->"


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

MEMBER_RE = re.compile(
    r'<a\s+href=["\']https://github\.com/'
    r'(?P<username>[^/"\'?#]+)["\'][^>]*>'
    r'\s*<b>(?P<name>.*?)</b>\s*</a>',
    re.I | re.S,
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
    """
    README의 스터디 멤버 표에서

    GitHub 사용자명
    실제 이름

    을 추출한다.
    """

    result = []
    seen = set()

    for match in MEMBER_RE.finditer(text):

        username = html.unescape(
            match.group("username")
        ).strip()

        name = re.sub(
            r"<[^>]+>",
            "",
            match.group("name"),
        )

        name = (
            html.unescape(name).strip()
            or username
        )

        key = username.lower()

        if key in seen:
            continue

        seen.add(key)

        result.append(
            {
                "username": username,
                "name": name,
            }
        )

    if not result:
        raise ValueError(
            "README에서 스터디 멤버를 찾지 못했습니다."
        )

    return result


# =========================================================
# 날짜 처리
# =========================================================

def actual_date(
    folder: str,
) -> date:
    """
    MM-DD 폴더명을 실제 날짜 객체로 변환한다.

    예:
        08-17
        → 2026-08-17
    """

    month, day = map(
        int,
        folder.split("-"),
    )

    today = today_kst()

    candidate = date(
        today.year,
        month,
        day,
    )

    # 연말 → 연초를 넘어가는 스터디 대응
    #
    # 예를 들어 현재가 1월인데 12-20 폴더가 있다면
    # 전년도 날짜로 판단한다.
    if (
        candidate
        > today + timedelta(days=45)
    ):
        candidate = date(
            today.year - 1,
            month,
            day,
        )

    return candidate


def study_days() -> list[Path]:
    """
    problem_solve/MM-DD 형태 중 실제 스터디 회차만 가져온다.

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

    for path in PROBLEM_ROOT.iterdir():

        if not path.is_dir():
            continue

        if not DATE_RE.fullmatch(
            path.name
        ):
            continue

        try:
            current_date = actual_date(
                path.name
            )

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


def solved_on_day(
    day: Path,
    username: str,
) -> bool:
    """
    평일 1일 1문제 기준.

    해당 날짜 문제 폴더에 있는
    자신의 파일이 기본 템플릿과 달라졌으면
    풀이 완료로 판단한다.
    """

    for problem in day.iterdir():

        if not problem.is_dir():
            continue

        for file in problem.iterdir():

            if not file.is_file():
                continue

            # -------------------------------------------------
            # 파일 이름이 GitHub username과 동일한 경우만 확인
            #
            # oneul0.java
            # BBZJUN.java
            # ...
            # -------------------------------------------------

            if (
                file.stem.lower()
                != username.lower()
            ):
                continue

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

            day_date = actual_date(
                day.name
            )

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
    README용 알고리즘 잔디 SVG.

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
            '스터디원의 날짜별 알고리즘 문제 풀이 현황'
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
            member_stats["solved"]
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
                f"{quote(day.name, safe='')}"
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
