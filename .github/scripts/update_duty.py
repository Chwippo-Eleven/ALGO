from __future__ import annotations

from datetime import date, datetime, time, timedelta
from pathlib import Path
from zoneinfo import ZoneInfo

import html
import json
import re


# =========================================================
# 경로
# =========================================================

README_PATH = Path("README.md")
CONFIG_PATH = Path("problem-duty.json")
SVG_PATH = Path("assets/problem-duty.svg")


# =========================================================
# 시간 정책
# =========================================================

KST = ZoneInfo("Asia/Seoul")

# 평일 18:00부터 다음 평일 문제 담당자로 전환
DUTY_CHANGE_TIME = time(
    hour=18,
    minute=0,
)


WEEKDAY_NAMES = [
    "월",
    "화",
    "수",
    "목",
    "금",
    "토",
    "일",
]


# =========================================================
# README 멤버 추출
#
# <a href="https://github.com/oneul0">
#   <b>김기훈</b>
# </a>
# =========================================================

MEMBER_PATTERN = re.compile(
    r'<a\s+href=["\']https://github\.com/'
    r'(?P<username>[^/"\'?#]+)["\'][^>]*>'
    r'\s*<b>(?P<name>.*?)</b>\s*</a>',
    re.IGNORECASE | re.DOTALL,
)


# =========================================================
# 현재 시각
# =========================================================

def now_kst() -> datetime:
    return datetime.now(KST)


# =========================================================
# README에서 현재 활성 멤버 추출
# =========================================================

def get_active_members(
    readme: str,
) -> dict[str, str]:

    members: dict[str, str] = {}

    for match in MEMBER_PATTERN.finditer(readme):

        username = html.unescape(
            match.group("username")
        ).strip()

        name = re.sub(
            r"<[^>]+>",
            "",
            match.group("name"),
        )

        name = html.unescape(name).strip()

        if not username:
            continue

        members[username] = (
            name
            or username
        )

    if not members:
        raise ValueError(
            "README에서 활성 스터디 멤버를 "
            "찾지 못했습니다."
        )

    return members


# =========================================================
# 평일 계산
# =========================================================

def next_weekday(
    current: date,
) -> date:

    target = (
        current
        + timedelta(days=1)
    )

    while target.weekday() >= 5:
        target += timedelta(days=1)

    return target


def next_or_same_weekday(
    current: date,
) -> date:

    target = current

    while target.weekday() >= 5:
        target += timedelta(days=1)

    return target


def weekday_distance(
    start: date,
    target: date,
) -> int:
    """
    두 날짜 사이에 담당자가 몇 번 변경되는지 계산한다.

    목 -> 금 = 1
    금 -> 월 = 1
    """

    if target < start:
        raise ValueError(
            "현재 문제 날짜가 "
            "problem-duty.json의 base_date보다 이전입니다."
        )

    if start == target:
        return 0

    cursor = start
    count = 0

    while cursor < target:
        cursor = next_weekday(cursor)
        count += 1

    return count


# =========================================================
# 현재 담당 문제 날짜
# =========================================================

def current_problem_date(
    current: datetime,
) -> date:
    """
    평일 18:00 이전
        -> 오늘 문제

    평일 18:00 이후
        -> 다음 평일 문제

    금요일 18:00 이후
        -> 월요일 문제

    토/일
        -> 월요일 문제
    """

    today = current.date()

    # 주말
    if today.weekday() >= 5:
        return next_or_same_weekday(
            today
        )

    # 평일 18시 이전
    if current.time() < DUTY_CHANGE_TIME:
        return today

    # 평일 18시 이후
    return next_weekday(
        today
    )


# =========================================================
# 설정 파일
# =========================================================

def load_config() -> dict:

    if not CONFIG_PATH.exists():
        raise FileNotFoundError(
            f"{CONFIG_PATH}가 없습니다."
        )

    with CONFIG_PATH.open(
        "r",
        encoding="utf-8",
    ) as file:
        config = json.load(file)

    if not isinstance(config, dict):
        raise ValueError("problem-duty.json은 객체여야 합니다.")

    required = {
        "order",
        "base_date",
        "base_username",
    }

    missing = (
        required
        - config.keys()
    )

    if missing:
        raise ValueError(
            "problem-duty.json에 필수 필드가 없습니다: "
            + ", ".join(
                sorted(missing)
            )
        )

    if not isinstance(
        config["order"],
        list,
    ):
        raise ValueError(
            "problem-duty.json의 "
            "order는 배열이어야 합니다."
        )

    if not config["order"]:
        raise ValueError(
            "problem-duty.json의 "
            "order가 비어 있습니다."
        )

    seen = set()
    for username in config["order"]:
        if not isinstance(username, str) or not re.fullmatch(
            r"[A-Za-z0-9](?:[A-Za-z0-9-]{0,37}[A-Za-z0-9])?", username
        ):
            raise ValueError("order에는 올바른 GitHub 사용자명만 넣어야 합니다.")
        key = username.lower()
        if key in seen:
            raise ValueError(f"order에 중복된 사용자명이 있습니다: {username}")
        seen.add(key)

    base_username = config["base_username"]
    if not isinstance(base_username, str) or base_username.lower() not in seen:
        raise ValueError("base_username은 order에 등록된 사용자명이어야 합니다.")

    base_date = config["base_date"]
    if not isinstance(base_date, str) or not re.fullmatch(r"\d{4}-\d{2}-\d{2}", base_date):
        raise ValueError("base_date는 YYYY-MM-DD 형식이어야 합니다.")
    try:
        parsed_date = date.fromisoformat(base_date)
    except ValueError as error:
        raise ValueError("base_date가 올바른 날짜가 아닙니다.") from error
    if parsed_date.weekday() >= 5:
        raise ValueError("base_date는 평일이어야 합니다.")

    return config


