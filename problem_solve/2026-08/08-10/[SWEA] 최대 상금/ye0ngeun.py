T = int(input())

for tc in range(1, T + 1):
    num, K = input().split()

    numbers = list(num)
    K = int(K)

    answer = 0
    visited = [set() for _ in range(K + 1)]

    def dfs(depth):
        global answer

        state = ''.join(numbers)

        if state in visited[depth]:
            return

        visited[depth].add(state)

        if depth == K:
            answer = max(answer, int(state))
            return

        for i in range(len(numbers)):
            for j in range(i + 1, len(numbers)):
                numbers[i], numbers[j] = numbers[j], numbers[i]

                dfs(depth + 1)

                numbers[i], numbers[j] = numbers[j], numbers[i]

    dfs(0)

    print(f'#{tc} {answer}')
