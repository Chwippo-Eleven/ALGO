import sys
input = sys.stdin.readline

def check(film, D, W, K):
    #모든 열이 K개 이상 연속된 같은 값을 가지는지 확인
    for col in range(W):
        count = 1
        passed = False
        for row in range(1, D):
            if film[row][col] == film[row - 1][col]:
                count += 1
            else:
                count = 1
            if count >= K:
                passed = True
                break
        if not passed:
            return False
    return True
  
def solution(D, W, K, film):
    # K == 1이면 어떤 필름이든 통과
    if K == 1:
        return 0
    answer = K
  
    def dfs(row, changed):
        nonlocal answer
        # 이미 현재 최적해보다 많이 약품을 투입했다면
        if changed >= answer:
            return
        # 모든 행을 결정했으면 성능 검사
        if row == D:
            if check(film, D, W, K):
                answer = changed
            return
        # 1. 현재 행 그대로
        dfs(row + 1, changed)
        # 원래 행 백업
        original = film[row][:]
        # 2. 현재 행 전체를 A(0)로 변경
        film[row] = [0] * W
        dfs(row + 1, changed + 1)
        # 3. 현재 행 전체를 B(1)로 변경
        film[row] = [1] * W
        dfs(row + 1, changed + 1)
        # 원상복구
        film[row] = original
    
    dfs(0, 0)
    
    return answer


T = int(input())

for tc in range(1, T + 1):
    D, W, K = map(int, input().split())
    film = [list(map(int, input().split())) for _ in range(D)]
    
    result = solution(D, W, K, film)
    
    print(f"#{tc} {result}")
