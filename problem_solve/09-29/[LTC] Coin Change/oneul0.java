class Solution {
    int[] dp;
    public int coinChange(int[] coins, int amount) {
        if(amount == 0) return 0;
        if(coins.length == 1 && amount%coins[0] != 0) return -1;

        dp = new int[amount+1];
        Arrays.fill(dp, Integer.MAX_VALUE-1);
        dp[0] = 0;
        for(int c : coins){
            for(int i = c; i<=amount; i++){
                dp[i] = Math.min(dp[i], dp[i-c]+1);
            }
        }
        return dp[amount] == Integer.MAX_VALUE-1 ? -1 : dp[amount];
    }
}