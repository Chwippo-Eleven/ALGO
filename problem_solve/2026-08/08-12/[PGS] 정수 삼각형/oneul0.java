class Solution {
    public int solution(int[][] triangle) {
        int[][] dp = new int[triangle.length][triangle[triangle.length-1].length];
        dp[0][0] = triangle[0][0];
        for(int i = 1; i<triangle.length; i++){
            for(int j = 0; j<triangle[i].length; j++){
                //왼쪽 끝은 바로 위(i-1, j)만 가능
                if(j == 0){
                    dp[i][j] = triangle[i][j]+ dp[i-1][j];
                }
                //오른쪽 끝은(i-1, j-1)만 가능
                else if(j == triangle[i].length-1){
                    dp[i][j] = triangle[i][j]+ dp[i-1][j-1];
                }
                //나머지는 위(i-1,j), 왼쪽(i-1,j-1) 가능
                else{
                    dp[i][j] = triangle[i][j]+ Math.max(dp[i-1][j], dp[i-1][j-1]);
                }
            }
        }
        
        int answer = 0;
        for(int val : dp[dp.length-1]){
            answer = Math.max(answer, val);
        }
        return answer;
    }
}
