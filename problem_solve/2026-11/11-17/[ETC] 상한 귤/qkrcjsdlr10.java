import java.util.*;

public class Main {

    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {-1, 0, 1, 0};

    public static class Pair{
        int first, second;

        Pair(int first, int second){
            this.first = first;
            this.second = second;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int k = sc.nextInt();
        int[][] vis = new int[n + 2][n + 2];
        for(int i = 0; i < n; i++){
            Arrays.fill(vis[i], Integer.MAX_VALUE);
        }
        ArrayDeque<Pair> q = new ArrayDeque<>();

        int[][] grid = new int[n][n];
        for (int i = 0; i < n; i++){
            for (int j = 0; j < n; j++){
                grid[i][j] = sc.nextInt();
                if(grid[i][j] == 2){
                    q.addLast(new Pair(i, j));
                    vis[i][j] = 0;
                }else if(grid[i][j] == 0){
                    vis[i][j] = -1;
                }
            }
        }


        // Please write your code here.

        while(!q.isEmpty()){
            Pair cur = q.pollFirst();
            int y = cur.first;
            int x = cur.second;
            // System.out.println(y + " " + x);
            for(int d = 0; d < 4; d++){
                int ny = y + dy[d];
                int nx = x + dx[d];

                if(nx < 0 || nx >= n || ny < 0 || ny >= n) continue;
                if(vis[ny][nx] == -1) continue;
                if(vis[y][x] + 1 >= vis[ny][nx]) continue;
                // System.out.println(ny + " " + nx);


                vis[ny][nx] = vis[y][x] + 1;
                q.addLast(new Pair(ny, nx));

            }

        }

        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                System.out.print(((vis[i][j] != Integer.MAX_VALUE)? vis[i][j] : -2) + " ");
            }
            System.out.println();
        }

    }
}