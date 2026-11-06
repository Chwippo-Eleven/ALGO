import java.util.Scanner;
import java.util.*;

public class Main {

    static public class Pair{
        int first, second;

        Pair(int first, int second){
            this.first = first;
            this.second = second;
        }
    }

    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {-1, 0, 1, 0};

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        int[][] grid = new int[n][m];

        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                grid[i][j] = sc.nextInt();
        // Please write your code here.

        int res = 0;
        int cnt = 0;

        while(true){
            ArrayDeque<Pair> q = new ArrayDeque<>();
            boolean[][] vis = new boolean[n][m];
            boolean check = false;

            for(int i = 0; i < n; i++){
                if(check) break;
                for(int j = 0; j < m; j++){
                    if(grid[i][j] == 1){
                        check = true;
                        break;
                    }
                }
            }

            if(!check) break;
            q.add(new Pair(0, 0));
            vis[0][0] = true;

            while(!q.isEmpty()){
                Pair cur = q.pollFirst();
                int x = cur.second;
                int y = cur.first;

                for(int d = 0; d < 4; d++){
                    int nx = x + dx[d];
                    int ny = y + dy[d];

                    if(ny >= n || ny < 0 || nx >= m || nx < 0) continue;
                    if(grid[ny][nx] == 1) continue;
                    if(vis[ny][nx]) continue;

                    // grid[ny][nx] = -1;
                    vis[ny][nx] = true;
                    q.add(new Pair(ny, nx));
                }
            }
            cnt = 0;
            for(int i = 0; i < n; i++){
                for(int j = 0; j < m; j++){
                    if(vis[i][j] == true){
                        for(int d = 0; d < 4; d++){
                            int nx = j + dx[d];
                            int ny = i + dy[d];

                            if(ny >= n || ny < 0 || nx >= m || nx < 0) continue;
                            if(grid[ny][nx] == 0) continue;
                            if(vis[ny][nx]) continue;
                            cnt++;
                            // grid[ny][nx] = -1;
                            grid[ny][nx] = 0;
                        }
                    }
                }
            }

            res++;
            // System.out.println(res);
        }
        System.out.print(res + " " + cnt);
        // return res;
    }
}