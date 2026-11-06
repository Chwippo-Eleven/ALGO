import java.util.*;

class Solution {
    public int solution(int n, int[][] edge) {
        int answer = 0;
        
        ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
        Deque<Integer> dq = new ArrayDeque<>();
        
        int[] dist = new int[n + 1];
        Arrays.fill(dist, -1);
        dist[1] = 0;
        for(int i = 0; i <= n; i++){
            graph.add(new ArrayList<>());
        }
        
        for(int[] vertex : edge){
            int u = vertex[0];
            int v = vertex[1];
            
            graph.get(u).add(v);
            graph.get(v).add(u);
        }
        
        dq.add(1);
        
        while(!dq.isEmpty()){
            int cur = dq.pollFirst();
            
            for(int next : graph.get(cur)){
                if(dist[next] != -1) continue;
                dq.add(next);
                dist[next] = dist[cur] + 1;
            }
        }
        
        int maxDist = 0;
        int count = 0;

        for (int i = 1; i <= n; i++) {
            if (dist[i] > maxDist) {
                maxDist = dist[i];
                count = 1;
            } else if (dist[i] == maxDist) {
                count++;
            }
        }
        
        answer = count;
        
        return answer;
    }
}