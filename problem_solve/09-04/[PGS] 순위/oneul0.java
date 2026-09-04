import java.util.*;
class Solution {
    int n;
    public int solution(int n, int[][] results) {
        this.n = n;
        List<List<Integer>> wingr = new ArrayList<>();
        List<List<Integer>> losegr = new ArrayList<>();
        for(int i = 0; i<=n; i++){
            wingr.add(new ArrayList<>());
            losegr.add(new ArrayList<>());
        }
        for(int[] r : results){
            wingr.get(r[0]).add(r[1]);
            losegr.get(r[1]).add(r[0]);
        }
        
        int answer = 0;
        for(int i = 1; i<=n; i++){
            answer += bfs(i, wingr) + bfs(i, losegr) == n-1 ? 1 : 0;
        }
        
        return answer;
    }
    public int bfs(int sx, List<List<Integer>> gr){
        Queue<Integer> q = new ArrayDeque<>();
        boolean[] visited= new boolean[n+1];
        q.offer(sx);
        visited[sx] = true;
        int count=0;
        while(!q.isEmpty()){
            int cur= q.poll();
            
            for(int next : gr.get(cur)){
                if(visited[next]) continue;
                q.offer(next);
                visited[next] =true;
                count++;
            }
        }
        return count;
    }
}