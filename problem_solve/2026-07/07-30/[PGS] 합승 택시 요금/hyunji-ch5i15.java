import java.util.*;

class Solution {
    
    class Edge implements Comparable<Edge>{
        int dest;
        int cost;
        Edge(int dest, int cost) {
            this.dest = dest;
            this.cost = cost;
        }
        
        @Override
        public int compareTo(Edge o) {
            if (this.cost == o.cost) {
                return this.dest - o.dest;
            }
            return this.cost - o.cost;
        }
    }
    
    // 그래프 생성
    List<Edge>[] arr;
    boolean[] visited;
    public int solution(int n, int s, int a, int b, int[][] fares) {
        arr = new ArrayList[n+1];
        visited = new boolean[n+1];
        for (int i=1; i<=n; i++) {
            arr[i] = new ArrayList<>();
        }
        
        for (int i=0; i<fares.length; i++) {
            int start = fares[i][0];
            int end = fares[i][1];
            int cost = fares[i][2];
            // 양방향 그래프 
            arr[start].add(new Edge(end,cost));
            arr[end].add(new Edge(start,cost));
        }
        
        int[] dp = new int[n+1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        daijkstra(s, dp);
        
        // 1. 시작점으로부터, 따로 갔을 때 값
        int alone = dp[a] + dp[b];
         
        // 2. 같이 갔을 때  
        // S -> 임의의 점 -> A
        // S -> 임의의 점 -> B
        // S -> dp[임의의 점] + 임의의 점에서 시작한 dp[A] + 임의의 점에서 시작한 dp[B]
        int answer = Integer.MAX_VALUE;
        // 임의의 점에서 시작
        for (int i=1; i<=n; i++) {
            int[] dp2 = new int[n+1];
            Arrays.fill(dp2, Integer.MAX_VALUE);
            // 임의의 시작 점 i
            daijkstra(i, dp2);
            int temp = dp[i] + dp2[a] + dp2[b];
            answer = Math.min(answer, temp);
        }
        
        return alone>answer?answer:alone;
    }
    
    private void daijkstra(int s, int[] dp) {
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        pq.offer(new Edge(s,0));
        dp[s] = 0;
        
        while(!pq.isEmpty()) {
            Edge cur = pq.poll();
            int current = cur.dest;
            // 빠른 스킵
            if (cur.cost > dp[current]) continue;
            
            for (Edge next: arr[current]) {
                int dest = next.dest;
                int cost = next.cost;    
                if (dp[dest] > dp[current] + cost) {
                    dp[dest] = dp[current] + cost;
                    pq.offer(new Edge(dest, dp[dest]));
                }
                
            }
        }
    }
    
}