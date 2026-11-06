import math

def solution(numbers):
    answer = 0
    candidates = set()
    result = []
    
    def permutation(cnt, r):
        if cnt == r:
            candidates.add(int(''.join(result)))
            return
        
        for i in range(len(numbers)):
            if visited[i]:
                continue
            
            visited[i] = True
            result.append(numbers[i])
            
            permutation(cnt + 1, r)
            
            visited[i] = False
            result.pop()
    
    def is_prime(number):
        if number == 0 or number == 1:
            return False
        for i in range(2, int(math.sqrt(number)) + 1):
            if number % i == 0:
                return False
        return True
    
    for r in range(1, len(numbers) + 1):
        visited = [False] * len(numbers)
        permutation(0, r)
    
    for candidate in candidates:
        if is_prime(candidate):
            answer += 1
    
    return answer
