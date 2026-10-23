import java.io.*;
import java.util.*;
public class Main {

    static int[] dx = {-1,-1,1,1}, dy = {1,-1,-1,1};

    static StringTokenizer st;
    static BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {
        int n = Integer.parseInt(br.readLine());
        int[][] grid = new int[n][n];
        for (int i = 0; i < n; i++){
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++){
                grid[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int answer = 0;
        for(int i = n-1; i>=0; i--){
            for(int j =n-1; j>=0; j--){
                answer = Math.max(answer, solve(i, j, grid, n));
            }
        }
            
        System.out.print(answer);
    }
    public static int solve(int x, int y, int[][] grid, int n) {
        int best = 0;

        for (int a = 1; y + a < n; a++) {
            for (int b = 1; b <= y && a + b <= x; b++) {

                int[] len = {a, b, a, b};

                int cx = x;
                int cy = y;
                int sum = 0;

                for (int d = 0; d < 4; d++) {
                    for (int k = 0; k < len[d]; k++) {
                        sum += grid[cx][cy];

                        cx += dx[d];
                        cy += dy[d];
                    }
                }

                best = Math.max(best, sum);
            }
        }

        return best;
    }
}