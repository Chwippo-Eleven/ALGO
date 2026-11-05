import java.util.*;
class Solution {
    public int solution(int[][] routes) {
        Arrays.sort(routes, (a, b) -> a[0]-b[0]);

        int answer = 1;
        int end = routes[0][1];
        for (int i=1; i<routes.length; i++) {
            if (end<routes[i][0]) {
                answer++;
                end = routes[i][1];
            } else {
                end = Math.min(end, routes[i][1]);
            }
        }
        return answer;
    }
}