class Solution {

    private final int INF = Integer.MAX_VALUE;
    
    public int coinChange(int[] coins, int amount) {

        int[] dp = new int[amount + 1];
        // dp[i]: minimum coin count to reach value i

        Arrays.fill(dp, INF);
        dp[0] = 0;

        for (int value = 0; value <= amount; value++) {
            for (int coin : coins) {
                int prev = value - coin;
                if (prev < 0 || dp[prev] == INF) continue;

                dp[value] = Math.min(dp[value], dp[prev] + 1);
            }
        }

        return (dp[amount] == INF) ? -1 : dp[amount];
    }
}
