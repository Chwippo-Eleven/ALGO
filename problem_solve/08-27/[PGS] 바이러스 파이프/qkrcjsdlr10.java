import java.util.*;

class Solution {
    
    int res = 0;
    boolean[] vis;
    
    List<Node>[] graph;
    
    class Node{
        int from;
        int to;
        int edge;
        
        Node(int from, int to, int edge){
            this.from = from;
            this.to = to;
            this.edge = edge;
        }
    }
    
    void dfs(int depth, int k, int prePipe, int n){
        if(depth == k){
            int cnt = 0;
            for(boolean x : vis){
                if(x){
                    cnt++;
                }
            }
            
            res = Math.max(res, cnt);
            
            return;
        }
        
        for(int pipe = 1; pipe <= 3; pipe++){
            if(pipe == prePipe) continue;
            
            boolean[] backup = vis.clone();
            
            bfs(pipe, n);
            dfs(depth + 1, k, pipe, n);
            
            vis = backup;
        }
    }
    
    void bfs(int pipe, int n){
        Deque<Integer> q = new ArrayDeque<>();
        
        for(int i = 1; i <= n; i++){
            if(!vis[i]) continue;
            q.addLast(i);
        }
        
        while(!q.isEmpty()){
            int cur = q.pollFirst();
            for(Node next : graph[cur]){
                if(next.edge != pipe) continue;
                if (vis[next.to]) continue;
                vis[next.to] = true;
                q.addLast(next.to);
            }
        }
    }
    
    public int solution(int n, int infection, int[][] edges, int k) {
        int answer = 0;
        vis = new boolean[n + 1];
        graph = new ArrayList[n + 1];
        
        for(int i = 1; i <= n; i++){
            graph[i] = new ArrayList<>();
        } 
        
        for(int[] node : edges){
            int from = node[0];
            int to = node[1];
            int edge = node[2];
            
            graph[from].add(new Node(from, to, edge));
            graph[to].add(new Node(to, from, edge));
        }
        
        vis[infection] = true;
        
        dfs(0, k, 0, n);
        
        answer = res;
        
        return answer;
    }
}