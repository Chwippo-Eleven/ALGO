

import java.util.*;

class Solution {
    public int solution(int n, int[][] edge) {
        int answer = 0;

        //주석 달기
        Map<Integer, List<Integer>> map = new HashMap<>();
        for(int i=1;i<=n;i++){
            map.put(i, new ArrayList<>());
        }
        for(int[] e: edge){
            map.get(e[0]).add(e[1]);
            map.get(e[1]).add(e[0]);
        }
        
        Queue<int[]> queue = new ArrayDeque<>();
        boolean[]visited=new boolean[n+1];
        
        queue.offer(new int[]{1,1});
        visited[1]=true;
        
        int[]dist=new int[n+1];
        int max=Integer.MIN_VALUE;
        while(!queue.isEmpty()){
            int[] cur=queue.poll();
            
            int cd=cur[1];
            dist[cur[0]]=cd;
            max=Math.max(max, cd);
            for(int next:map.get(cur[0])){
                if(!visited[next]){
                    visited[next]=true;
                    queue.offer(new int[]{next,cd+1});
                }
            }
        }
        
        for(int value:dist){
            if(value==max)answer++;
        }
        return answer;
    }
    
    
}
