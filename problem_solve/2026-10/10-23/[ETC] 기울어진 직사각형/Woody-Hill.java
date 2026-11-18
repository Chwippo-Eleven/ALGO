import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {

    private static int n;
    private static int[][] grid;

    private static int[] dr = {-1, -1, 1, 1};
    private static int[] dc = {1, -1, -1, 1};

    private static int sr, sc;
    private static int maxSum;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        n = Integer.parseInt(br.readLine());
        grid = new int[n][n];

        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                grid[i][j] = Integer.parseInt(st.nextToken());
            }   
        }

        maxSum = 0;

        for (int i = 2; i < n; i++) {
            for (int j = 1; j < n - 1; j++) {
                sr = i;
                sc = j;
                backtrack(i, j, 0, 0);
            }
        }

        bw.write(maxSum + "\n");

        bw.flush();
        bw.close();
        br.close();
    }

    private static void backtrack(int r, int c, int dir, int sum) {
        if (r == sr && c == sc && sum > 0) {
            maxSum = Math.max(maxSum, sum);
            return;
        }

        int nr = r + dr[dir];
        int nc = c + dc[dir];

        if (!isIn(nr, nc)) return;

        backtrack(nr, nc, dir, sum + grid[r][c]);
        
        if (dir < 3){
            backtrack(nr, nc, dir + 1, sum + grid[r][c]);
        }
    }

    private static boolean isIn(int r, int c) {
        return 0 <= r && r < n && 0 <= c && c < n;
    }
}
