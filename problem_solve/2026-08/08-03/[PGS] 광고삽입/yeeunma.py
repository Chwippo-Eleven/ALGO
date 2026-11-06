def time_to_sec(time):
    h, m, s = map(int, time.split(":"))
    return h * 3600 + m * 60 + s


def sec_to_time(sec):
    h = sec // 3600
    sec %= 3600
    m = sec // 60
    s = sec % 60
    return f"{h:02d}:{m:02d}:{s:02d}"


def solution(play_time, adv_time, logs): # 전체 시간, 광고 시간, 시청기록

    play = time_to_sec(play_time)
    adv = time_to_sec(adv_time)

    # 배열 인덱스가 0부터 시작하므로 +1
    timeline = [0] * (play + 1)

    # 시작은 +1, 끝은 -1
    for log in logs:
        start, end = log.split("-")
        start = time_to_sec(start)
        end = time_to_sec(end)

        timeline[start] += 1
        timeline[end] -= 1

    # 첫 번째 누적합
    # -> 각 초의 시청자 수
    for i in range(1, play + 1):
        timeline[i] += timeline[i - 1]

    # 두 번째 누적합
    # -> 0초부터 현재까지의 총 시청 시간
    for i in range(1, play + 1):
        timeline[i] += timeline[i - 1]

    # 광고를 0초부터 시작했을 때의 시청시간
    max_watch = timeline[adv - 1]
    best_start = 0

    # 광고 시작 위치를 하나씩 이동
    for start in range(1, play - adv + 1):

        end = start + adv - 1

        watch = timeline[end] - timeline[start - 1]

        if watch > max_watch:
            max_watch = watch
            best_start = start

    return sec_to_time(best_start)
