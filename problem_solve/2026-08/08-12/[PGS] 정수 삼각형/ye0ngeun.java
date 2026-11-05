class Solution {
    public int solution(int[][] triangle) {
        for (int i = 1; i < triangle.length; i++) {
            for (int j = 0; j < triangle[i].length; j++) {
                // 맨 왼쪽
                if (j == 0) {
                    triangle[i][j] += triangle[i-1][j];
                } 
                // 맨 오른쪽
                else if (j == i) {
                    triangle[i][j] += triangle[i-1][j-1];
                }
                // 가운데
                else {
                    triangle[i][j] += Math.max(triangle[i-1][j-1], triangle[i-1][j]);
                }
            }
        }
        
        int[] lastRow = triangle[triangle.length - 1];
        int answer = 0;

        for (int value : lastRow) {
            answer = Math.max(answer, value);
        }
        
        return answer;
    }
}
