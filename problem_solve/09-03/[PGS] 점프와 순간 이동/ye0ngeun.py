def solution(n):
    answer = 0

    while n > 0:
        # 짝수면 순간이동으로 가능
        if n % 2 == 0:
            n //= 2
        # 홀수면 점프해야됨
        else:
            n -= 1
            answer += 1

    return answer
