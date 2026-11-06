import java.util.*;
class Solution {
    public long solution(int n, int[] times) {
        Arrays.sort(times); // 빠른 심사관부터 검사
        long left = 1; 
        long right = (long) times[times.length - 1] * n;
        
        while (left < right) {
            long mid = (left + right) / 2; // 걸린 시간
            long count = 0;
            for (int t : times) {
                count += mid / t;
                if (count >= n) break; // 더 크면 안 봐도 X
            }
            
            
            if (count >= n) {
                right = mid; 
            } else {
                left = mid + 1;
            }
        }
        
        return left; 
    }
}