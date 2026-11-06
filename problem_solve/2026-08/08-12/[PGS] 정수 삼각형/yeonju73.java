// 밑에서부터 올라오기
// arr[i-1][j] 또는 arr[i-1][j-1] 로만 이동 가능
// [i-1]의 j랑 j+1이랑 max 해서 [i-1][j]에 더해서 저장
class Solution {
    public int solution(int[][] triangle) {
        int n = triangle.length;
        
        for(int i = n-1; i >= 1; i--) {
            for(int j = 0; j < i; j++) {
                int maxValue = Math.max(triangle[i][j], triangle[i][j+1]);
                triangle[i-1][j] += maxValue;
            }
        }
        return triangle[0][0];
    }
}
