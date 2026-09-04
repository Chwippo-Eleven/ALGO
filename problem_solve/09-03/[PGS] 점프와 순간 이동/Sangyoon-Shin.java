import java.util.*;

public class Solution {
    public int solution(int n) {

        int use = 0;

        // 2로 나눌 수 있을땐 무조건 나눠주는게 배터리 소모가 0이므로 유리함.
        // 홀수인 경우 짝수로 만들어주고 그 때부터 순간이동시키면 배터리 소모 최소.
        while (n > 0){
            if (n % 2 == 0){
                n /= 2;
            } else {
                n -= 1;
                use += 1;
            }
        }
        return use;
    }
}