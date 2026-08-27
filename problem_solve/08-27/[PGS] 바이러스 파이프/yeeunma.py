from collections import deque

def solution(n, infection, edges, k):
    graph = [[] for _ in range(n + 1)]

    # 그래프 생성
    for a, b, t in edges:
        graph[a].append((b, t))
        graph[b].append((a, t))

    answer = 1

    # 파이프를 여는 순서를 DFS로 완전탐색
    def make_order(order):
        nonlocal answer

        # k번 선택했다면 감염 시뮬레이션
        if len(order) == k:
            infected = [False] * (n + 1)
            infected[infection] = True

            # 선택한 순서대로 파이프를 연다
            for pipe_type in order:
                queue = deque()

                # 현재 감염된 모든 배양체에서 시작
                for i in range(1, n + 1):
                    if infected[i]:
                        queue.append(i)

                visited = [False] * (n + 1)

                while queue:
                    now = queue.popleft()

                    if visited[now]:
                        continue

                    visited[now] = True

                    for nxt, edge_type in graph[now]:
                        # 현재 연 파이프 종류만 이동 가능
                        if edge_type != pipe_type:
                            continue

                        if infected[nxt]:
                            continue

                        infected[nxt] = True
                        queue.append(nxt)

            answer = max(answer, sum(infected))
            return

        # A, B, C 중 하나 선택
        for pipe_type in range(1, 4):
            order.append(pipe_type)
            make_order(order)
            order.pop()

    make_order([])

    return answer
