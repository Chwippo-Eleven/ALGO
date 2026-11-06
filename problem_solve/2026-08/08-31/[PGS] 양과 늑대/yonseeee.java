import java.util.*;

class Solution {
    public int solution(int[] info, int[][] edges) {
        int answer = 0;
        
        boolean[] visited=new boolean[info.length];
        visited[0]=true;
        
        return dfs(info, edges, visited, 0, 1);
    }
    
    private int dfs(int[] info, int[][] edges, boolean[] visited, int wolf, int sheep){
        if(wolf>=sheep){
            return sheep;
        }
        
        int max=sheep;
        
        for(int[] edge: edges){
            int parent=edge[0];
            int child=edge[1];
            
            if(visited[parent]&&!visited[child]){
                visited[child]=true;
                
                if(info[child]==1){//늑대
                    max=Math.max(max, dfs(info, edges, visited, wolf+1, sheep));
                }else{//양
                    max=Math.max(max, dfs(info, edges, visited, wolf, sheep+1));
                }
                
                visited[child]=false;
            }
        
        }
        
        return max;
    }
}
