def solution(triangle):
    N = len(triangle)
    temp = [[0]*N for _ in range(N)]
    
    temp[0][0] = triangle[0][0]
    
    for i in range(1, N):
        for j in range(i + 1):
            if j == 0:
                temp[i][j] = temp[i-1][j] + triangle[i][j]
            elif j == i:
                temp[i][j] = temp[i-1][j-1] + triangle[i][j]
            else:
                temp[i][j] = max(temp[i-1][j-1], temp[i-1][j]) + triangle[i][j]

    return max(temp[N-1])
