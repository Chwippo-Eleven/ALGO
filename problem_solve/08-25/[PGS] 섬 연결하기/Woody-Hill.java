import java.util.Arrays;

class Solution {
    
    static class Edge implements Comparable<Edge> {
        int sNode;
        int eNode;
        int cost;
        
        Edge(int sNode, int eNode, int cost) {
            this.sNode = sNode;
            this.eNode = eNode;
            this.cost  = cost;
        }
        
        @Override
        public int compareTo(Edge other) {
            return Integer.compare(this.cost, other.cost);
        }   // 비용 기준 오름차순 정렬
    }
    
    // For Union-Find
    private int[] parent;
    private int[] rank;
    
    public int solution(int n, int[][] costs) {
        
        // Edge 배열에 값 담아두기
        Edge[] edges = new Edge[costs.length];
        
        for (int i = 0; i < costs.length; i++) {
            edges[i] = new Edge(costs[i][0], costs[i][1], costs[i][2]);
        }
        
        return kruskal(n, edges);  // Kruskal 알고리즘 수행
    }
    
    // Kruskal Algorithm (with Union-Find)
    private int kruskal(int n, Edge[] edges) {
        
        init(n);                // Union-Find 배열 초기화
        
        int mstCost = 0;
        int edgeCount = 0;
        
        Arrays.sort(edges);     // Edge 비용 오름차순 정렬
        
        for (Edge edge : edges) {   // 비용이 적은 edge부터
            
            // 이미 연결되어 있으면 다음으로!
            if (!union(edge.sNode, edge.eNode)) { continue; }
            
            mstCost += edge.cost;   // 비용 추가
            edgeCount += 1;         // 사용한 간선 개수 추가
            
            // MST의 간선 개수는 (n - 1)개이므로 그 이상 할 필요가 없다
            if (edgeCount == n - 1) { break; }
        }
        
        return mstCost;
    }
    
    // Union-Find 배열을 길이 n으로 초기화
    private void init(int n) {
        parent = new int[n];
        rank   = new int[n];
        
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            rank[i]   = 0;
        }
    }
    
    // Find: 경로 압축 & 부모 반환
    private int find(int x) {
        if (parent[x] == x) { return x; }
        
        parent[x] = find(parent[x]);
        return parent[x];
    }
    
    // Rank 기반 Union 함수
    // Union 성공 시 true, 이미 연결되어 있으면 false 반환
    private boolean union(int a, int b) {
        a = find(a);
        b = find(b);
        
        if (a == b) { return false; }
        
        if (rank[b] > rank[a]) {
            int temp = a;
            a = b;
            b = temp;
        }
        
        parent[b] = a;
        
        if (rank[a] == rank[b]) {
            rank[a] += 1;
        }
        
        return true;
    }
}
