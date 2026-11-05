class Solution {
    
    static final int INF = Integer.MAX_VALUE;
    
    static int[] singleAndBull = {
         1,  2,  3,  4,  5,  6,  7, 
         8,  9, 10, 11, 12, 13, 14,
        15, 16, 17, 18, 19, 20, 50,
    };
    
    static int[] doubleAndTriple = {
        21, 22, 24, 26, 27, 28, 30, 
        32, 33, 34, 36, 38, 39, 40, 
        42, 45, 48, 51, 54, 57, 60,
    };
    
    public int[] solution(int target) {
        
        // dp[i][0]: 다트 개수 / dp[i][1]: 싱글 & 불 개수
        int[][] dp = new int[target + 1][2];
        
        // 전체 채우기
        for (int i = 1; i <= target; i++) {
            dp[i][0] = INF;
            dp[i][1] = 0;
            
            for (int x : singleAndBull) {
                if (i - x < 0) break;
                
                // 다트 개수가 더 적은 방법이 있는 경우
                if (dp[i - x][0] + 1 < dp[i][0]) {
                    dp[i][0] = dp[i - x][0] + 1;
                    dp[i][1] = dp[i - x][1] + 1;
                
                // 다트 개수가 같고 싱글 또는 불을 더 많이 맞히는 경우
                } else if (dp[i - x][0] + 1 == dp[i][0] && dp[i - x][1] + 1 > dp[i][1]) {
                    dp[i][0] = dp[i - x][0] + 1;
                    dp[i][1] = dp[i - x][1] + 1;
                }
            }
            
            for (int x : doubleAndTriple) {
                if (i - x < 0) break;
                
                // 다트 개수가 더 적은 방법이 있는 경우
                if (dp[i - x][0] + 1 < dp[i][0]) {
                    dp[i][0] = dp[i - x][0] + 1;
                    dp[i][1] = dp[i - x][1];
                
                // 다트 개수가 같고 싱글 또는 불을 더 많이 맞히는 경우
                } else if (dp[i - x][0] + 1 == dp[i][0] && dp[i - x][1] > dp[i][1]) {
                    dp[i][0] = dp[i - x][0] + 1;
                    dp[i][1] = dp[i - x][1];
                }
            }
        }
        
        return dp[target];
    }
}
