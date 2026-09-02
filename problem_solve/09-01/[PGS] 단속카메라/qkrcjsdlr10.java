import java.util.*;


class Solution {
    public int solution(int[][] routes) {
        int answer = 0;

        Arrays.sort(routes, (a, b) -> Integer.compare(a[1], b[1]));
        int cur = Integer.MIN_VALUE;

        for(int[] next : routes){
            if(cur >= next[0]) continue;
            cur = next[1];
            answer++;
        }

        return answer;
    }
}