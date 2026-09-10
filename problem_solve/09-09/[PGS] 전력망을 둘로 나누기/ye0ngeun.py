def solution(n, wires):
    graph = [[] for _ in range(n+1)]
    
    for v1, v2 in wires:
        graph[v1].append(v2)
        graph[v2].append(v1)
    
    # 두 전력망 송전탑 개수의 차이는 최대 n을 넘지 않음
    answer = n
    
    def dfs(node, cut_v1, cut_v2):
        visited[node] = True
        cnt = 1
    
        for next_node in graph[node]:
            # 끊은 전선은 탐색하지 않음
            if ((node == cut_v1 and next_node == cut_v2) 
                or (node == cut_v2 and next_node == cut_v1)):
                continue
            
            if not visited[next_node]:
                cnt += dfs(next_node, cut_v1, cut_v2)
    
        return cnt

    for v1, v2 in wires:        
        visited = [False] * (n+1)
        cnt = dfs(v1, v1, v2)
        # 한쪽이 cnt개라면 다른 한쪽은 n-cnt개
        diff = abs(cnt - (n - cnt))
        answer = min(answer, diff)
        
    return answer
            
