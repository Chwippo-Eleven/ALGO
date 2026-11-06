def solution(n, times):
    # 주어진 시간 안에 모든 심사관이 n명 이상을 처리할 수 있는지 확인
    def is_possible(best_time):
        total = 0
        for t in times:
            # 심사관 한 명이 best_time 동안 처리할 수 있는 사람 수
            total += best_time // t
        return total >= n
    
    # 정답(최소 심사 시간)을 이분 탐색
    # 가장 느린 심사관이 혼자 n명을 처리하는 시간을 최대 범위로 설정
    lo, hi = 1, max(times) * n
    answer = hi
    
    while lo <= hi:
        mid = (lo + hi) // 2
        if is_possible(mid):
            # mid 시간 안에 처리가 가능하므로
            # 현재 값을 후보로 저장하고 더 짧은 시간이 가능한지 탐색
            answer = mid
            hi = mid -1
        else:
            # mid 시간으로는 부족하므로 더 긴 시간 탐색
            lo = mid + 1
    return answer
