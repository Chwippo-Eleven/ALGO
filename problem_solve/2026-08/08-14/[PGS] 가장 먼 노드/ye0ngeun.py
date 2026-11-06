from collections import deque

def solution(n, edge):
    graph = [[] for _ in range(n + 1)]

    # 양방향 그래프 생성
    for a, b in edge:
        graph[a].append(b)
        graph[b].append(a)

    # 1번 노드로부터의 거리
    distance = [-1] * (n + 1)
    distance[1] = 0

    queue = deque([1])

    while queue:
        current = queue.popleft()

        for next_node in graph[current]:
            if distance[next_node] == -1:
                distance[next_node] = distance[current] + 1
                queue.append(next_node)

    max_distance = max(distance)

    return distance.count(max_distance)
