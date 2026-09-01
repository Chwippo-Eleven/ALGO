def solution(info, edges):
    answer = 0

    # 1. 트리 만들기
    graph = [[] for _ in range(len(info))]

    for parent, child in edges:
        graph[parent].append(child)

    # 2. DFS
    def dfs(node, sheep, wolf, possible):
        nonlocal answer

        # 현재 노드가 양인지 늑대인지 확인
        if info[node] == 0:
            sheep += 1
        else:
            wolf += 1

        # 늑대가 양보다 많아지면 종료
        if wolf >= sheep:
            return

        # 지금까지 모은 양의 최대값
        answer = max(answer, sheep)

        # 현재 노드의 자식들을 다음 후보에 추가
        next_possible = possible.copy()

        for child in graph[node]:
            next_possible.append(child)

        # 다음에 갈 수 있는 노드를 하나씩 선택
        for next_node in next_possible:
            # 선택한 노드는 후보에서 제거
            new_possible = next_possible.copy()
            new_possible.remove(next_node)

            dfs(
                next_node,
                sheep,
                wolf,
                new_possible
            )

    # 0번부터 시작
    dfs(0, 0, 0, [])

    return answer
