import java.io.*;

class Solution {
    static int len, res, target;
    public int coinChange(int[] coins, int amount) {

        len = coins.length;

        // i를 만들 수 있는 동전 최소 개수 저장할거임.
        int[] dp = new int[amount + 1];

        Arrays.fill(dp, amount + 1);

        dp[0] = 0;
        for (int i = 1; i <= amount; i++){
            for (int coin : coins){
                if (i >= coin){
                    dp[i] = Math.min(dp[i], dp[i - coin] + 1);
                }
            }
        }
        if (dp[amount] == amount + 1){
            return -1;
        } else {
            return dp[amount];
        }
    }
}