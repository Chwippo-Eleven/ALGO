import java.util.*;

class Solution {
    List<List<Integer>> winGraph = new ArrayList<>();
    List<List<Integer>> loseGraph = new ArrayList<>();
    
    boolean[] vis;
    int answer = 0;
    
    
    public int dfs(List<List<Integer>> graph, int cur){
        int cnt = 0;
        
        for(int next : graph.get(cur)){
            if(vis[next]) continue;
            vis[next] = true;
            cnt += 1 + dfs(graph, next);
        }
        
        return cnt;
    }
    
    public int solution(int n, int[][] results) {
        for (int i = 0; i <= n; i++) {
            winGraph.add(new ArrayList<>());
            loseGraph.add(new ArrayList<>());
        }
        
        for(int[] result : results){
            int winner = result[0];
            int loser = result[1];
            
            winGraph.get(winner).add(loser);
            loseGraph.get(loser).add(winner);
        }
        
        for(int player = 1; player <= n; player++){
            vis = new boolean[n + 1];
            vis[player] = true;
            int winCount = dfs(winGraph, player);
            vis = new boolean[n + 1];
            vis[player] = true;
            int loseCnt = dfs(loseGraph, player);
            
            
            if(winCount + loseCnt == n-1){
                answer++;
            }
        }
        
        
        return answer;
    }
}