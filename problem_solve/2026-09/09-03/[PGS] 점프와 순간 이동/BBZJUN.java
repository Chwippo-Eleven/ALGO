import java.util.*;

public class Solution {
    public int solution(int n) {
        int ans = 0;

        // 홀수는 직전에서 +1
        // 짝수는 그냥 전전에서 2배라 +0
        
        while(n>0){
            if (n%2==1){
                ans = ans + 1;
            }
            n=n/2;
        }

        return ans;
    }
}
