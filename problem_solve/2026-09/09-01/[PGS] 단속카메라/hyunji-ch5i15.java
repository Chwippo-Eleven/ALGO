import java.util.*;
class Solution {
    public int solution(int[][] routes) {
        int n = routes.length;
        Arrays.sort(routes, (a,b)->a[1]-b[1]);
        boolean[] visited = new boolean[n];
        int answer = 0;
        for (int i=0; i<n; i++) {
            if (visited[i]) continue;
            answer++;
            visited[i] = true;
            int j = i+1;
            
            while (j<n && routes[i][1] >= routes[j][0]) {
                visited[j] = true;
                j++;
            }
        }
        
        return answer;
    }
}