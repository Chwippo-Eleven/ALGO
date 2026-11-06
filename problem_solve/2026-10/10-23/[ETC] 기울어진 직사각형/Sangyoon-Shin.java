import java.io.*;
import java.util.*;

public class Main {
    static int n, res, sR, sC;
    static int[][] map;
    static int[] dr = {-1, -1, 1, 1};
    static int[] dc = {1, -1, -1, 1};
    public static void main(String[] args) throws IOException{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        map = new int[n][n];

        for (int i = 0; i < n; i++){
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++){
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        res = 0;

        for (int i = 2; i < n; i++){
            for (int j = 1; j < n - 1; j++){
                sR = i;
                sC = j;

                dfs(i, j, 0, map[sR][sC], 0);
            }
        }

        System.out.println(res);
    }
    public static void dfs(int r, int c, int dir, int sum, int moved){

        // 1. 지금 방향으로 1칸이라도 이동했고 바꿀 방향 남아있으면 바꿔보기
        if (moved > 0 && dir < 3){
            dfs(r, c, dir + 1, sum, 0);
        }

        // 2. 현재 방향 유지하는 경우
        int nr = r + dr[dir];
        int nc = c + dc[dir];

        if (!isIn(nr, nc)) return;

        // 시작 지점으로 돌아왔고 4번째 방향인 상태면 한바퀴 돌고 온 것
        if (nr == sR && nc == sC){
            if (dir == 3){
                res = Math.max(res, sum);
            }
            return;
        }

        dfs(nr, nc, dir, sum + map[nr][nc], moved + 1);
    }
    public static boolean isIn(int r, int c){
        return r >= 0 && r < n && c >= 0 && c < n;
    }
}