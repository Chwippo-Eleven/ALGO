import java.io.*;
import java.util.*;

public class Solution {
    static int t, n;
    static final int INF = Integer.MAX_VALUE;
    static int[][] map;
    static int[][] dist;
    static boolean[][] v;
    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, -1, 1};
    public static void main(String[] args) throws IOException{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        t = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= t; tc++) {
            n = Integer.parseInt(br.readLine());

            map = new int[n][n];
            dist = new int[n][n];
            v = new boolean[n][n];

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    dist[i][j] = INF;
                }
            }

            for (int i = 0; i < n; i++) {
                String s = br.readLine();
                for (int j = 0; j < n; j++) {
                    map[i][j] = s.charAt(j) - '0';
                }
            }

            dijkstra();

            System.out.println("#" + tc + " " + (dist[n - 1][n - 1]));
        }
    }
    public static void dijkstra() {

        ArrayDeque<int[]> q = new ArrayDeque<>();
        v[0][0] = true;
        q.offerLast(new int[] {0, 0});
        dist[0][0] = map[0][0];

        while (!q.isEmpty()) {

            int[] cur = q.pollFirst();
            for (int i = 0; i < 4; i++) {
                int nr = cur[0] + dr[i];
                int nc = cur[1] + dc[i];

                if (nr < 0 || nr >= n || nc < 0 || nc >= n) continue;
                // 최단 비용 따지기
                if (dist[nr][nc] > map[nr][nc] + dist[cur[0]][cur[1]]) {
                    dist[nr][nc] = map[nr][nc] + dist[cur[0]][cur[1]];
                    v[nr][nc] = true;
                    q.addLast(new int[] {nr, nc});
                }
            }
        }

    }
}