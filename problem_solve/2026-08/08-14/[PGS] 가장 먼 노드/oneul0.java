import java.util.*;
class Solution {
    public int solution(int n, int[][] edge) {
        List<List<Integer>> gr = new ArrayList<>();
        for(int i = 0; i<=n; i++){
            gr.add(new ArrayList<>());
        }
        for(int[] e : edge){
            gr.get(e[0]).add(e[1]);
            gr.get(e[1]).add(e[0]);
        }
        
        return bfs(n, gr);
    }
    
    public int bfs(int n, List<List<Integer>> gr){
        Queue<int[]> q = new ArrayDeque<>();
        int[] dists = new int[n+1];
        Arrays.fill(dists, -1);
        q.offer(new int[]{1, 0});
        dists[1] = 0;
        
        while(!q.isEmpty()){
            int[] cur = q.poll();
            
            for(int next : gr.get(cur[0])){
                if(dists[next] > -1) continue;
                q.offer(new int[]{next, cur[1]+1});
                dists[next] = cur[1]+1;
            }
        }
        
        int maxVal = Arrays.stream(dists).max().orElse(0);
        return (int) Arrays.stream(dists)
            .filter(a -> a == maxVal)
            .count();
    }
}