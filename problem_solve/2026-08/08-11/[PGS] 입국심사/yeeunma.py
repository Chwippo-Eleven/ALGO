def solution(n,times):
    left = 1
    right = n*max(times)
    while left < right:
        count = 0
        k = (left+right)//2
        for i in range(len(times)):
            count += k // times[i]
        if count >= n:
            right = k
        else:
            left = k+1
    return left
