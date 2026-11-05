def solution(n, q, ans):
    answer = 0

    def dfs(start, code):
        # 숫자 5개를 골랐는지 확인
        if len(code) < 5:
        # 5개가 아니라면 start부터 n까지 숫자를 하나 선택
            code.append() # ?????
        
        # 5개를 골랐다면 모든 q와 비교해서 조건 검사
        else:
            for i in range(len(q)):
                for j in range(5):
                    if q#?????

        # 전부 만족하면 answer 증가
        answer += 1

    dfs(1, [])

    return answer
