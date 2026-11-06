import java.util.*;

class Solution
{
    public int solution(int [][]board)
    {
        int r = board.length;
        int c = board[0].length;
        int res = 0;

        int[][] dp = new int[r][c];

        for (int i = 0; i < r; i++){
            for (int j = 0; j < c; j++){
                if (board[i][j] == 0) continue;

                dp[i][j] = 1;
                if (i > 0 && j > 0){
                    dp[i][j] = Math.min(
                            dp[i - 1][j - 1],
                            Math.min(dp[i - 1][j], dp[i][j - 1])) + 1;
                }
                res = Math.max(res, dp[i][j]);
            }
        }
        return res * res;
    }
}