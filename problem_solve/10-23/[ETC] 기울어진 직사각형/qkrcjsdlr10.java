
import java.util.Scanner;

public class Main {

    static int[] dx = {1, -1, -1, 1};
    static int[] dy = {1, 1, -1, -1};
    static int n = 0;
    static int res = 0;

    public static void moveDown(int y, int x, int d, int rCnt, int lCnt, int[][] grid, int sum) {
        if(!isIn(y, x)) return;

        int nx = x + dx[d];
        int ny = y + dy[d];

        if(d == 0) {
            if(isIn(ny, nx))
                moveDown(ny, nx, d, rCnt + 1, lCnt, grid, sum + grid[ny][nx]);

            if(rCnt > 0)
                moveDown(y, x, d + 1, rCnt, lCnt, grid, sum);

        } else if(d == 1) {
            if(isIn(ny, nx))
                moveDown(ny, nx, d, rCnt, lCnt + 1, grid, sum + grid[ny][nx]);

            if(lCnt > 0)
                moveUp(y, x, d + 1, rCnt, lCnt, grid, sum);
        }
    }

    public static void moveUp(int y, int x, int d, int rCnt, int lCnt, int[][] grid, int sum) {
        if(!isIn(y, x)) return;

        if(d == 3 && lCnt == 0) {
            res = Math.max(res, sum - grid[y][x]);
            return;
        }

        int nx = x + dx[d];
        int ny = y + dy[d];

        if(d == 2) {
            if(rCnt == 0) {
                moveUp(y, x, d + 1, rCnt, lCnt, grid, sum);
            } else if(isIn(ny, nx)) {
                moveUp(ny, nx, d, rCnt - 1, lCnt, grid, sum + grid[ny][nx]);
            }

        } else if(d == 3) {
            if(lCnt > 0 && isIn(ny, nx))
                moveUp(ny, nx, d, rCnt, lCnt - 1, grid, sum + grid[ny][nx]);
        }
    }

    public static boolean isIn(int y, int x) {
        return y < n && y >= 0 && x < n && x >= 0;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        n = sc.nextInt();
        int[][] grid = new int[n][n];

        for(int i = 0; i < n; i++)
            for(int j = 0; j < n; j++)
                grid[i][j] = sc.nextInt();

        for(int i = 0; i < n; i++)
            for(int j = 0; j < n; j++)
                moveDown(i, j, 0, 0, 0, grid, grid[i][j]);

        System.out.println(res);
    }
}
