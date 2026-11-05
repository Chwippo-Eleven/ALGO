class Solution {
    public long solution(int n) {
        if (n == 1) return 1;
        
        // dp[n-1] = n번째 칸에 도달하는 방법의 수
        long[] dp = new long[n];
        
        dp[0] = 1;
        dp[1] = 2;
        
        // n에 도달하는 방법은 n-1에서 1칸 뛰기, n-2에서 2칸 뛰기 두 가지 경우
        for(int i = 2; i < n; i++){
            dp[i] = (dp[i-1] + dp[i-2]) % 1234567;
        }
        return dp[n-1];
    }
}
