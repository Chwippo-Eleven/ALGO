import java.util.*;
class Solution {
    public int[] solution(String[][] places) {
        int n = places.length;
        int[] answer = new int[n];
        for(int i = 0; i<n; i++){
            answer[i] = solve(places[i]);
        }
        return answer;
    }
    public int solve(String[] place){
        for(int i = 0; i<5; i++){
            for(int j = 0; j<5; j++){
                if(place[i].charAt(j) == 'P'){
                    if(!bfs(place, i, j)) return 0;
                }
            }
        }
        
        return 1;
    }
    int[] dx = {-1,1,0,0}, dy = {0,0,-1,1};
    public boolean bfs(String[] place, int sx, int sy){
        Queue<int[]> q = new ArrayDeque<>();
        q.offer(new int[]{sx, sy, 0});
        boolean[][] visited = new boolean[5][5];
        visited[sx][sy] = true;
        while(!q.isEmpty()){
            int[] cur = q.poll();
            for(int i = 0; i<4; i++){
                int nx = cur[0] + dx[i];
                int ny = cur[1] + dy[i];
                
                if(nx<0 || ny<0 || nx>=5 || ny>=5) continue;
                if(place[nx].charAt(ny) == 'X' || visited[nx][ny]) continue;
                if(place[nx].charAt(ny) == 'P') return false;
                if(cur[2]+1 >= 2) continue;
                q.offer(new int[]{nx, ny, cur[2]+1});
                visited[nx][ny] = true;
            }
        }
        return true;
    }
}