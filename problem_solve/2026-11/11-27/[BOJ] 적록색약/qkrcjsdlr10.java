package boj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.StringTokenizer;

public class boj10026 {

    static int n;
    static char[][] board;
    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {-1, 0, 1, 0};

    public static class Pair{
        int first, second;

        Pair(int first, int second){
            this.first = first;
            this.second = second;
        }
    }

    public static int bfs(int type){
        boolean[][] vis = new boolean[n][n];
        char[][] temp = new char[n][n];

        if(type == 0){
            for (int i = 0; i < n; i++) {
                temp[i] = board[i].clone();
            }
        }else{
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if(board[i][j] == 'R' || board[i][j] == 'G'){
                        temp[i][j] = 'G';
                    }else{
                        temp[i][j] = 'B';
                    }
                }
            }
        }

        ArrayDeque<boj.boj10026.Pair> q = new ArrayDeque<>();
        int cnt = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if(vis[i][j]) continue;

                cnt++;
                char st = temp[i][j];
                q.addLast(new boj.boj10026.Pair(i, j));
                vis[i][j] = true;

                while(!q.isEmpty()){
                    boj.boj10026.Pair cur = q.pollFirst();
                    int x = cur.second;
                    int y = cur.first;

                    for (int d = 0; d < 4; d++) {
                        int nx = x + dx[d];
                        int ny = y + dy[d];

                        if(nx < 0 || nx >= n || ny < 0 || ny >= n) continue;
                        if(st != temp[ny][nx]) continue;
                        if(vis[ny][nx]) continue;

                        q.addLast(new boj.boj10026.Pair(ny, nx));
                        vis[ny][nx] = true;
                    }
                }

            }
        }

        return cnt;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());

        String[] grid = new String[n];
        board = new char[n][n];

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            grid[i] = st.nextToken();
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] = grid[i].charAt(j);
            }
        }

        System.out.println(bfs(0) + " " + bfs(1));

    }

}
