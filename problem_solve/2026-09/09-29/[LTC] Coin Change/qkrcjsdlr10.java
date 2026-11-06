class Solution {

    static int answer = Integer.MAX_VALUE;
    static int[] dp;

    public static void dfs(int[] coins, int amount, int sum, int depth){
        if(sum >= amount){
            if(sum == amount){
                answer = Math.min(answer, depth);
                // System.out.println("asdfasdfasdfasdf");
            }

            return;
        }

        if (dp[sum] != Integer.MAX_VALUE && dp[sum] <= depth) {
            return;
        }

        dp[sum] = depth;

        for (int i = 0; i < coins.length; i++) {
            if (coins[i] <= amount - sum) {
                dfs(coins, amount, sum + coins[i], depth + 1);
            }
        }

    }

    public int coinChange(int[] coins, int amount) {
        answer = Integer.MAX_VALUE;
        dp = new int[amount + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dfs(coins, amount, 0, 0);

        if(amount == 0){
            return 0;
        }

        return ((answer == Integer.MAX_VALUE)? -1 : answer);
    }
}