

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution {

    static int n = 0;
    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {-1, 0, 1, 0};
    static boolean[][] vis;

    public static class Pair{
        int first, second;

        Pair(int first, int second){
            this.first = first;
            this.second = second;
        }
    }

    public static void playCmd(String cmd, int n, int[][] board){
        int dir = -1;
        int sy = 0, sx = 0;
        int di = 0, dj = 0;
        if(cmd.equals("up")){
            dir = 0;
            di = 1;
            dj = 1;
            sy = 0;
            sx = 0;
        }else if(cmd.equals("right")){
            dir = 1;
            di = 1;
            dj = -1;
            sy = 0;
            sx = n-1;
        }else if(cmd.equals("down")){
            dir = 2;
            di = -1;
            dj = -1;
            sy = n - 1;
            sx = n - 1;
        }else if(cmd.equals("left")){
            dir = 3;
            di = 1;
            dj = 1;
            sy = 0;
            sx = 0;
        }

        sumFunc(sy, sx, di, dj, dir, board);
        moveFunc(sy, sx, di, dj, (dir + 2) % 4, board);
    }

    public static void sumFunc(int sy, int sx, int di, int dj, int d, int[][] board){
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                int y = sy + i * di;
                int x = sx + j * dj;
                if(board[y][x] == 0) continue;
                if(vis[y][x]) continue;
                Pair p = findNum(y, x, (d + 2) % 4, board);
                if(p.first == -1 && p.second == -1) continue;
                if(board[y][x] == board[p.first][p.second]){
                    board[y][x] += board[p.first][p.second];
                    board[p.first][p.second] = 0;
                    vis[y][x] = true;
                }
            }
        }
    }

    public static void moveFunc(int sy, int sx, int di, int dj, int d, int[][] board){
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                int y = sy + i * di;
                int x = sx + j * dj;
                if(board[y][x] != 0) continue;
                Pair p = findNum(y, x, d, board);
                if(p.first == -1 && p.second == -1) continue;

                board[y][x] = board[p.first][p.second];
                board[p.first][p.second] = 0;
            }
        }
    }

    public static Pair findNum(int y, int x, int d, int[][] board){
        int nx = x + dx[d];
        int ny = y + dy[d];
        int tx = -1;
        int ty = -1;
        while(isIn(ny, nx)){
            if(board[ny][nx] != 0){
                tx = nx;
                ty = ny;
                break;
            }

            nx += dx[d];
            ny += dy[d];
        }

        return new Pair(ty, tx);
    }

    public static boolean isIn(int y, int x){
        return y >= 0 && y < n && x >= 0 && x < n;
    }

    public static void main(String[] args) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int tc = Integer.parseInt(br.readLine());

        for (int t = 1; t <= tc; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            n = Integer.parseInt(st.nextToken());
            String cmd = st.nextToken();
            int[][] board = new int[n][n];
            vis = new boolean[n][n];

            for (int i = 0; i < n; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < n; j++) {
                    board[i][j] = Integer.parseInt(st.nextToken());
                }
            }

            playCmd(cmd, n, board);

            sb.append("#").append(t).append("\n");
            for (int[] ints : board) {
                for (int anInt : ints) {
                    sb.append(anInt).append(" ");
                }
                sb.append("\n");
            }
        }

        System.out.println(sb);

    }

}
