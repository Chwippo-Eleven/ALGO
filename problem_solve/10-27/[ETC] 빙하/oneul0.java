import java.io.*;
import java.util.*;
public class Main {
    static int n;
    static int m;
    static int[] dx = {-1,1,0,0}, dy = {0,0,-1,1};
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw =  new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        int[][] grid = new int[n][m];
        for (int i = 0; i < n; i++){
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++){
                grid[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        
        int time = 0;
        int melt = 0;
        while(true){
            int count = bfs(grid);
            if(count == 0) break;
            melt = count;
            time++;
        }
        bw.write(time+" "+melt);
        bw.flush();
        br.close();
        bw.close();
    }
    public static int bfs(int[][] grid){
        Queue<int[]> q = new ArrayDeque<>();
        boolean[][] visited = new boolean[n][m];
        q.offer(new int[]{0,0});
        visited[0][0] = true;
        int count = 0;
        while(!q.isEmpty()){
            int[] cur = q.poll();

            for(int i = 0; i<4; i++){
                int nx = cur[0] + dx[i];
                int ny = cur[1] + dy[i];
                if(nx<0 || ny<0 || nx>=n || ny>=m || visited[nx][ny]) continue;
                
                visited[nx][ny] = true;
                if(grid[nx][ny] == 1) {
                    grid[nx][ny] = 0;
                    count++;
                    continue;
                }
                q.offer(new int[]{nx, ny});
            }
        }
        return count;
    }
}