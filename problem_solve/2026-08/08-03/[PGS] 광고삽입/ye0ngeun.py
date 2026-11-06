def solution(play_time, adv_time, logs):
    # "HH:MM:SS" -> 초
    def to_seconds(time):
        h, m, s = map(int, time.split(":"))
        return h * 3600 + m * 60 + s

    # 초 -> "HH:MM:SS"
    def to_time(seconds):
        h = seconds // 3600
        seconds %= 3600
        m = seconds // 60
        s = seconds % 60

        return f"{h:02d}:{m:02d}:{s:02d}"

    play_seconds = to_seconds(play_time)
    adv_seconds = to_seconds(adv_time)

    # 종료 시각이 play_seconds일 수도 있으므로 +1 크기로 생성
    viewers = [0] * (play_seconds + 1)

    # 시작 시각 +1, 종료 시각 -1 기록 ( 차분배열 )
    for log in logs:
        start, end = log.split("-")
        start = to_seconds(start)
        end = to_seconds(end)

        viewers[start] += 1
        viewers[end] -= 1

    # 1차 누적합 : 각 초의 시청자 수
    for second in range(1, play_seconds):
        viewers[second] += viewers[second - 1]

    # 2차 누적합 : 0초부터 i초 직전까지의 누적 시청 시간
    prefix = [0] * (play_seconds + 1)

    for second in range(play_seconds):
        prefix[second + 1] = prefix[second] + viewers[second]

    # 광고를 0초에 넣었을 때의 누적 시청 시간
    max_watch_time = prefix[adv_seconds]
    answer = 0

    # 광고 시작 시각을 한 초씩 이동
    for start in range(1, play_seconds - adv_seconds + 1):
        end = start + adv_seconds

        # [start, end) 구간의 누적 시청 시간
        current_watch_time = prefix[end] - prefix[start]

        # 가장 빠른 시각 유지
        if current_watch_time > max_watch_time:
            max_watch_time = current_watch_time
            answer = start

    return to_time(answer)
