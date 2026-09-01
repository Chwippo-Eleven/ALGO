import java.util.*;


class Solution {
    public int solution(int[][] routes) {
        int answer = 0;
        
        Arrays.sort(routes, (a, b) -> Integer.compare(a[1], b[1]));
        int cur = routes[0][1];
        answer++;
        
        for(int[] next : routes){
            if(cur >= next[0]) continue;
            cur = next[1];
            answer++;
        }
        
        return answer;
    }
}