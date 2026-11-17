import java.io.*;
import java.util.*;

public class Main {
    private static final int EMPTY = -1;    // 비어 있는 칸을 나타내는 상수
    private static final int GYUL  = -2;    // 싱싱한 귤을 나타내는 상수

    private static int[] dy = {-1, 1, 0, 0};
    private static int[] dx = {0, 0, -1, 1};

    static int n, k;

    private static boolean isIn(int y, int x) {
        return 0 <= y && y < n && 0 <= x && x < n;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        int[][] grid = new int[n][n];       // 상한 시간 또는 특수한 값 기록
        int[][] badGyulLoc = new int[k][2]; // 상한 귤의 (y, x)좌표 저장
        int index = 0;                      // badGyulLoc에 값 저장할 때 사용

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                int value = Integer.parseInt(st.nextToken());

                if (value == 0) {           // 빈칸
                    grid[i][j] = EMPTY;
                } else if (value == 1) {    // 싱싱한 귤
                    grid[i][j] = GYUL;
                } else if (value == 2) {    // 상한 귤
                
                    grid[i][j] = 0; // 이미 상했으니 상한 시간 = 0

                    badGyulLoc[index][0] = i;
                    badGyulLoc[index][1] = j;
                    index++;
                }
            }
        }

        // BFS Logic
        Queue<int[]> queue = new ArrayDeque<>();    // (y, x) 좌표 삽입

        int currTime = 0;   // 현재 시간 초기화

        for (int i = 0; i < k; i++) {
            queue.offer(new int[] {badGyulLoc[i][0], badGyulLoc[i][1]});
        }
        
        while (!queue.isEmpty()) {
            currTime++;
            int size = queue.size();

            while (size-- > 0) {
                int[] current = queue.poll();
                int currY = current[0];
                int currX = current[1];

                for (int d = 0; d < 4; d++) {
                    int nextY = currY + dy[d];
                    int nextX = currX + dx[d];

                    if (!isIn(nextY, nextX)) continue;

                    // 만약 싱싱한 귤이 들어있다면 상한 시간을 기록한다
                    if (grid[nextY][nextX] == GYUL) {
                        grid[nextY][nextX] = currTime;
                        queue.offer(new int[] {nextY, nextX});
                    }
                }
            }
        }

        // 출력
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                bw.write(grid[i][j] + " ");
            }
            bw.newLine();
        }

        bw.flush();
        bw.close();
        br.close();
    }
}
