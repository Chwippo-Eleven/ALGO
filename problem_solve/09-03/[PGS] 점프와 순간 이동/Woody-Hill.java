public class Solution {
    public int solution(int n) {       
        int jump = 0;
        while (n > 0) {
            jump += n % 2;
            n /= 2;
        }
        return jump;
    }
}
