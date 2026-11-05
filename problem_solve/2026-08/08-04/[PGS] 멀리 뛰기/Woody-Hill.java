class Solution {
    public long solution(int n) {
        
        long[] fibonacci = new long[n + 1];
        fibonacci[0] = 1;
        fibonacci[1] = 1;
        
        for (int i = 2; i <= n; i++) {
            fibonacci[i] = (fibonacci[i - 1] + fibonacci[i - 2]) % 1234567;
        }
        
        return fibonacci[n];
    }
}
