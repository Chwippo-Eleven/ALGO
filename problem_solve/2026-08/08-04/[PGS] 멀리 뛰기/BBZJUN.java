class Solution {
    static long[] arr = new long[2001];
    public long solution(int n) {
        
        arr[0] = 0L;
        arr[1] = 1L;
        arr[2] = 2L;
        for (int i=3; i<=n; i++){
            arr[i] = (arr[i-1] + arr[i-2])%1234567;
        }
        return arr[n];
    }
    
}
