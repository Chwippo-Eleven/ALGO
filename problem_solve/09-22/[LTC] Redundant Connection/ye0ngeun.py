class Solution:
    def findRedundantConnection(self, edges: list[list[int]]) -> list[int]:
        parent = [i for i in range(len(edges) + 1)]
        
        def find(x):
            if parent[x] == x:
                return x

            parent[x] = find(parent[x])
            return parent[x]
        
        def union(a, b):
            root_a = find(a)
            root_b = find(b)

            parent[root_b] = root_a
        
        for a, b in edges:
            if find(a) == find(b):
                return [a, b]
            
            union(a, b)
