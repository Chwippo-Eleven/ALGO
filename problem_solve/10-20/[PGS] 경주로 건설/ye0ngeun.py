import heapq

def solution(board):
    n = len(board)
    
    # 상하좌우
    dr = [-1, 1, 0, 0]
    dc = [0, 0, -1, 1]
    
    # 최소 비용 배열(r, c, direction)
    # 하나의 좌표에 도착하는 방향이 총 4개이기 때문에 최소비용을 4개 저장
    INF = float('inf')
    dist = [[[INF] * 4 for _ in range(n)] for _ in range(n)]
    
    # 우선순위 큐 (cost, r, c, direction)
    pq = []
    heapq.heappush(pq, (0, 0, 0, -1))
    
    # 최소 비용 탐색
    while pq:
        cost, r, c, direction = heapq.heappop(pq)
        
        # 이미 더 저렴한 경로가 발견되었다면 건너뛰기
        if direction != -1 and cost > dist[r][c][direction]:
            continue
        
        for nd in range(4):
            nr = r + dr[nd]
            nc = c + dc[nd]
            
            # 범위를 벗어난 경우
            if not (0 <= nr < n and 0 <= nc < n):
                continue
            
            # 벽인 경우
            if board[nr][nc] == 1:
                continue
        
            # 이동 비용 계산
            if direction == -1 or direction == nd:
                next_cost = cost + 100
            else:
                next_cost = cost + 600
            
            # 최소 비용 갱신
            if next_cost < dist[nr][nc][nd]:
                dist[nr][nc][nd] = next_cost
                heapq.heappush(pq, (next_cost, nr, nc, nd))
            
    answer = min(dist[n-1][n-1])
    
    return answer
