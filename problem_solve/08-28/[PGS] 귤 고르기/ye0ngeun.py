from collections import Counter

def solution(k, tangerine):
    total = 0
    answer = 0
    counts = sorted(Counter(tangerine).values(), reverse=True)
    
    for count in counts:
        total += count
        answer += 1
        
        if total >= k:
            break

    return answer
