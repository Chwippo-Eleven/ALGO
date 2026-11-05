package SWEA.Test;

import java.io.*;
import java.util.*;

public class SWEA2112 {
    static int t, d, w, k;
    static int[][] map;
    static int res;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        t = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= t; tc++){
            st = new StringTokenizer(br.readLine());
            d = Integer.parseInt(st.nextToken());
            w = Integer.parseInt(st.nextToken());
            k = Integer.parseInt(st.nextToken());

            map = new int[d][w];

            for (int i = 0; i < d; i++){
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < w; j++){
                    map[i][j] = Integer.parseInt(st.nextToken());
                }
            }

            res = k;

            if (k == 1){
                res = 0;
            } else {
                dfs(0, 0);
            }

            System.out.println("#" + tc + " " + res);
        }
    }
    public static void dfs(int row, int count){

        if (count >= res){ // 이미 최소횟수를 넘겼으면 더 볼 필요가 없는거지. -> 가지치기
            return;
        }
        if (check()){
            res = count;
            return;
        }
        if (row == d){
            return;
        }

        int[] origin = map[row].clone();

        dfs(row + 1, count); // 1. 지금 행에 약품 투입 안함

        Arrays.fill(map[row], 0); // 2. A로 약품 투입
        dfs(row + 1, count + 1);

        Arrays.fill(map[row], 1); // 3. B로 약품 투입
        dfs(row + 1, count + 1);

        map[row] = origin; // 다음 선택에 영향 주면 안되니까 백트래킹
    }
    public static boolean check(){ // 모든 열에 대해서 각 행을 탐색했을때 k개만큼 연속된 색상있으면 true, 하나의 열에서라도 조건 만족 안하면 false

        for (int i = 0; i < w; i++){
            int cnt = 1;
            boolean isPossible = false;

            for (int j = 1; j < d; j++){
                if (map[j][i] == map[j - 1][i]){
                    cnt++;
                } else {
                    cnt = 1;
                }

                if (cnt >= k){
                    isPossible = true;
                    break;
                }
            }

            if (k == 1){
                isPossible = true;
            }
            if (!isPossible){
                return false;
            }
        }
        return true;
    }
}
