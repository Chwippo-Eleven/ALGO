class Solution {
    public long solution(int n) {

        // 1칸 뛰는 방법 -> 1
        // 2칸 뛰는 방법 -> (1, 1) / (2) -> 2
        // 3칸 뛰는 방법 -> 1칸 전에서 오는 방법 + 2칸 전에서 오는 방법

        if (n <= 2){
            return n;
        } else {
            long[] dp = new long[n + 1];
            dp[1] = 1;
            dp[2] = 2;

            for (int i = 3; i <= n; i++){
                dp[i] = (dp[i - 1] + dp[i - 2]) % 1234567;
            }
            return dp[n];
        }
    }
}