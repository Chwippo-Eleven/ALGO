from collections import Counter

def solution(k, t):
    count = Counter(t)
    counts = sorted(count.values(), reverse=True)
    answer = 0
    total = 0
    for cnt in counts:
        total += cnt
        answer += 1
        if total >= k:
            break

    return answer
