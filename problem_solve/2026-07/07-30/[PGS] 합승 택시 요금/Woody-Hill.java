import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

class Solution {
    
    final int INF = Integer.MAX_VALUE;
    
    static class State implements Comparable<State> {
        int cost;
        int node;
        
        State(int cost, int node) {
            this.cost = cost;
            this.node = node;
        }
        
        public int compareTo(State o) {
            return this.cost - o.cost;
        }
    }
    
    static class Edge {
        int dest;
        int cost;
        
        Edge(int dest, int cost) {
            this.dest = dest;
            this.cost = cost;
        }
    }
    
    public int solution(int n, int s, int a, int b, int[][] fares) {
        
        List<Edge>[] graph = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }
        
        for (int[] fare : fares) {
            int c = fare[0], d = fare[1], f = fare[2];
            graph[c].add(new Edge(d, f));
            graph[d].add(new Edge(c, f));
        }
        
        int answer = INF;
        
        int[] startFare = dijkstra(n, s, graph);
        for (int cp = 1; cp <= n; cp++) {
            int[] checkpointFare = dijkstra(n, cp, graph);
            int x = startFare[cp] + checkpointFare[a] + checkpointFare[b];
            answer = Math.min(answer, x);
        }
        
        return answer;
    }
    
    private int[] dijkstra(int n, int s, List<Edge>[] graph) {
        
        int[] minFare = new int[n + 1];
        Arrays.fill(minFare, INF);
        
        PriorityQueue<State> pq = new PriorityQueue<>();
        
        minFare[s] = 0;
        pq.offer(new State(minFare[s], s));
        
        while (!pq.isEmpty()) {
            State current = pq.poll();
            int currCost = current.cost;
            int currNode = current.node;
            
            if (currCost > minFare[currNode]) {
                continue;
            }
            
            for (Edge e : graph[currNode]) {
                if (currCost + e.cost < minFare[e.dest]) {
                    minFare[e.dest] = currCost + e.cost;
                    pq.offer(new State(minFare[e.dest], e.dest));
                }
            }
        }
        
        return minFare;
    }
}
