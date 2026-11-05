import java.util.*;

class Solution {
    public int solution(int[][] triangle) {
        int n=triangle.length;
        
        int[][]dp=new int[n][n];
        
        dp[0][0]=triangle[0][0];
        
        for(int i=1;i<n;i++){
            for(int j=0;j<triangle[i].length;j++){

                //제일 왼쪽
                if(j==0){
                    dp[i][j]=dp[i-1][j]+triangle[i][j];
                    continue;
                }
                //제일 오른쪽
                if(j==i){
                    dp[i][j]=dp[i-1][j-1]+triangle[i][j];
                    continue;
                }
                
                dp[i][j]=Math.max(dp[i-1][j-1], dp[i-1][j])+triangle[i][j];
                
            }
        }

        
        int max=Integer.MIN_VALUE;
        for(int value:dp[n-1]){
            max=Math.max(max,value);
        }
        return max;
    }
}
