import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    private static final int INF = Integer.MAX_VALUE;

    private static int n, k;
    private static int[] dr = {-1, 1, 0, 0};
    private static int[] dc = {0, 0, -1, 1};

    private static boolean isIn(int r, int c) {
        return 0 <= r && r < n && 0 <= c && c < n;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        int[][] grid = new int[n][n];

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                grid[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // (r1, c1), (r2, c2)를 0-based로 변환
        st = new StringTokenizer(br.readLine());
        int r1 = Integer.parseInt(st.nextToken()) - 1;
        int c1 = Integer.parseInt(st.nextToken()) - 1;

        st = new StringTokenizer(br.readLine());
        int r2 = Integer.parseInt(st.nextToken()) - 1;
        int c2 = Integer.parseInt(st.nextToken()) - 1;
        
        // BFS-Like
        int[][][] minCost = new int[n][n][k + 1];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                Arrays.fill(minCost[i][j], INF);
            }
        }

        // (행, 열, 없앤 벽 수, 비용)을 원소로 갖는 큐
        Queue<int[]> queue = new ArrayDeque<>();

        // 시작 지점 초기화
        Arrays.fill(minCost[r1][c1], 0);
        queue.offer(new int[] {r1, c1, 0, 0});

        BFS:
        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int curRow = current[0];
            int curCol = current[1];
            int curWallCnt = current[2];
            int curCost = current[3];

            if (curCost > minCost[curRow][curCol][curWallCnt]) continue;

            for (int d = 0; d < 4; d++) {
                int nr = curRow + dr[d];
                int nc = curCol + dc[d];

                if (!isIn(nr, nc)) continue;

                int nextWallCnt = curWallCnt;
                if (grid[nr][nc] == 1) nextWallCnt += 1;

                if (nextWallCnt > k) continue;
                
                int nextCost = curCost + 1;

                if (nextCost < minCost[nr][nc][nextWallCnt]) {
                    for (int i = nextWallCnt; i <= k; i++) {
                        minCost[nr][nc][i] = Math.min(minCost[nr][nc][i], nextCost);
                    }
                    queue.offer(new int[] {nr, nc, nextWallCnt, nextCost});
                }

                if (nr == r2 && nc == c2) break BFS;
            }
        }

        int result = minCost[r2][c2][k];
        bw.write((result == INF ? -1 : result) + "\n");

        bw.flush();
        bw.close();
        br.close();
    }
}
