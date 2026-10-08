from itertools import permutations

def solution(numbers):
    candidates = set()

    for r in range(1, len(numbers) + 1):
        for p in permutations(numbers, r):
            num = int(''.join(p))
            candidates.add(num)

    answer = 0

    for num in candidates:
        if is_prime(num):
            answer += 1

    return answer


def is_prime(n):
    if n < 2:
        return False

    i = 2
    while i * i <= n:
        if n % i == 0:
            return False
        i += 1

    return True
