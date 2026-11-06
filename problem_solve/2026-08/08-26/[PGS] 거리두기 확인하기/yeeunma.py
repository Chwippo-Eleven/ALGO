from collections import deque

def solution(places):
    answer = []

    for place in places:
        result = 1
        for r in range(5):
            for c in range(5):
                if place[r][c] != 'P':
                    continue
                queue = deque([(r, c, 0)])
                visited = [[False] * 5 for _ in range(5)]
                visited[r][c] = True
                while queue:
                    x, y, dist = queue.popleft()
                    if dist == 2:
                        continue
                    for dx, dy in [(1, 0), (-1, 0), (0, 1), (0, -1)]:
                        nx = x + dx
                        ny = y + dy
                        if not (0 <= nx < 5 and 0 <= ny < 5):
                            continue
                        if visited[nx][ny]:
                            continue
                        if place[nx][ny] == 'X':
                            continue
                        if place[nx][ny] == 'P':
                            result = 0
                            break
                        visited[nx][ny] = True
                        queue.append((nx, ny, dist + 1))
                    if result == 0:
                        break
                if result == 0:
                    break
            if result == 0:
                break
        answer.append(result)

    return answer
