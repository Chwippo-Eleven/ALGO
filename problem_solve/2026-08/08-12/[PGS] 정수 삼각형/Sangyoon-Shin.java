import java.util.*;

class Solution {
    public int solution(int[][] triangle) {
        // 1
        // 2 3
        // 4 5 6
        // 7 8 9 10
        // 1 + 2 , 1 + 3
        // 1 + 2 + 4, 1 + 2 + 5, 1 + 3 + 5, 1 + 3 + 6
        // (i, j)에서 j != 0 && j != 맨 끝 이면 (i - 1, j - 1) or (i - 1, j)에서 올 수 있음
        // j == 0 -> (i - 1, j) / j == 맨 끝 -> (i - 1, j - 1) 에서 올 수 있음
        if (triangle.length == 1){
            return triangle[0][0];
        }

        int[][] dp = new int[triangle.length][triangle[triangle.length - 1].length];
        dp[0][0] = triangle[0][0];
        for (int i = 1; i < triangle.length; i++){
            for (int j = 0; j < triangle[i].length; j++){
                if (j == 0){
                    dp[i][j] = dp[i - 1][j] + triangle[i][j];
                } else if (j == triangle[i].length - 1){
                    dp[i][j] = dp[i - 1][j - 1] + triangle[i][j];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j - 1] + triangle[i][j], dp[i - 1][j] + triangle[i][j]);
                }
            }
        }

        return Arrays.stream(dp[dp.length - 1]).max().getAsInt();
    }
}