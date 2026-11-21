import java.util.*;
import java.io.*;

public class Main {
    static int n, k;
    static int[][] map;
    static ArrayDeque<int[]> q = new ArrayDeque<>();
    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, -1, 1};
    static boolean[][] visited;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        map = new int[n][n];
        visited = new boolean[n][n];

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                if (map[i][j] == 0) {
                    map[i][j] = -1;
                }
                if (map[i][j] == 2) {
                    visited[i][j] = true;
                    map[i][j] = 0;
                    q.offerLast(new int[] {i, j});
                }
            }
        }
        bfs();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (!visited[i][j] && (map[i][j] != -1)){
                    map[i][j] = -2;
                }
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }
    public static void bfs() {
        while (!q.isEmpty()) {
            int[] cur = q.pollFirst();
            for (int i = 0; i < 4; i++) {
                int nr = cur[0] + dr[i];
                int nc = cur[1] + dc[i];

                if (nr < 0 || nr >= n || nc < 0 || nc >= n) continue;
                if (map[nr][nc] <= 0) continue;
                if (visited[nr][nc]) continue;

                visited[nr][nc] = true;
                map[nr][nc] = map[cur[0]][cur[1]] + 1;
                q.offerLast(new int[] {nr, nc});
            }
        }
    }
}
