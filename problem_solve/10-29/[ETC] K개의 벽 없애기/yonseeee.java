import java.io.*;
import java.util.*;

public class Main {
    static int[] dr = {1, -1, 0, 0};
    static int[] dc = {0, 0, 1, -1};

    static int n, k;
    static int[][] grid;
    static List<int[]> walls;
    static int[] selected;
    static int r1, c1, r2, c2;
    static int min;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        grid = new int[n][n];
        walls = new ArrayList<>();
        selected = new int[k];

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                grid[i][j] = Integer.parseInt(st.nextToken());
                if(grid[i][j] == 1) walls.add(new int[]{i, j});
            }
        }

        st = new StringTokenizer(br.readLine());
        r1 =  Integer.parseInt(st.nextToken())-1;
        c1 =  Integer.parseInt(st.nextToken())-1;

        st = new StringTokenizer(br.readLine());
        r2 = Integer.parseInt(st.nextToken())-1;
        c2 = Integer.parseInt(st.nextToken())-1;


        min = Integer.MAX_VALUE;

        dfs(0, 0);

        if(min == Integer.MAX_VALUE) System.out.println(-1);
        else System.out.println(min);

    }

    public static void dfs(int start, int idx){
        if(idx == k){
            int[][] tmp = new int[n][n];
            for(int i = 0; i < n; i++){
                tmp[i] = grid[i].clone();
            }

            crashWall(tmp);

            int result = bfs(tmp);
            if(result != -1)
            min = Math.min(min, result);

            return;
        }
        for(int i = start; i < walls.size(); i++){
            selected[idx] = i;

            dfs(i+1, idx+1);
        }
    }

    public static void crashWall(int[][] tmp){
        for(int i = 0; i < k; i++){
            int[] coord = walls.get(selected[i]);

            tmp[coord[0]][coord[1]] = 0;


        }
    }

    public static int bfs(int[][] tmp){
        Queue<int[]> queue = new ArrayDeque();

        boolean[][] visited = new boolean[n][n];

        queue.offer(new int[]{r1, c1, 0});
        visited[r1][c1] = true;

        while(!queue.isEmpty()){
            int[] cur = queue.poll();

            if(cur[0] == r2 && cur[1] == c2)return cur[2];
            
            for(int i = 0; i < 4; i++){
                int nr = cur[0] + dr[i];
                int nc = cur[1] + dc[i];

                if(inRange(nr, nc) && tmp[nr][nc] == 0 && !visited[nr][nc]){
                    visited[nr][nc] = true;
                    queue.offer(new int[]{nr, nc, cur[2]+1});
                }
            }
        }

        return -1;
    }

    public static boolean inRange(int r, int c){
        return r >= 0 && r < n && c >=0 && c < n;
    }

        
}
