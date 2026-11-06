from collections import deque

def solution(rectangle, characterX, characterY, itemX, itemY):
    # 테두리 만들기 ( 좌표 2배 확장 )
    mx, my = 0, 0
    for r in rectangle:
        _, _, x, y = r
        mx, my = max(mx, x), max(my, y)
        
    width, height = mx * 2 + 2, my * 2 + 2
    
    arr = [[float('inf')] * width for _ in range(height)]
    
    
    for r in rectangle:
        rx, ry, hx, hy = r[0] * 2, r[1] * 2, r[2] * 2, r[3] * 2
        for x in range(rx, hx + 1):
            for y in range(ry, hy + 1):
                if (x == rx or x == hx or y == ry or y == hy):
                    if (arr[y][x] == float('inf')):
                        arr[y][x] = 1
                else:
                      arr[y][x] = 0
    
    # 최소 경로 구하기
    directions = [(1, 0), (0, -1), (-1, 0), (0, 1)]
    
    queue = deque()
    queue.append((characterY * 2, characterX * 2))
        
    while queue:
        y, x = queue.popleft()
        for dy, dx in directions:
            ny, nx = y + dy, x + dx
            
            if 0 <= nx < width and 0 <= ny < height and arr[ny][nx] == 1:
                arr[ny][nx] = arr[y][x] + 1
                queue.append((ny, nx))
                
    return arr[itemY * 2][itemX * 2] // 2
