def solution(n, costs):
    parent = [i for i in range(n)]

    def find(x):
        if parent[x] == x:
            return x
        return find(parent[x])

    def union(a, b):
        root_a = find(a)
        root_b = find(b)

        parent[root_b] = root_a

    costs.sort(key=lambda x: x[2])

    answer = 0

    for a, b, cost in costs:
        if find(a) != find(b):
            union(a, b)
            answer += cost

    return answer
