import java.util.*;

class Solution {
    static char[][] map;
    static int n, m, sR, sC, eR, eC;
    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, -1, 1};
    public int solution(String[] board) {

        n = board.length;
        m = board[0].length();

        map = new char[n][m];

        for (int i = 0; i < n; i++){
            for (int j = 0; j < m; j++){
                map[i][j] = board[i].charAt(j);
                if (map[i][j] == 'R'){
                    sR = i; sC = j;
                }
                if (map[i][j] == 'G'){
                    eR = i; eC =j;
                }
            }
        }

        return bfs(sR, sC);
    }
    public int bfs(int r, int c){

        ArrayDeque<int[]> q = new ArrayDeque<>();
        boolean[][] v = new boolean[n][m];

        v[r][c] = true;
        q.offerLast(new int[] {r, c, 0});

        while (!q.isEmpty()){

            int[] cur = q.pollFirst();
            for (int i = 0; i < 4; i++){
                int nr = cur[0];
                int nc = cur[1];

                if (nr == eR && nc == eC){
                    return cur[2];
                }

                while (isIn(nr + dr[i], nc + dc[i]) && map[nr + dr[i]][nc + dc[i]] != 'D'){
                    nr += dr[i];
                    nc += dc[i];
                }
                if (v[nr][nc]) continue;

                v[nr][nc] = true;
                q.offerLast(new int[] {nr, nc, cur[2] + 1});
            }
        }
        return -1;
    }
    public boolean isIn(int r, int c){
        return r >= 0 && r < n && c >= 0 && c < m;
    }
}