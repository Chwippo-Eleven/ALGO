class Solution {
    public int solution(int[][] triangle) {
        int answer = 0;
        int n = triangle.length;
        int mx = 0;
        int [][] dp = new int[n][n];
        
        for(int i = 0; i < triangle.length; i++){
            for(int j = 0; j < triangle[i].length; j++){
                dp[i][j] = triangle[i][j];
            }
        }
        
        for(int i = 1; i < triangle.length; i++){
            for(int j = 0; j < triangle[i].length; j++){
                if(j == 0){
                    dp[i][j] = dp[i-1][j] + dp[i][j];
                    continue;
                }
                dp[i][j] = Math.max(dp[i - 1][j] + dp[i][j], dp[i - 1][j - 1] + dp[i][j]);
            }
        }
        
        
        for(int j = 0; j < n; j++){
            answer = Math.max(answer, dp[n-1][j]);
        }
        
        return answer;
    }
}