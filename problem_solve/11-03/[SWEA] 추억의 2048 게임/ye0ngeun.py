T = int(input())

for test_case in range(1, T + 1):
    N, S = input().split()
    N = int(N)

    board = [list(map(int, input().split())) for _ in range(N)]

    def merge(line):
        numbers = [num for num in line if num != 0]
        result = []

        i = 0
        while i < len(numbers):
            if i + 1 < len(numbers) and numbers[i] == numbers[i+1]:
                result.append(numbers[i] * 2)
                i += 2
            else:
                result.append(numbers[i])
                i += 1

        result += [0] * (N - len(result))
        return result

    if S == 'up':
        for c in range(N):
            line = [board[r][c] for r in range(N)]
            merged = merge(line)

            for r in range(N):
                board[r][c] = merged[r]

    elif S == 'down':
        for c in range(N):
            line = [board[r][c] for r in range(N-1, -1, -1)]
            merged = merge(line)

            for r in range(N):
                board[N-1-r][c] = merged[r]

    elif S == 'left':
        for r in range(N):
            board[r] = merge(board[r])

    elif S == 'right':
        for r in range(N):
            board[r] = merge(board[r][::-1])[::-1]

    print(f'#{test_case}')
    for row in board:
        print(*row)
