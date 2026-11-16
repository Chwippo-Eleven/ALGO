import java.util.*;
public class Main {

    static int[][] dp;

    public static int dfs(int mask, int[][] cost, int cur, int n){
        if (mask == (1 << n) - 1){
            if (cost[cur][0] != 0) {
                return cost[cur][0];
            }
            return Integer.MAX_VALUE;
        }

        if (dp[mask][cur] != -1) {
            return dp[mask][cur];
        }

        int res = Integer.MAX_VALUE;

        for (int i = 0; i < n; i++) {
            if ((mask & (1 << i)) == 0 && cost[cur][i] != 0) {
                int next = dfs(mask | (1 << i), cost, i, n);

                if (next != Integer.MAX_VALUE) {
                    res = Math.min(res, cost[cur][i] + next);
                }
            }
        }

        return dp[mask][cur] = res;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[][] cost = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                cost[i][j] = sc.nextInt();
            }
        }
        // Please write your code here.
        // dp = new int[n];
        dp = new int[1 << n][n];

        for (int[] arr : dp) {
            Arrays.fill(arr, -1);
        }

        int answer = dfs(1, cost, 0, n);

        System.out.println(answer);
    }
}