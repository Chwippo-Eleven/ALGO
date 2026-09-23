import java.util.*;

class Solution {
    private static List<Integer>[] graph;
    public int[] findRedundantConnection(int[][] edges) {
        int n = edges.length;


        graph=new ArrayList[n+1];
        for(int i=1;i<=n;i++){
            graph[i]=new ArrayList<>();
        } 
        for(int[] edge: edges){
            int a = edge[0], b=edge[1];

            graph[a].add(b);
            graph[b].add(a);
        }

        for(int i=n-1;i>=0;i--){
            int a = edges[i][0];
            int b = edges[i][1];

            graph[a].remove(Integer.valueOf(b));
            graph[b].remove(Integer.valueOf(a));

            boolean[] visited=new boolean[n+1];
            int cnt=0;
            for(int j=1;j<=n;j++){
                if(!visited[j]){
                    dfs(j, visited);
                    cnt++;
                }
            }
            if(cnt==1)return edges[i];

            graph[a].add(b);
            graph[b].add(a);

        }

        return new int[2];
    }
    private static void dfs(int cur, boolean[] visited){
        visited[cur]=true;
        for(int next:graph[cur]){
            if(visited[next])continue;
            dfs(next, visited);
        }
    }
}
