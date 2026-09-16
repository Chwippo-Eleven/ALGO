class Solution {
    public int[] solution(int brown, int yellow) {
        int[] answer = new int [2];
        int sum = brown / 2 + 2;      // w + h
        int mul = brown + yellow;     // w * h

        int d = sum * sum - 4 * mul;

        int w = (sum + (int)Math.sqrt(d)) / 2;
        int h = (sum - (int)Math.sqrt(d)) / 2;
        
        answer[0] = w;
        answer[1] = h;
        
        return answer;
    }
}