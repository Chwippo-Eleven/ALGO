import java.util.*;

class Solution {
    private int[][]map;
    private int answer = Integer.MIN_VALUE;
    public int solution(int n, int infection, int[][] edges, int k) {
        
        map=new int[n+1][n+1];

        for(int[]edge: edges){
            int x=edge[0];
            int y=edge[1];
            int type=edge[2];
            
            map[x][y]=type;
            map[y][x]=type;
        }
        
        boolean[]infected=new boolean[n+1];
        infected[infection]=true;
        
        dfs(n, 0, k, infected);

        return answer;
    }
    
    
    private void dfs(int n, int count, int k, boolean[] infected){
        if(count==k){
            int cnt=0;
            for(int i=1;i<=n;i++){
                if(infected[i]){
                    cnt++;
                }
            }
            answer=Math.max(answer, cnt);
            
            return;
        }

        for(int type=1;type<=3;type++){
            boolean[]next=infected.clone();
            for(int i=1;i<=n;i++){
                if(infected[i]){
                    bfs(i, n, type, next);
                }
            }
            
            dfs(n, count+1, k, next);
        }
    }
    
    private void bfs(int start, int n, int type, boolean[]infected){
        Queue<Integer>queue=new ArrayDeque<>();
        boolean[]visited=new boolean[n+1];
        queue.offer(start);
        
        while(!queue.isEmpty()){
            int cur=queue.poll();
            
            for(int i=1;i<=n;i++){
                if(map[cur][i]==type){
                    if(!visited[i]&&!infected[i]){
                        visited[i]=true;
                        queue.offer(i);
                        infected[i]=true;
                    }
                }
            }
        }
    }
}
