from itertools import combinations

def solution(n, q, ans):
    q_ans = list(zip(q, ans))
    answer = 0

    for candidate in combinations(range(1, n+1), 5):
        possible = True
        
        for q, ans in q_ans:
            cnt = 0
            for num in q:
                if num in candidate:
                    cnt += 1
            if cnt != ans:
                possible = False
                break
                
        if possible:
            answer += 1
    
    return answer
