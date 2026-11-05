import java.util.*;

class Solution {
    
    static class Node implements Comparable<Node>{
        int to;
        int cost;
        
        Node(int to, int cost){
            this.to = to;
            this.cost = cost;
        }
        
        @Override
        public int compareTo(Node o) {
            return this.cost - o.cost;
        }
    }
    
    static int[] dijkstra(int start, int n, ArrayList<Node>[] graph){
        PriorityQueue<Node> pq = new PriorityQueue<>();
        int[] dist = new int[n + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        
        dist[start] = 0;
        
        pq.offer(new Node(start, 0));
        
        while(!pq.isEmpty()){
            Node cur = pq.poll();
            
            int curNode = cur.to;
            int curCost = cur.cost;
            
            if(dist[curNode] < curCost){
                continue;
            }
            
            for(Node next : graph[curNode]){
                int nextCost = next.cost + curCost;
                
                if(nextCost < dist[next.to]){
                    dist[next.to] = nextCost;
                    pq.offer(new Node(next.to, nextCost));
                }
            }
        }
        
        return dist;
    }
    
    public int solution(int n, int s, int a, int b, int[][] fares) {
        int answer = Integer.MAX_VALUE;
        
        ArrayList<Node>[] graph = new ArrayList[n + 1];
        
        
        for(int i = 1; i <= n; i++){
            graph[i] = new ArrayList<>();
        }
        
        for(int i = 0; i < fares.length; i++){
            graph[fares[i][0]].add(new Node(fares[i][1], fares[i][2]));
            graph[fares[i][1]].add(new Node(fares[i][0], fares[i][2]));
        }
        
        int[] dijkstraS = dijkstra(s, n, graph);
        int[] dijkstraA = dijkstra(a, n, graph);
        int[] dijkstraB = dijkstra(b, n, graph);
        
        for(int i = 1; i <= n; i++){
            int totalCost = dijkstraS[i] + dijkstraA[i] + dijkstraB[i];
            
            answer = Math.min(answer, totalCost);
        }
        
        
        return answer;
    }
}