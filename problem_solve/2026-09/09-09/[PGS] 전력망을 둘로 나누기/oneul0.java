import java.util.*;
class Solution {
    List<List<Integer>> gr = new ArrayList<>();
    public int solution(int n, int[][] wires) {
        for(int i = 0; i<=n; i++){
            gr.add(new ArrayList<>());
        }
        for(int[] w : wires){
            gr.get(w[0]).add(w[1]);
            gr.get(w[1]).add(w[0]);
        }
        int answer = 987654321;
        //하나 정해서 끊기
        //정점 하나 체크하면 간선도 다 끊기는 효과니까
        for(int i =1; i<=n; i++){
            boolean[] visited = new boolean[n+1];
            visited[i] = true;
            int o = bfs(visited);
            answer = Math.min(answer, Math.abs(o+o-n));
        }
        
        return answer;
    }
    public int bfs(boolean[] visited){
        Queue<Integer> q = new ArrayDeque<>();
        q.offer(1);
        visited[1] = true;
        int result = 1;
        while(!q.isEmpty()){
            int cur = q.poll();
            
            for(int next : gr.get(cur)){
                if(!visited[next]){
                    q.offer(next);
                    visited[next] = true;
                    result++;
                }
            }
        }
        return result;
    }
}