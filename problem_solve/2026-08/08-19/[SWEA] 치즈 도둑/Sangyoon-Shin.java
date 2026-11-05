import java.io.*;
import java.util.*;

public class Solution{
    static int t, n, max, res, cnt;
    static int[][] map;
    static boolean[][] visited;
    static int[] dr = new int[] {-1, 1, 0, 0};
    static int[] dc = new int[] {0, 0, -1, 1};
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        t = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= t; tc++) {
            n = Integer.parseInt(br.readLine());
            map = new int[n][n];
            max = 0; // 최댓값까지만 탐색하면 됨.
            res = 0;

            for (int r = 0; r < n; r++){
                st = new StringTokenizer(br.readLine());
                for (int c = 0; c < n; c++){
                    map[r][c] = Integer.parseInt(st.nextToken());
                    if (map[r][c] > max){
                        max = map[r][c];
                    }
                }
            }

            for (int i = 0; i <= max; i++){
                visited = new boolean[n][n];
                cnt = 0;
                for (int r = 0; r < n; r++){
                    for (int c = 0; c < n; c++){
                        if (!visited[r][c] && map[r][c] >= i){
                            // 현재 일 수 보다 큰 애들만 방문 가능한 칸이고
                            // bfs 돌린 횟수 자체가 영역의 개수
                            bfs(r, c, i);
                            cnt++;
                        }
                    }
                }
                res = Math.max(res, cnt);
            }

            System.out.println("#" + tc + " " + res);
        }
    }
    public static void bfs(int r, int c, int day){
        ArrayDeque<int[]> q = new ArrayDeque<>();
        visited[r][c] = true;
        q.addLast(new int[] {r, c});

        while (!q.isEmpty()){
            int[] cur = q.removeFirst();
            for (int i = 0; i < 4; i++){
                int ddr = cur[0] + dr[i];
                int ddc = cur[1] + dc[i];
                if (isIn(ddr, ddc) && !visited[ddr][ddc] && map[ddr][ddc] >= day){
                    visited[ddr][ddc] = true;
                    q.addLast(new int[] {ddr, ddc});
                }
            }
        }

    }
    public static boolean isIn(int r, int c){
        return r >= 0 && r < n && c >= 0 && c < n;
    }
}
