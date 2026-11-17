import java.io.*;
import java.util.*;
public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken()), k = Integer.parseInt(st.nextToken());
        int[][] grid = new int[n][n], rottenTime = new int[n][n];
        for(int i = 0; i<n; i++) Arrays.fill(rottenTime[i], -2);
        Queue<int[]> q = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                grid[i][j] = Integer.parseInt(st.nextToken());
                if(grid[i][j] == 2) {
                    q.offer(new int[]{i, j, 0});
                    rottenTime[i][j] = 0;
                }
                if(grid[i][j] == 0) rottenTime[i][j] = -1;
            }
        }
        int[] dx = {-1, 1, 0, 0}, dy = {0, 0, -1, 1};
        while(!q.isEmpty()){
            int[] cur = q.poll();
            for(int i = 0; i<4; i++){
                int nx = cur[0] + dx[i], ny = cur[1] + dy[i];
                if(nx<0 || ny<0 || nx>=n || ny>=n || grid[nx][ny] == 0) continue;
                if(grid[nx][ny] == 1 && rottenTime[nx][ny] == -2){
                    rottenTime[nx][ny] = cur[2]+1;
                    q.offer(new int[]{nx, ny, cur[2]+1});
                }
            }
        }

        for(int i = 0; i<n; i++) {
            for(int j = 0; j<n; j++) bw.write(rottenTime[i][j]+" ");
            bw.newLine();
        }
        bw.flush(); br.close(); bw.close();
    }
}
