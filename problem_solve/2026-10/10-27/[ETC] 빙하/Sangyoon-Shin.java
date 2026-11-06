import java.io.*;
import java.util.*;

public class Main {
    static int n, m;
    static int[][] map;
    static boolean[][] v;
    static int ice, time;
    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, -1, 1};
    static ArrayDeque<int[]> q;
    static ArrayList<int[]> cand;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        map = new int[n][m];

        for (int i = 0; i < n; i++){
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++){
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        ice = 0; time = 0;

        q = new ArrayDeque<>();
        while (countIce() > 0){
            v = new boolean[n][m];
            bfs(0, 0);

            // 얼음을 물로 바꾸고 방문 표시 지워야함.
            changeIceToWater();
            time++;
        }

        System.out.println(time + " " + ice);
    }
    public static int countIce(){
        int cnt = 0;

        for (int i = 0; i < n; i++){
            for (int j = 0; j < m; j++){
                if (map[i][j] == 1) cnt++;
            }
        }

        if (cnt > 0){
            ice = cnt;
        }
        return cnt;
    }

    public static void bfs(int r, int c){

        cand = new ArrayList<>();
        q.offerLast(new int[] {r, c});
        v[r][c] = true;

        while (!q.isEmpty()){
            int[] cur = q.pollFirst();
            for (int i = 0; i < 4; i++){
                int nr = cur[0] + dr[i];
                int nc = cur[1] + dc[i];

                if (!isIn(nr, nc)) continue;
                if (v[nr][nc]) continue;

                if (map[nr][nc] == 0){ // 1. 바깥쪽 물이면 큐에 넣기
                    v[nr][nc] = true;
                    q.offerLast(new int[] {nr, nc});
                }
                if (map[nr][nc] == 1){ // 2. 빙하인 경우 다음턴에 물로 바뀌는 애.
                    cand.add(new int[] {nr, nc});
                    v[nr][nc] = true;
                }
            }
        }
    }
    public static boolean isIn(int r, int c){
        return r >= 0 && r < n && c >= 0 && c < m;
    }
    public static void changeIceToWater(){

        for (int[] cur : cand){
            map[cur[0]][cur[1]] = 0;
        }
    }
}