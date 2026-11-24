package boj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.StringTokenizer;

public class boj14503 {

    static int[] dx = {0, -1, 0, 1};
    static int[] dy = {-1, 0, 1, 0};
    static int n;
    static int m;
    static int res = 0;
    static int[][] board;
    static int cnt = 0;
    public static class Robot{
        int y, x, d, clean = 0;

        Robot(int y, int x, int d){
            this.y = y;
            this.x = x;
            this.d = d;
        }
    }

    public static boolean isOut(int y, int x){
        return y >= n || y < 0 || x >= m || x < 0;
    }

    public static void dfs(boj.boj14503.Robot robot){
        int d = robot.d;
        int md = (d + 2) % 4;
        int y = robot.y;
        int x = robot.x;
        if(board[y][x] != 2){
            res++;
        }
        board[y][x] = 2;
        boj.boj14503.Robot temp = null;

        for (int dir = d + 1; dir <= d + 4; dir++) {
            int td = dir % 4;
            int ny = y + dy[td];
            int nx = x + dx[td];

            if(isOut(ny, nx)) continue;
            if(board[ny][nx] != 0) continue;

            temp = new boj.boj14503.Robot(ny, nx, td);
            break;
        }

        if (temp == null) {
            int ny = y + dy[md];
            int nx = x + dx[md];

            if(board[ny][nx] == 2){

                robot.y = ny;
                robot.x = nx;
                dfs(robot);
            }
        }else{
            dfs(temp);
        }


    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuffer sb = new StringBuffer();
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        board = new int[n][m];

        st = new StringTokenizer(br.readLine());
        int y = Integer.parseInt(st.nextToken());
        int x = Integer.parseInt(st.nextToken());
        int d = Integer.parseInt(st.nextToken());

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        ArrayDeque<boj.boj14503.Robot> q = new ArrayDeque<>();
        boj.boj14503.Robot robot = new boj.boj14503.Robot(y, x, (4 - d) % 4);

        dfs(robot);

        System.out.println(res);
    }

}
