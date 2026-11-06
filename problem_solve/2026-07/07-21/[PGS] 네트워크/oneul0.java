import java.util.*;
class Solution {
    List<List<Integer>> gr = new ArrayList<>();
    boolean[] visited;
    public int solution(int n, int[][] computers) {
        visited = new boolean[n];
        for(int i = 0; i<n; i++){
            gr.add(new ArrayList<>());
        }
        for(int i = 0; i<computers.length; i++){
            for(int j = 0; j<computers[i].length; j++){
                if(i == j) continue;
                if(computers[i][j] == 1){
                    gr.get(i).add(j);
                }
            }
        }

        int answer = 0;
        for(int i = 0; i<n; i++){
            if(visited[i]) continue;
            answer++;
            bfs(i);
        }
        return answer;
    }
    public void bfs(int sx){
        Queue<Integer> q = new ArrayDeque<>();
        q.offer(sx);
        visited[sx] = true;
        while(!q.isEmpty()){
            int cur = q.poll();
            
            for(int next : gr.get(cur)){
                if(visited[next]) continue;
                q.offer(next);
                visited[next] = true;
            }
        }
    }
}