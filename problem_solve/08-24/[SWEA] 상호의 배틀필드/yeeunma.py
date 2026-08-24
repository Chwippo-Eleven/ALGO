T = int(input())

dx = [-1, 1, 0, 0]
dy = [0, 0, -1, 1]

direction = {
    '^': 0,
    'v': 1,
    '<': 2,
    '>': 3
}

# 이동 명령 -> 방향
move_dir = {
    'U': 0,
    'D': 1,
    'L': 2,
    'R': 3
}

for tc in range(1, T + 1):
    H, W = map(int, input().split())

    game_map = []
    tank_x = tank_y = 0
    tank_dir = 0

    # 맵 입력 + 전차 위치 찾기
    for i in range(H):
        row = list(input())
        game_map.append(row)

        for j in range(W):
            if row[j] in direction:
                tank_x = i
                tank_y = j
                tank_dir = direction[row[j]]

    N = int(input())
    commands = input()

    for command in commands:

        if command in move_dir:
            tank_dir = move_dir[command]

            nx = tank_x + dx[tank_dir]
            ny = tank_y + dy[tank_dir]

            # 맵 안 + 평지인 경우 이동
            if (0 <= nx < H and
                    0 <= ny < W and
                    game_map[nx][ny] == '.'):

                # 기존 위치
                game_map[tank_x][tank_y] = '.'

                tank_x = nx
                tank_y = ny

                # 새로운 위치에 전차 표시
                game_map[tank_x][tank_y] = '^v<> '[0]  # 임시

                # 방향에 맞는 전차 표시
                if tank_dir == 0:
                    game_map[tank_x][tank_y] = '^'
                elif tank_dir == 1:
                    game_map[tank_x][tank_y] = 'v'
                elif tank_dir == 2:
                    game_map[tank_x][tank_y] = '<'
                else:
                    game_map[tank_x][
        
        elif command == 'S':
            nx = tank_x + dx[tank_dir]
            ny = tank_y + dy[tank_dir]

            while 0 <= nx < H and 0 <= ny < W:

                # 벽돌
                if game_map[nx][ny] == '*':
                    game_map[nx][ny] = '.'
                    break

                # 강철
                elif game_map[nx][ny] == '#':
                    break

                # 그 외에는 계속 직진
                nx += dx[tank_dir]
                ny += dy[tank_dir]

    print(f'#{tc}', end=' ')
    for row in game_map:
        print(''.join(row))
