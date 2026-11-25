import java.io.*;
import java.util.*;

public class Main {
    static int n, m, r, c, d;
    static int[][] map;
    static int[] dr = new int[] {-1, 0, 1, 0};
    static int[] dc = new int[] {0, 1, 0, -1};
    static int cnt;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        map = new int[n][m];

        st = new StringTokenizer(br.readLine());
        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());
        d = Integer.parseInt(st.nextToken());

        for (int i = 0; i < n; i++){
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++){
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        cnt = 0;
        boolean isBlocked = false;
        while (true){
            if (isBlocked){
                System.out.println(cnt);
                return;
            }
            if (map[r][c] == 0){ // 현재 칸이 청소 안된 칸이면
                cnt++;
                map[r][c] = 2; // 청소 완료된 방은 2로 표시
            } else {
                boolean isAlreadyClean = true; // 4방향이 이미 다 청소되었는지
                for (int i = 0; i < 4; i++){
                    int ddr = r + dr[i];
                    int ddc = c + dc[i];
                    if (ddr >= 0 && ddr < n && ddc >= 0 && ddc < m){
                        if (map[ddr][ddc] == 0){ // 청소 안된 방이 하나라도 있는 경우
                            isAlreadyClean = false;
                            break;
                        }
                    }
                }
                if (isAlreadyClean) { // 4방향이 다 청소된 방이면 후진하는 로직
                    goBack();
                    if (r >= 0 & r < n && c >= 0 && c < m){
                        if (map[r][c] == 1){
                            isBlocked = true;
                        }
                    } else {
                        isBlocked = true;
                    }
                } else { // 청소 안된 방이 있으면 반시계로 90도 돌리고, 그 방향의 방을 확인
                    changeDir();
                    int ddr = r + dr[d];
                    int ddc = c + dc[d];
                    if (ddr >= 0 && ddr < n && ddc >= 0 && ddc < m){
                        if (map[ddr][ddc] == 0){ // 돌리고 전진한 방이 청소 안한 방이면 청소하면 됨.
                            map[ddr][ddc] = 2;
                            cnt++;
                            r = ddr;
                            c = ddc; // 위치 업데이트 해주기
                        }
                    }
                }
            }
        }
    }
    public static void changeDir(){
        if (d == 0){
            d = 3;
        } else {
            d -= 1;
        }
    }
    public static void goBack(){
        if (d == 0){
            r += 1;
        } else if (d == 1){
            c -= 1;
        } else if (d == 2){
            r -= 1;
        } else {
            c += 1;
        }
    }
}
