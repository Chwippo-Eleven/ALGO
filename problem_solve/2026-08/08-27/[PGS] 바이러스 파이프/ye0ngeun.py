from collections import deque

def solution(n, infection, edges, k):
    graph = [[] for _ in range(n + 1)]
    
    # 그래프 생성 (연결된 노드, 파이프 타입)
    for a, b, pipe_type in edges:
        graph[a].append((b, pipe_type))
        graph[b].append((a, pipe_type))
        
    answer = 1
    
    def spread(infected, opened_pipe):
        next_infected = infected[:]
        queue = deque()
        
        # 현재 감염된 모든 노드를 시작점으로 설정
        for node in range(1, n + 1):
            if next_infected[node]:
                queue.append(node)
        
        while queue:
            current = queue.popleft()
            
            for next_node, pipe_type in graph[current]:
                # 이번에 연 파이프만 가능
                if pipe_type != opened_pipe:
                    continue
                # 이미 감염된 노드는 제외
                if next_infected[next_node]:
                    continue
        
                next_infected[next_node] = True
                queue.append(next_node)
        
        return next_infected
    
    # 파이프 개방 순서를 DFS로 완전탐색
    def dfs(depth, infected, prev_pipe):
        nonlocal answer
        
        infected_count = sum(infected)
        answer = max(answer, infected_count)
        
        # 모든 노드가 감염되었으면 이미 최댓값 도달로 종료
        if infected_count == n:
            return
        
        # k번 개방했으면 종료
        if depth == k:
            return
        
        for pipe_type in [1, 2, 3]:
            # 같은 파이프를 연속으로 여는 경우 제외
            if pipe_type == prev_pipe:
                continue
        
            next_infected = spread(infected, pipe_type)
            dfs(depth + 1, next_infected, pipe_type)
    
    # 최초 감염 상태
    infected = [False] * (n + 1)
    infected[infection] = True
    
    dfs(0, infected, 0)
    
    return answer
