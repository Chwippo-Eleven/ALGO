import java.util.*;
import java.io.*;

class Solution
{
    public static void main(String args[]) throws Exception
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int T = Integer.parseInt(br.readLine());

        for(int test_case = 1; test_case <= T; test_case++)
        {
            int N = Integer.parseInt(br.readLine());
            int[][] arr = new int[N][N];
            StringTokenizer st;
            for(int i = 0; i<N; i++){
                st = new StringTokenizer(br.readLine());
                for(int j = 0; j<N; j++){
                    arr[i][j] = Integer.parseInt(st.nextToken());
                }
            }


            //days gone
            int maxGroup =  0;
            for(int day = 0; day<=100; day++){
                maxGroup = Math.max(maxGroup, groupCount(arr, day, N));
            }
            bw.write("#"+test_case+" "+maxGroup+"\n");
        }
        bw.flush();
        br.close();
        bw.close();
    }

    static int[] dx = {-1,1,0,0}, dy = {0,0,-1,1};
    public static int groupCount(int[][] arr, int day, int N){
        int count = 0;
        boolean[][] visited = new boolean[N][N];
        for(int i = 0; i<N; i++){
            for(int j = 0; j<N; j++){
                if(!visited[i][j] && day<= arr[i][j]){
                    count++;
                    bfs(arr, day, N, visited, i, j);
                }
            }
        }
        return count;
    }
    public static void bfs(int[][] arr, int day, int N, boolean[][] visited, int sx, int sy){
        Queue<int[]> q = new ArrayDeque<>();
        q.offer(new int[]{sx, sy});
        visited[sx][sy] = true;

        while(!q.isEmpty()){
            int[] cur = q.poll();
            for(int i = 0; i<4; i++){
                int nx = cur[0] + dx[i];
                int ny = cur[1] + dy[i];
                if(nx<0 || ny<0 || nx>=N || ny>=N || visited[nx][ny]) continue;
                if(arr[nx][ny] < day) continue;
                q.offer(new int[]{nx, ny});
                visited[nx][ny] = true;
            }
        }
    }
}