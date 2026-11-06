import java.io.*;
import java.util.*;

public class Solution {

    static int N;
    static int[][] cheese;
    static boolean[][] visited;
    static int day;

    static int[] dx = {-1, 1, 0, 0};
    static int[] dy = {0, 0, -1, 1};

    static void dfs(int x, int y) {
        visited[x][y] = true;

        for (int d = 0; d < 4; d++) {
            int nx = x + dx[d];
            int ny = y + dy[d];

            if (0 <= nx && nx < N && 0 <= ny && ny < N) {
                if (!visited[nx][ny] && cheese[nx][ny] > day) {
                    dfs(nx, ny);
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int T = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= T; tc++) {
            N = Integer.parseInt(br.readLine());

            cheese = new int[N][N];

            for (int i = 0; i < N; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());

                for (int j = 0; j < N; j++) {
                    cheese[i][j] = Integer.parseInt(st.nextToken());
                }
            }

            int answer = 1;

            for (day = 1; day <= 100; day++) {
                visited = new boolean[N][N];
                int count = 0;

                for (int i = 0; i < N; i++) {
                    for (int j = 0; j < N; j++) {
                        if (cheese[i][j] > day && !visited[i][j]) {
                            dfs(i, j);
                            count++;
                        }
                    }
                }

                answer = Math.max(answer, count);
            }

            System.out.println("#" + tc + " " + answer);
        }
    }
}
