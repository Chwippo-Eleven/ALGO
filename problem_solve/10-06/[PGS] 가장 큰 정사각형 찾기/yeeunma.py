def solution(board):
    N = len(board)
    M = len(board[0])

    dp = [[0] * M for _ in range(N)]

    for i in range(N):
        for j in range(M):
            if board[i][j] == 0:
                continue
            if i == 0 or j == 0:
                dp[i][j] = 1
            else:
                dp[i][j] = min(dp[i-1][j], dp[i][j-1], dp[i-1][j-1]) + 1

    max_length = max(max(row) for row in dp)

    return max_length * max_length
