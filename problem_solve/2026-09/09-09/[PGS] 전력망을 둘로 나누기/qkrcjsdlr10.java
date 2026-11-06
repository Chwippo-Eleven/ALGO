import java.util.*;

class Solution {
    public int solution(int n, int[][] wires) {
        int answer = Integer.MAX_VALUE;
        
        List<List<Integer>> graph = new ArrayList<>();
        Deque<Integer> dq = new ArrayDeque<>();
        
        
        for(int i = 0; i <= n; i++){
            graph.add(new ArrayList<>());
        }
        
        for(int[] wire : wires){
            int u = wire[0];
            int v = wire[1];
            
            graph.get(u).add(v);
            graph.get(v).add(u);
        }
        
        for(int[] wire : wires){
            boolean[] vis = new boolean[n + 1];
            int u = wire[0];
            int v = wire[1];
            
            graph.get(u).remove(Integer.valueOf(v));
            graph.get(v).remove(Integer.valueOf(u));
            
            int cnt1 = 0;
            int cnt2 = 0;
            
            for(int i = 1; i <= n; i++){
                if(vis[i]) continue;
                vis[i] = true;
                int curCnt = 0;
                
                dq.addLast(i);
                
                while(!dq.isEmpty()){
                    int cur = dq.pollFirst();
                    curCnt++;
                    for(int next : graph.get(cur)){
                        if(vis[next]) continue;
                        vis[next] = true;
                        dq.addLast(next);
                    }
                }
                if(cnt1 == 0){
                    cnt1 = curCnt;
                }else{
                    cnt2 = curCnt;
                }
            }
            answer = Math.min(answer, Math.abs(cnt1 - cnt2));
            graph.get(u).add(v);
            graph.get(v).add(u);
        }
        
        
        
        return answer;
    }
}