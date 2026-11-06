import java.util.*;

class Solution {
    
    public long solution(int n) {
        long answer = 0;
        
        List<Long> arr = new ArrayList<>();
        arr.add(0L);
        arr.add(1L);
        arr.add(2L);
        
        for(int i = 3; i <= n; i++){
            long num = (arr.get(i - 1) + arr.get(i - 2)) % 1234567;
            arr.add(num);
        }
        
        answer = arr.get(n);
        
        return answer;
    }
}
