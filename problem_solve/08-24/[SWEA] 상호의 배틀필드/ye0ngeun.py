T = int(input())

# U, D, L, R
dx = [-1, 1, 0, 0]
dy = [0, 0, -1, 1]

direction = {
    '^': 0,
    'v': 1,
    '<': 2,
    '>': 3
}

tank_shape = ['^', 'v', '<', '>']

for tc in range(1, T + 1):
    H, W = map(int, input().split())
    field = [list(input()) for _ in range(H)]

    # 전차 위치와 방향 찾기
    x = y = d = 0

    for i in range(H):
        for j in range(W):
            if field[i][j] in direction:
                x, y = i, j
                d = direction[field[i][j]]

    N = int(input())
    commands = input()

    for command in commands:

        # 이동 명령
        if command in "UDLR":
            if command == 'U':
                d = 0
            elif command == 'D':
                d = 1
            elif command == 'L':
                d = 2
            else:
                d = 3

            # 이동 여부와 상관없이 방향은 먼저 변경
            field[x][y] = tank_shape[d]

            nx = x + dx[d]
            ny = y + dy[d]

            # 맵 안이고 평지일 때만 이동
            if 0 <= nx < H and 0 <= ny < W and field[nx][ny] == '.':
                field[x][y] = '.'
                x, y = nx, ny
                field[x][y] = tank_shape[d]

        # 포탄 발사
        else:
            nx = x + dx[d]
            ny = y + dy[d]

            while 0 <= nx < H and 0 <= ny < W:
                if field[nx][ny] == '*':
                    field[nx][ny] = '.'
                    break

                if field[nx][ny] == '#':
                    break

                nx += dx[d]
                ny += dy[d]

    print(f"#{tc}", end=" ")
    for row in field:
        print("".join(row))
