class Solution {
    public int solution(int[][] triangle) {
        int n = triangle.length;
        int answer = Integer.MIN_VALUE;
        // 현재 상태: 위좌 위우 택 1
        // 왼모: 우좌
        // 우모: 왼좌
        // dp정의(현 인덱스 상태에서의 최댓값) : dp[i][j] = Math.max(왼좌, 우좌);
        int[][] dp = new int[n][n]; 
        if (n==1) return triangle[0][0];
        // 초기화
        dp[0][0] = triangle[0][0];
        dp[1][0] = dp[0][0] + triangle[1][0];
        dp[1][1] = dp[0][0] + triangle[1][1];
        
        for (int i=2; i<n; i++) {
            for (int j=0; j<triangle[i].length; j++) {
                if (j==0) { // left side
                    dp[i][j] = dp[i-1][j];
                } else if (j==i) { // right side
                    dp[i][j] = dp[i-1][j-1];
                } else { // basic
                    dp[i][j] = Math.max(dp[i-1][j-1], dp[i-1][j]);
                }
                // last 공통 처리
                dp[i][j] += triangle[i][j];
            }
        }
        for (int i=0; i<n; i++) {
            answer = Math.max(answer, dp[n-1][i]);
        }
            
        return answer;
    }
}