# =========================================================
# 현재 활성 로테이션 구성
# =========================================================

def build_active_order(
    full_order: list[str],
    active_members: dict[str, str],
) -> list[str]:
    """
    JSON의 순서는 그대로 유지한다.

    README에서 빠진 멤버는
    현재 로테이션에서 제외한다.
    """

    active_lookup = {
        username.lower(): username
        for username
        in active_members
    }

    active_order: list[str] = []

    for username in full_order:

        real_username = (
            active_lookup.get(
                username.lower()
            )
        )

        if real_username is None:
            continue

        active_order.append(
            real_username
        )

    if not active_order:
        raise ValueError(
            "현재 활성 로테이션 멤버가 없습니다."
        )

    return active_order


# =========================================================
# order 탐색
# =========================================================

def find_order_index(
    order: list[str],
    username: str,
) -> int | None:

    target = username.lower()

    for index, candidate in enumerate(order):

        if candidate.lower() == target:
            return index

    return None


# =========================================================
# 다음 활성 담당자
# =========================================================

def next_active_username(
    full_order: list[str],
    active_order: list[str],
    current_username: str,
) -> str:

    if not active_order:
        raise ValueError(
            "활성 멤버가 없습니다."
        )

    active_lookup = {
        username.lower(): username
        for username
        in active_order
    }

    current_index = find_order_index(
        full_order,
        current_username,
    )

    if current_index is None:
        raise ValueError(
            f"{current_username}이 "
            "problem-duty.json의 order에 없습니다."
        )

    total = len(full_order)

    for offset in range(
        1,
        total + 1,
    ):
        candidate = full_order[
            (
                current_index
                + offset
            )
            % total
        ]

        active_username = (
            active_lookup.get(
                candidate.lower()
            )
        )

        if active_username:
            return active_username

    raise ValueError(
        "다음 활성 담당자를 찾지 못했습니다."
    )


# =========================================================
# 특정 날짜 담당자 계산
# =========================================================

def resolve_duty(
    config: dict,
    active_order: list[str],
    target_date: date,
) -> str:
    """
    JSON의 고정 기준점을 이용해서 담당자를 계산한다.

    JSON은 수정하지 않는다.

    예:
        base_date     = 2026-08-20
        base_username = Sangyoon-Shin

        08-20 -> Sangyoon-Shin
        08-21 -> yeonju73
        08-24 -> hyunji-ch5i15
    """

    base_date = date.fromisoformat(
        config["base_date"]
    )

    username = config["base_username"]

    full_order = list(
        config["order"]
    )

    if find_order_index(
        full_order,
        username,
    ) is None:
        raise ValueError(
            "base_username이 "
            "order에 존재하지 않습니다."
        )

    distance = weekday_distance(
        base_date,
        target_date,
    )

    active_lower = {
        item.lower()
        for item
        in active_order
    }

    # 기준 담당자가 현재 비활성화 상태라면
    # 다음 활성 담당자부터 계산
    if username.lower() not in active_lower:

        username = next_active_username(
            full_order,
            active_order,
            username,
        )

    else:
        # README에 적힌 실제 대소문자 형태 사용
        for active_username in active_order:

            if (
                active_username.lower()
                == username.lower()
            ):
                username = active_username
                break

    for _ in range(distance):

        username = next_active_username(
            full_order,
            active_order,
            username,
        )

    return username


# =========================================================
# 날짜 출력
# =========================================================

def format_date(
    target: date,
) -> str:

    weekday = WEEKDAY_NAMES[
        target.weekday()
    ]

    return (
        f"{target.month:02d}-"
        f"{target.day:02d} "
        f"({weekday})"
    )


# =========================================================
# SVG escape
# =========================================================

def escape_svg(
    value: str,
) -> str:

    return html.escape(
        value,
        quote=True,
    )


# =========================================================
# SVG 생성
# =========================================================

