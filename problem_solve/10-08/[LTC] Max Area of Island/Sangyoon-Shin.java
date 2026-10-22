import java.util.*;

class Solution {
    static int r, c, res;
    static boolean[][] visited;
    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, -1, 1};
    static int cnt;
    public int maxAreaOfIsland(int[][] grid) {
        res = 0;
        r = grid.length;
        c = grid[0].length;

        visited = new boolean[r][c];
        for (int i = 0; i < r; i++){
            for (int j = 0; j < c; j++){
                if (!visited[i][j] && grid[i][j] == 1) {
                    cnt = 0;
                    bfs(i, j, grid);
                }
            }
        }
        return res;
    }
    public static void bfs(int i, int j, int[][] map){


        ArrayDeque<int[]> q = new ArrayDeque<>();

        visited[i][j] = true;
        q.offerLast(new int[] {i, j});
        cnt++;
        res = Math.max(res, cnt);

        while (!q.isEmpty()){
            int[] cur = q.pollFirst();
            res = Math.max(res, cnt);

            for (int x = 0; x < 4; x++){
                int nr = cur[0] + dr[x];
                int nc = cur[1] + dc[x];


                if (nr >= r || nr < 0 || nc >= c || nc < 0) continue;
                if (visited[nr][nc]) continue;
                if (map[nr][nc] == 0) continue;

                visited[nr][nc] = true;
                q.offerLast(new int[] {nr, nc});
                cnt++;
            }

        }
    }
}