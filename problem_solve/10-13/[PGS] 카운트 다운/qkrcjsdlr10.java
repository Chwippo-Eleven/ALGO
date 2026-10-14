class Solution {

    int[][] dp;
    int[] score;

    public void dfs(int target) {
        if (target == 0) return;
        if (dp[target][0] != 0) return;

        for (int i = 1; i <= 21; i++) {
            if (i == 21) {
                update(target, 50, 1);
            } else {
                for (int j = 1; j <= 3; j++) {
                    update(target, score[i] * j, j == 1 ? 1 : 0);
                }
            }
        }
    }
    public void update(int target, int s, int cnt) {
        if (target < s) return;

        dfs(target - s);

        int darts = dp[target - s][0] + 1;
        int single = dp[target - s][1] + cnt;

        if (dp[target][0] == 0 || darts < dp[target][0] || (darts == dp[target][0] && single > dp[target][1])) {

            dp[target][0] = darts;
            dp[target][1] = single;
        }
    }

    public int[] solution(int target) {
        int[] answer = {};

        score = new int[22];
        dp = new int[target + 1][2];

        for (int i = 1; i <= 20; i++) {
            score[i] = i;

            if(i * 2 <= target){
                dp[i * 2][0] = 1;
                dp[i * 2][1] = 0;
            }

            if(i * 3 <= target){
                dp[i * 3][0] = 1;
                dp[i * 3][1] = 0;
            }

            if (i <= target) {
                dp[i][0] = 1;
                dp[i][1] = 1;
            }
        }

        score[21] = 50;
        if (target >= 50) {
            dp[50][0] = 1;
            dp[50][1] = 1;
        }

        dfs(target);


        return new int[]{dp[target][0], dp[target][1]};
    }
}