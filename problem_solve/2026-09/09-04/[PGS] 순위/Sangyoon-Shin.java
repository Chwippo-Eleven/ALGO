import java.util.*;

class Solution {
    public int solution(int n, int[][] results) {

        int[][] f = new int[n + 1][n + 1];

        // A가 B를 이김 -> 1
        // A가 B에게 짐 -> -1

        for (int i = 0; i < results.length; i++){
            int win = results[i][0];
            int lose = results[i][1];

            f[win][lose] = 1;
            f[lose][win] = -1;
        }

        // A -> B, B -> C 이면 A -> C인 경우에 대해서도 체크해주기
        for (int k = 1; k <= n; k++){
            for (int i = 1; i <= n; i++){
                for (int j = 1; j <= n; j++){
                    if (f[i][k] == 1 && f[k][j] == 1){
                        f[i][j] = 1;
                        f[j][i] = -1;
                    }
                    if (f[i][k] == -1 && f[k][j] == -1){
                        f[i][j] = -1;
                        f[j][i] = 1;
                    }
                }
            }
        }

        // 상태표 확인하면서 0이 아닌 열이 n-1개 있으면 순위 판단 가능
        int res = 0;
        for (int i = 1; i <= n; i++){
            int cnt = 0;

            for (int j = 1; j <= n; j++){
                if (f[i][j] != 0){
                    cnt++;
                }
            }
            if (cnt == n - 1){
                res++;
            }
        }
        return res;


    }
}