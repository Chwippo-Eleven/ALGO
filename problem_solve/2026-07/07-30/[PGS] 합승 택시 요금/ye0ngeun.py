import heapq

def solution(n, s, a, b, fares):

    graph = [[] for _ in range(n + 1)]

    for start, end, cost in fares:
        graph[start].append((end, cost))
        graph[end].append((start, cost))

    def dijkstra(start):
        INF = float("inf")
        distance = [INF] * (n + 1)
        distance[start] = 0

        pq = [(0, start)]

        while pq:
            current_cost, current_node = heapq.heappop(pq)

            if current_cost > distance[current_node]:
                continue

            for next_node, next_cost in graph[current_node]:
                new_cost = current_cost + next_cost

                if new_cost < distance[next_node]:
                    distance[next_node] = new_cost
                    heapq.heappush(pq, (new_cost, next_node))

        return distance

    distS = dijkstra(s)
    distA = dijkstra(a)
    distB = dijkstra(b)

    answer = float("inf")

    for m in range(1, n + 1):
        answer = min(
            answer,
            distS[m] + distA[m] + distB[m]
        )

    return answer
