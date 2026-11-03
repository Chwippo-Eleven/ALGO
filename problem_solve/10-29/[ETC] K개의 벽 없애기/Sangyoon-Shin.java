import java.util.*;

public class Main {
    static int N, K, startR, startC, endR, endC;
    static int[][] map;
    static boolean[][][] v;

    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, -1, 1};

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        N = sc.nextInt();
        K = sc.nextInt();

        map = new int[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                map[i][j] = sc.nextInt();
            }
        }

        startR = sc.nextInt() - 1;
        startC = sc.nextInt() - 1;
        endR = sc.nextInt() - 1;
        endC = sc.nextInt() - 1;

        v = new boolean[N][N][K + 1];

        int res = bfs();
        System.out.println(res);
    }

    public static int bfs() {
        ArrayDeque<int[]> q = new ArrayDeque<>();

        q.offerLast(new int[]{startR, startC, 0, 0});
        v[startR][startC][0] = true;

        while (!q.isEmpty()) {
            int[] cur = q.pollFirst();

            int r = cur[0];
            int c = cur[1];
            int k = cur[2];
            int dist = cur[3];

            // 도착
            if (r == endR && c == endC) {
                return dist;
            }

            for (int i = 0; i < 4; i++) {
                int nr = r + dr[i];
                int nc = c + dc[i];

                if (!isIn(nr, nc)) continue;

                // 1. 벽 X
                if (map[nr][nc] == 0) {
                    if (v[nr][nc][k]) continue;

                    v[nr][nc][k] = true;
                    q.offerLast(new int[]{nr, nc, k, dist + 1});

                }

                // 2. 벽 O
                else if (map[nr][nc] == 1 && k < K) {
                    if (v[nr][nc][k + 1]) continue;

                    v[nr][nc][k + 1] = true;
                    q.offerLast(new int[]{nr, nc, k + 1, dist + 1});
                }
            }
        }

        return -1;
    }

    public static boolean isIn(int r, int c) {
        return r >= 0 && r < N && c >= 0 && c < N;
    }
}
