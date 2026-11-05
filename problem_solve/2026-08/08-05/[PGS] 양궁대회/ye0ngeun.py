def solution(n, info):
    answer = [-1]
    max_diff = 0

    # 라이언의 화살 배치
    lion = [0] * 11

    # 완성된 화살 배치의 점수 차이 계산
    def get_score_diff():
        lion_score = 0
        apeach_score = 0

        for i in range(11):
            score = 10 - i

            if lion[i] == 0 and info[i] == 0:
                continue

            if lion[i] > info[i]:
                lion_score += score
            else:
                apeach_score += score

        return lion_score - apeach_score

    # index: 현재 결정할 점수판의 인덱스
    # used: 지금까지 사용한 화살 수
    def dfs(index, used):
        nonlocal answer, max_diff

        # index 0~9, 즉 10점부터 1점까지 모두 결정한 상태
        if index == 10:
            # 남은 화살은 전부 0점 칸에 넣기
            lion[10] = n - used

            diff = get_score_diff()

            # 라이언이 이긴 경우만 정답 후보
            if diff > 0:
                if diff > max_diff:
                    max_diff = diff
                    answer = lion[:]

                # 점수 차이가 같으면 낮은 점수에 더 많이 쏜 배치 선택
                elif diff == max_diff and lion[::-1] > answer[::-1]:
                    answer = lion[:]

            # 다음 경우를 위해 0점 칸 복구
            lion[10] = 0
            return

        needed = info[index] + 1

        # 1. 현재 점수를 가져가는 경우
        if used + needed <= n:
            lion[index] = needed
            dfs(index + 1, used + needed)

            # 현재 선택 취소
            lion[index] = 0

        # 2. 현재 점수를 포기하는 경우
        dfs(index + 1, used)

    dfs(0, 0)

    return answer
