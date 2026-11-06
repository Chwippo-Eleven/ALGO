import java.util.*;
import java.io.*;

public class Solution {
    static int N;
    static int[][] map;
    static boolean[][] visited;
    static int[] dx = {-1, 1, 0, 0};
    static int[] dy = {0, 0, -1, 1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine().trim());
        for (int tc = 1; tc <= T; tc++) {
            N = Integer.parseInt(br.readLine().trim());
            map = new int[N][N];
            for (int i = 0; i < N; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                for (int j = 0; j < N; j++) {
                    map[i][j] = Integer.parseInt(st.nextToken());
                }
            }

            int maxCount = 0;
            for (int day = 1; day <= 100; day++) {
                int count = countBlocks(day);
                maxCount = Math.max(maxCount, count);
            }

            sb.append("#").append(tc).append(" ").append(maxCount).append("\n");
        }

        System.out.print(sb);
    }

    static int countBlocks(int day) {
        visited = new boolean[N][N];
        int count = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                // day일차엔 값이 day 이하인 칸은 이미 먹힌 상태
                if (map[i][j] > day && !visited[i][j]) {
                    bfs(i, j, day);
                    count++;
                }
            }
        }
        return count;
    }

    static void bfs(int sx, int sy, int day) {
        Deque<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[]{sx, sy});
        visited[sx][sy] = true;

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int cx = cur[0], cy = cur[1];
            for (int d = 0; d < 4; d++) {
                int nx = cx + dx[d];
                int ny = cy + dy[d];
                if (nx < 0 || ny < 0 || nx >= N || ny >= N) continue;
                if (visited[nx][ny]) continue;
                if (map[nx][ny] <= day) continue;
                visited[nx][ny] = true;
                queue.offer(new int[]{nx, ny});
            }
        }
    }
}