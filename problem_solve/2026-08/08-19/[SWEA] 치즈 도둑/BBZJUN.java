import java.util.*;
import java.io.*;

public class Solution {
    public static int N;
    public static int[][] map;
    public static boolean[][] visited;
    public static int[] dx = {-1, 1, 0, 0};
    public static int[] dy = {0, 0, -1, 1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();

        for (int test_case = 1; test_case <= T; test_case++) {
            N = Integer.parseInt(br.readLine());
            // 배열 만들고
            map = new int[N][N];

            // 생성
            for (int i = 0; i < N; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                for (int j = 0; j < N; j++) {
                    map[i][j] = Integer.parseInt(st.nextToken());
                }
            }

            int maxCount = 0;

            // 0~100까지 날짜를 카운트
            for (int d = 0; d <= 100; d++) {
                int count = ccc(d);
                maxCount = Math.max(maxCount, count);
            }
			System.out.printf("#%d %d\n", test_case, maxCount);
        }
    }


    //d일 일 때 카운트 체크
    public static int ccc(int d) {
        visited = new boolean[N][N];
        int count = 0;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (map[i][j] > d && !visited[i][j]) {
                    bfs(i, j, d);
                    count++;
                }
            }
        }
        return count;
    }

    // 이어진 것들 체크
    public static void bfs(int sx, int sy, int d) {
        Deque<int[]> dq = new ArrayDeque<>();
        dq.offer(new int[]{sx, sy});
        visited[sx][sy] = true;

        while (!dq.isEmpty()) {
            int[] cur = dq.poll();
            int x = cur[0], y = cur[1];

            for (int i = 0; i < 4; i++) {
                int nx = x + dx[i];
                int ny = y + dy[i];

                if (nx < 0 || ny < 0 || nx >= N || ny >= N) //범위
                    continue;
                if (visited[nx][ny]) //방문
                    continue;
                if (map[nx][ny] <= d) //날짜
                    continue;

                visited[nx][ny] = true;
                dq.offer(new int[]{nx, ny});
            }
        }
    }
}
