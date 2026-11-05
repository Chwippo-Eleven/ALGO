import java.util.*;

class Solution {
    public long solution(int n) {
        long answer = 0;
        // 엣지케이스 처리
        if (n == 1)
            return 1;
        // dp 정의: n을 만들 수 있는 경우의 수
        long[] dp = new long[n + 1];
        dp[1] = 1;
        dp[2] = 2;
        for (int i = 3; i <= n; i++) {
            dp[i] = (dp[i - 1] + dp[i - 2]) % 1234567;
        }

        return dp[n];
    }

}
