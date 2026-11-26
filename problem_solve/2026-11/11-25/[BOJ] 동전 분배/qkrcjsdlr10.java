package boj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.Arrays;

public class boj1943 {
    static int n;
    static int mx;
    static int[][] coins;
    static int[][] dp;
    static int target;

    public static int dfs(int depth, int sum){
        if(depth == n){
            if(sum == target){
                return 1;
            }
            return 0;
        }

        if(sum > target) return 0;

        if(dp[depth][sum] != -1) return dp[depth][sum];

        int temp = 0;

        for(int i = 0; i < coins[depth][1]; i++){
            temp += coins[depth][0];

            if(dfs(depth + 1, sum + temp) == 1){
                return dp[depth][sum] = 1;
            }
        }

        if(dfs(depth + 1, sum) == 1){
            return dp[depth][sum] = 1;
        }

        return dp[depth][sum] = 0;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        for(int t = 0; t < 3; t++){
            n = Integer.parseInt(br.readLine());

            coins = new int[n][2];
            mx = 0;

            for(int i = 0; i < n; i++){
                StringTokenizer st = new StringTokenizer(br.readLine());
                coins[i][0] = Integer.parseInt(st.nextToken());
                coins[i][1] = Integer.parseInt(st.nextToken());
                mx += coins[i][0] * coins[i][1];
            }

            if(mx % 2 != 0){
                sb.append(0).append("\n");
                continue;
            }

            target = mx / 2;
            dp = new int[n][target + 1];

            for(int[] row : dp){
                Arrays.fill(row, -1);
            }

            int res = dfs(0, 0);
            sb.append(res).append("\n");
        }

        System.out.print(sb);
    }
}