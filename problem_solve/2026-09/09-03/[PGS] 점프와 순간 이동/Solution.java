import java.util.*;

public class Solution {
    public int solution(int n) {
        // 체력 1은 무조건 쓰고 들어감
        int ans = 1;
        
        int dest = n;
        while(dest != 1) {
            // 2로 딱 나누어 떨어지면
            if (dest%2==0) {
                dest /= 2; // -> 순간이동 가능
            } else { // 체력 1 써야 함
                dest--; 
                ans++;
            }
        }

        return ans;
    }
}
