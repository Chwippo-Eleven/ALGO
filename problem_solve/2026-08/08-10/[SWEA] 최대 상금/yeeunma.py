def dfs(num, count):
    global answer

    # 교환 횟수를 모두 사용
    if count == K:
        answer = max(answer, int(num))
        return

    # 현재 상태를 이미 같은 교환 횟수에서 방문했다면 종료
    if (num, count) in visited:
        return

    visited.add((num, count))

    # 교환할 두 자리 선택
    for i in range(len(num)):
        for j in range(i + 1, len(num)):
            num_list = list(num)

            # 두 자리 교환
            num_list[i], num_list[j] = num_list[j], num_list[i]

            next_num = ''.join(num_list)

            dfs(next_num, count + 1)


T = int(input())

for tc in range(1, T + 1):
    number, K = input().split()

    answer = 0
    visited = set()

    dfs(number, 0)

    print(f"#{tc} {answer}")
