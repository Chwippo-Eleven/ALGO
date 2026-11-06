def check(board,i,j,visited):
    if board[i][j] == board[i][j+1] == board[i+1][j] == board[i+1][j+1] and board[i][j] != ".":
        visited[i][j] = 1
        visited[i][j+1] = 1
        visited[i+1][j] = 1
        visited[i+1][j+1] = 1

def solution(m, n, board):
    answer = 0
    while True:
        board = [list(row) for row in board]
        visited = [[0]*n for _ in range(m)]
        for i in range(m-1):
            for j in range(n-1):
                check(board,i,j,visited)
        cnt = 0
        for i in range(m):
            for j in range(n):
                if visited[i][j]:
                    board[i][j] = '.'
                    cnt += 1
        answer += cnt
        temp = [['.']*n for _ in range(m)]
        for j in range(n):
            write = m-1
            for i in range(m-1,-1,-1):
                if board[i][j] != '.':
                    temp[write][j] = board[i][j]
                    write -= 1

        board = temp
        if cnt == 0:
            break
    return answer
