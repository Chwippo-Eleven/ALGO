class Solution {
    public int solution(int[][] triangle) {
        
        int n = triangle.length;
        
        for(int i = 1; i < n; i++){
            for(int j = 0; j <= i; j++){
                
                if(j == 0){ // 왼쪽 숫자면 -> 오른쪽 위 숫자만 가능
                    triangle[i][j] += triangle[i-1][j];
                }
                else if(j == i){ // 오른쪽 숫자면 -> 왼쪽 위 숫자만 가능
                    triangle[i][j] += triangle[i-1][j-1];
                } 
                else{// 가운데에 낀 숫자면 -> 왼 위, 오 위 중에 큰거로 가져오면 댐
                    triangle[i][j] += Math.max(triangle[i-1][j-1], triangle[i-1][j]);
                }
            }
        }
        
        int answer = 0;
        
        for(int v : triangle[n-1]){ //바닥 중에서 큰 거 찾기
            answer = Math.max(answer, v);
        }
        
        return answer;
    }
}
