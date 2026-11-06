class Solution {
    public long solution(int n) {
        if(n==1) return 1;
        if(n==2) return 2;
        final int MOD = 1234567;
        long[] dp = new long[n+1];
        
        //dist = i일때
        dp[1] = 1;
        dp[2] = 2;
        for(int i = 3; i<=n; i++){
            dp[i] = (dp[i-1] + dp[i-2])%MOD;
        }
        
        return dp[n];
    }
}