import java.util.*;
class Solution {
    int min;
    public int solution(int n, int[][] wires) {
        min=n;
        
        List<Integer>[] map=new ArrayList[n+1];
        
        for(int i=1;i<=n;i++){
            map[i]=new ArrayList<>();
        }
        for(int []wire:wires){
            int a= wire[0];
            int b=wire[1];
            
            map[a].add(b);
            map[b].add(a);
        }
        
        boolean[] visited= new boolean[n+1];
        
        dfs(map, 1, visited);
        
        return min;
    }
    
    private int dfs(List<Integer>[] map, int current, boolean[] visited){
        visited[current]=true;
        int subtree=1;
        for(int next: map[current]){
            if(visited[next])continue;
            subtree+=dfs(map, next, visited);
        }
        int child=subtree;
        int other=map.length-1-child;
        
        
        if(current!=1) min=Math.min(min, Math.abs(child-other));
        
        return subtree;
    }
}
