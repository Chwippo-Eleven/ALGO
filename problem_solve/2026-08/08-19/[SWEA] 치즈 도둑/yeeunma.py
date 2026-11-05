import sys
sys.setrecursionlimit(100000)

def dfs(x, y, day):
    visited[x][y] = True
    for dx, dy in [(0, 1), (1, 0), (0, -1), (-1, 0)]:
        nx = x + dx
        ny = y + dy
        if 0 <= nx < N and 0 <= ny < N:
            if cheese[nx][ny] > day and not visited[nx][ny]:
                dfs(nx, ny, day)


T = int(input())

for tc in range(1, T + 1):
    N = int(input())
    cheese = [list(map(int, input().split())) for _ in range(N)]
    answer = 1

    for day in range(1, 101):
        visited = [[False] * N for _ in range(N)]
        count = 0
        for i in range(N):
            for j in range(N):
                if cheese[i][j] > day and not visited[i][j]:
                    dfs(i, j, day)
                    count += 1

        answer = max(answer, count)

    print(f"#{tc} {answer}")
