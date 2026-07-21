import java.util.ArrayDeque;
import java.util.Queue;

class Solution {
    public int solution(int n, int[][] computers) {
        int answer = 0;
        
        Queue<Integer> queue = new ArrayDeque<>();
        boolean[] visited = new boolean[n];
        
        for (int start = 0; start < n; start++) {
            if (visited[start]) continue;
            
            queue.add(start);
            
            while (!queue.isEmpty()) {
                int currNode = queue.poll();

                if (visited[currNode]) {
                    continue;
                }
                visited[currNode] = true;

                for (int next = 0; next < n; next++) {
                    if (visited[next] || computers[currNode][next] == 0) {
                        continue;
                    }
                    queue.add(next);
                }
            }
            answer += 1;
        }
        return answer;
    }
}
