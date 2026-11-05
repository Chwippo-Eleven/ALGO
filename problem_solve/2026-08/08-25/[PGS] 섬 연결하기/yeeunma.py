def solution(n, costs):
    costs.sort(key=lambda x: x[2])
    parent = [i for i in range(n)]

    def find(x):
        if parent[x] != x:
            parent[x] = find(parent[x])
        return parent[x]

    def union(a, b):
        a = find(a)
        b = find(b)
        if a == b:
            return False
        parent[b] = a
        return True

    answer = 0
    count = 0

    for a, b, cost in costs:
        if union(a, b):
            answer += cost
            count += 1
            if count == n - 1:
                break
    return answer
