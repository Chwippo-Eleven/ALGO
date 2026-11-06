class Solution {
    public int solution(int[][] triangle) {
        int n = triangle.length;
        int[][] pyramid = new int[n][n]; // DP 배열
        
        // 최상층만 처리해 두고 아래로 내려가면서 더하기
        pyramid[0][0] = triangle[0][0];
        
        int maxNum = pyramid[0][0];      // n = 1 대비
        
        for (int lvl = 1; lvl < n; lvl++) {
            for (int idx = 0; idx <= lvl; idx++) {
                int left  = (idx > 0)   ? pyramid[lvl - 1][idx - 1] : 0;
                int right = (idx < lvl) ? pyramid[lvl - 1][idx]     : 0;
                
                pyramid[lvl][idx] = Math.max(left, right) + triangle[lvl][idx];
                maxNum = Math.max(maxNum, pyramid[lvl][idx]);
            }
        }
        
        return maxNum;
    }
}
