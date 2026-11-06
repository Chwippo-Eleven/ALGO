class Solution {
    public int[] solution(int brown, int yellow) {
        
        // 가로와 세로의 합과 곱
        int sum  = (brown + 4) / 2;
        int prod = (2 * sum) + yellow - 4;
        
        // 근의 공식으로 가로와 세로 길이 구하기
        int width  = (sum + (int) Math.sqrt(sum * sum - 4 * prod)) / 2; 
        int height = (sum - (int) Math.sqrt(sum * sum - 4 * prod)) / 2;
        
        int[] answer = {width, height};
        return answer;
    }
}
