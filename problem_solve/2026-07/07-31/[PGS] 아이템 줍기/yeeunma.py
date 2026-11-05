from collections import deque

def solution(rectangle, characterX, characterY, itemX, itemY):
    # 빈 grid 구성 (y, x 순서로 접근하도록 102x102 배열)
    board = [[-1] * 102 for _ in range(102)]

    # 둘레 및 내부 구하기
    for r in rectangle:
        lx, ly, rx, ry = r[0] * 2, r[1] * 2, r[2] * 2, r[3] * 2
        for j in range(lx, rx + 1):  
            for k in range(ly, ry + 1):   
                if (lx < j < rx) and (ly < k < ry):
                    board[k][j] = 0 
                elif board[k][j] != 0:   
                    board[k][j] = 1  

    # BFS를 위한 큐 및 방문 배열 설정
    # (x, y, 이동거리)
    q = deque([(characterX * 2, characterY * 2, 0)])
    visited = [[False] * 102 for _ in range(102)]
    visited[characterY * 2][characterX * 2] = True
    
    dx = [-1, 1, 0, 0]
    dy = [0, 0, -1, 1]
    
    while q:
        x, y, dist = q.popleft()
        
        if x == itemX * 2 and y == itemY * 2:
            return dist // 2
        
        for i in range(4):
            nx, ny = x + dx[i], y + dy[i]
            if 0 <= nx < 102 and 0 <= ny < 102:
                if board[ny][nx] == 1 and not visited[ny][nx]:
                    visited[ny][nx] = True
                    q.append((nx, ny, dist + 1))

    return 0
