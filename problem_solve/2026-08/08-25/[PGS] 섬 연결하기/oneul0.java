import java.util.*;
class Solution {
    class Pair implements Comparable<Pair>{
        int to, cost;
        Pair(int to, int cost){
            this.to = to;
            this.cost = cost;
        }
        
        @Override
        public int compareTo(Pair o){
            return this.cost - o.cost;
        }
    }
    List<List<Pair>> gr = new ArrayList<>();
    public int solution(int n, int[][] costs) {
        for(int i  =0; i<n; i++){
            gr.add(new ArrayList<>());
        }
        for(int[] c : costs){
            gr.get(c[0]).add(new Pair(c[1], c[2]));
            gr.get(c[1]).add(new Pair(c[0], c[2]));
        }
        
        return prim(n);
    }
    
    public int prim(int n){
        PriorityQueue<Pair> pq = new PriorityQueue<>();
        pq.offer(new Pair(0,0));
        boolean[] visited = new boolean[n];
        
        int result = 0, count = 0;
        while(!pq.isEmpty()){
            Pair cur = pq.poll();
            
            if(visited[cur.to]) continue;
            
            result += cur.cost;
            count++;
            visited[cur.to] = true;
            
            for(Pair next : gr.get(cur.to)){
                if(!visited[next.to]) pq.offer(next);
            }
            
            if(count >= n) break;
        }
        
        return result;
    }
}