import java.util.*;

class Solution {
    public int solution(int k, int[] tangerine) {
        int answer = 0;
        
        int size = tangerine.length;
        int mx = 0;
        
        for(int x : tangerine){
            mx = Math.max(mx, x);
        }
        
        Integer[] count = new Integer[mx + 1];
        Arrays.fill(count, 0);
        for(int x : tangerine){
            count[x]++;
        }
        
        Arrays.sort(count, Collections.reverseOrder());
        
        int cnt = 0;
        int res = 0;
        
        for(int x : count){
            System.out.println(x);
            if(x == 0) break;
            if(cnt >= k) break;
            cnt += x;
            res++;
        }
        System.out.println(res);
        answer = res;
        return answer;
    }
}