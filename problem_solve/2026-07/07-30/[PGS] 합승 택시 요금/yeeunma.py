import heapq

INF = int(1e9)

def dijkstra(start, graph, n):
    dist = [INF] * (n + 1)
    dist[start] = 0

    pq = []
    heapq.heappush(pq, (0, start))

    while pq:
        cost, now = heapq.heappop(pq)

        if dist[now] < cost:
            continue

        for nxt, w in graph[now]:
            new_cost = cost + w

            if new_cost < dist[nxt]:
                dist[nxt] = new_cost
                heapq.heappush(pq, (new_cost, nxt))

    return dist


def solution(n, s, a, b, fares):
    graph = [[] for _ in range(n + 1)]

    for x, y, cost in fares:
        graph[x].append((y, cost))
        graph[y].append((x, cost))

    dist_s = dijkstra(s, graph, n)
    dist_a = dijkstra(a, graph, n)
    dist_b = dijkstra(b, graph, n)

    answer = INF

    for k in range(1, n + 1):
        answer = min(answer,
                     dist_s[k] + dist_a[k] + dist_b[k])

    return answer