def build_svg(
    problem_date: date,
    current_username: str,
    next_date: date,
    next_username: str,
    active_order: list[str],
    active_members: dict[str, str],
) -> str:

    current_name = active_members.get(
        current_username,
        current_username,
    )

    next_name = active_members.get(
        next_username,
        next_username,
    )

    rotation = " → ".join(
        active_members.get(
            username,
            username,
        )
        for username
        in active_order
    )

    current_date_text = escape_svg(
        format_date(problem_date)
    )

    next_date_text = escape_svg(
        format_date(next_date)
    )

    current_name_text = escape_svg(
        current_name
    )

    next_name_text = escape_svg(
        next_name
    )

    current_username_text = escape_svg(
        current_username
    )

    next_username_text = escape_svg(
        next_username
    )

    rotation_text = escape_svg(
        rotation
    )

    return f"""\
<svg
    xmlns="http://www.w3.org/2000/svg"
    width="1000"
    height="230"
    viewBox="0 0 1000 230"
    role="img"
    aria-labelledby="title description"
>
  <title id="title">문제 출제 담당</title>

  <desc id="description">
    현재 문제 출제 담당자와 다음 문제 출제 담당자
  </desc>

  <rect
      x="0"
      y="0"
      width="1000"
      height="230"
      rx="16"
      fill="#0d1117"
  />

  <text
      x="36"
      y="44"
      fill="#f0f6fc"
      font-size="22"
      font-weight="700"
      font-family="-apple-system, BlinkMacSystemFont, 'Segoe UI', sans-serif"
  >
    📌 문제 출제 담당
  </text>

  <text
      x="36"
      y="90"
      fill="#8b949e"
      font-size="17"
      font-family="-apple-system, BlinkMacSystemFont, 'Segoe UI', sans-serif"
  >
    현재 담당 · {current_date_text} 문제
  </text>

  <text
      x="335"
      y="90"
      fill="#f0f6fc"
      font-size="20"
      font-weight="700"
      font-family="-apple-system, BlinkMacSystemFont, 'Segoe UI', sans-serif"
  >
    {current_name_text} (@{current_username_text})
  </text>

  <text
      x="36"
      y="132"
      fill="#8b949e"
      font-size="17"
      font-family="-apple-system, BlinkMacSystemFont, 'Segoe UI', sans-serif"
  >
    다음 담당 · {next_date_text} 문제
  </text>

  <text
      x="335"
      y="132"
      fill="#f0f6fc"
      font-size="20"
      font-weight="700"
      font-family="-apple-system, BlinkMacSystemFont, 'Segoe UI', sans-serif"
  >
    {next_name_text} (@{next_username_text})
  </text>

  <line
      x1="36"
      y1="158"
      x2="964"
      y2="158"
      stroke="#30363d"
  />

  <text
      x="36"
      y="190"
      fill="#8b949e"
      font-size="14"
      font-family="-apple-system, BlinkMacSystemFont, 'Segoe UI', sans-serif"
  >
    평일 18:00 전환
  </text>

  <text
      x="170"
      y="190"
      fill="#c9d1d9"
      font-size="14"
      font-family="-apple-system, BlinkMacSystemFont, 'Segoe UI', sans-serif"
  >
    {rotation_text}
  </text>
</svg>
"""


# =========================================================
# 파일 변경 시에만 저장
# =========================================================

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


# =========================================================
# 실행
# =========================================================

def main() -> None:

    if not README_PATH.exists():

        raise FileNotFoundError(
            "README.md가 없습니다."
        )

    readme = README_PATH.read_text(
        encoding="utf-8"
    )

    # README는 읽기만 함
    active_members = get_active_members(
        readme
    )

    # JSON도 읽기만 함
    config = load_config()

    full_order = list(
        config["order"]
    )

    active_order = build_active_order(
        full_order,
        active_members,
    )

    current = now_kst()

    problem_date = current_problem_date(
        current
    )

    current_username = resolve_duty(
        config,
        active_order,
        problem_date,
    )

    next_date = next_weekday(
        problem_date
    )

    next_username = next_active_username(
        full_order,
        active_order,
        current_username,
    )

    svg = build_svg(
        problem_date,
        current_username,
        next_date,
        next_username,
        active_order,
        active_members,
    )

    changed = write_if_changed(
        SVG_PATH,
        svg,
    )

    print()
    print(
        "현재 담당: "
        f"{format_date(problem_date)} "
        f"{active_members.get(current_username, current_username)} "
        f"(@{current_username})"
    )

    print(
        "다음 담당: "
        f"{format_date(next_date)} "
        f"{active_members.get(next_username, next_username)} "
        f"(@{next_username})"
    )

    print()

    if changed:
        print(
            f"SVG 업데이트: {SVG_PATH}"
        )
    else:
        print(
            "SVG 변경 사항 없음"
        )


if __name__ == "__main__":
    main()
