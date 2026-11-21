import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    private static final int[] dr = {-1, 1, 0, 0};
    private static final int[] dc = {0, 0, -1, 1};

    private static int n, m;

    private static boolean isIn(int r, int c) {
        return 0 <= r && r < n && 0 <= c && c < m;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        int[][] grid = new int[n][m];
        int iceCount = 0;
        
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                int value = Integer.parseInt(st.nextToken());

                grid[i][j] = value;
                iceCount += value;
            }
        }
        
        int time = 0;   // 현재 시간

        // 큐의 원소: (행, 열)
        Queue<int[]> queue = new ArrayDeque<>();

        while (true) {
            time++;

            // 1. 바깥쪽 물 처리

            // 1-1. 방문 배열 & 녹이기 배열 초기화
            boolean[][] visited = new boolean[n][m];
            boolean[][] melting = new boolean[n][m];

            // 1-2. 가장자리의 물을 큐에 전부 삽입
            for (int c = 0; c < m; c++) {
                queue.offer(new int[] {0, c});
                queue.offer(new int[] {n - 1, c});

                visited[0][c] = true;
                visited[n - 1][c] = true;
            }

            for (int r = 1; r < n - 1; r++) {
                queue.offer(new int[] {r, 0});
                queue.offer(new int[] {r, m - 1});

                visited[r][0] = true;
                visited[r][m - 1] = true;
            }

            // 1-3. 녹일 수 있는 얼음들 찾기
            while (!queue.isEmpty()) {
                int[] coord = queue.poll();

                int r = coord[0];
                int c = coord[1];

                for (int d = 0; d < 4; d++) {
                    int nr = r + dr[d];
                    int nc = c + dc[d];

                    if (!isIn(nr, nc) || visited[nr][nc]) continue;

                    // 새로운 물 발견
                    if (grid[nr][nc] == 0) {
                        queue.offer(new int[] {nr, nc});
                        visited[nr][nc] = true;

                    // 새로운 얼음 발견
                    } else if (grid[nr][nc] == 1) {
                        melting[nr][nc] = true;
                        visited[nr][nc] = true;
                    }
                }
            }

            // 2. 얼음 녹이기
            int meltingCount = 0;
            
            for (int i = 1; i < n - 1; i++) {
                for (int j = 1; j < m - 1; j++) {
                    if (melting[i][j]) {
                        grid[i][j] = 0;
                        meltingCount++;
                    }
                }
            }

            // 3. 다 녹였으면 종료, 남았으면 얼음 수 갱신
            if (iceCount == meltingCount) {
                break;
            }

            iceCount -= meltingCount;
        }

        bw.write(time + " " + iceCount + "\n");

        bw.flush();
        bw.close();
        br.close();
    }
}
