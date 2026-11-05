import java.util.Scanner;
import java.util.*;

public class Main {

    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {-1, 0, 1, 0};

    public static class Node{
        int y, x, k;

        Node(int y, int x, int k){
            this.y = y;
            this.x = x;
            this.k = k;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int k = sc.nextInt();
        int[][] grid = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = sc.nextInt();
            }
        }
        int r1 = sc.nextInt() - 1;
        int c1 = sc.nextInt() - 1;
        int r2 = sc.nextInt() - 1;
        int c2 = sc.nextInt() - 1;

        // Please write your code here.

        int[][][] vis = new int[n][n][k + 1];
        ArrayDeque<Node> q = new ArrayDeque<>();
        q.add(new Node(r1, c1, 0));
        for (int[][] arr : vis) {
            for (int[] row : arr) {
                Arrays.fill(row, -1);
            }
        }

        vis[r1][c1][0] = 0;

        int res = Integer.MAX_VALUE;

        while(!q.isEmpty()){
            Node cur = q.pollFirst();
            int y = cur.y;
            int x = cur.x;
            int broken = cur.k;

            if(y == r2 && x == c2){
                break;
            }

            for(int d = 0; d < 4; d++){
                int nx = x + dx[d];
                int ny = y + dy[d];

                if(nx < 0 || nx >= n || ny < 0 || ny >= n) continue;

                if(grid[ny][nx] == 1){
                    if(broken >= k) continue;
                    if(vis[ny][nx][broken + 1] != -1) continue;
                    vis[ny][nx][broken + 1] = vis[y][x][cur.k] + 1;
                    q.add(new Node(ny, nx, broken + 1));

                }else{
                    if(vis[ny][nx][broken] != -1) continue;
                    vis[ny][nx][cur.k] = vis[y][x][cur.k] + 1;
                    q.add(new Node(ny, nx, cur.k));
                }

            }
        }

        for(int i = 0; i < k + 1; i++){
            if(vis[r2][c2][i] == -1) continue;
            res = Math.min(res, vis[r2][c2][i]);
        }

        System.out.println((res == Integer.MAX_VALUE)? -1 : res);
    }
}