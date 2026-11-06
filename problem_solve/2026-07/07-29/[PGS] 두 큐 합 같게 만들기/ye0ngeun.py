def solution(queue1, queue2):
    numbers = queue1 + queue2
    total = sum(numbers)
    
    if total % 2 == 1:
        return -1
    
    target = total // 2
    
    # 원형처럼 탐색하기 위해 한번 더 이어붙이기
    numbers = numbers * 2
    
    start = 0
    end = len(queue1)
    
    current_sum = sum(queue1)
    count = 0
    
    while start < len(numbers) and end < len(numbers):
        if current_sum == target:
            return count
        
        if current_sum < target:
            # q2의 맨 앞 원소를 q1에 삽입
            current_sum += numbers[end]
            end += 1
        else:
            # q1 맨 앞 원소 빼기
            current_sum -= numbers[start]
            start += 1
        
        count += 1

    return -1
