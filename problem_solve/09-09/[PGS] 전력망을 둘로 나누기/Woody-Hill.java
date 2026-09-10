import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

class Solution {
    
    Set<Integer>[] graph;
    
    public int solution(int n, int[][] wires) {
        
        // 그래프 초기화
        graph = new Set[n + 1];
        
        for (int i = 1; i <= n; i++) {
            graph[i] = new HashSet<>();
        }
        
        // 그래프 그리기
        for (int[] wire : wires) {
            int v1 = wire[0];
            int v2 = wire[1];
            
            graph[v1].add(v2);
            graph[v2].add(v1);
        }
        
        // 송전탑 개수의 최소 차이 구하기
        int minDiff = Integer.MAX_VALUE;
        
        for (int[] wire : wires) {  // 모든 송전선에 대하여
            int v1 = wire[0];
            int v2 = wire[1];
            
            // 해당 전선 제거
            graph[v1].remove(v2);
            graph[v2].remove(v1);
            
            int net1 = search(1, n);    // BFS로 1번을 포함하는 네트워크의 정점 수 구하기
            int net2 = n - net1;        // 포함되지 않은 정점들은 나머지 네트워크에 포함
            
            minDiff = Math.min(minDiff, Math.abs(net1 - net2));
            
            // 전선 복구
            graph[v1].add(v2);
            graph[v2].add(v1);
        }
        
        return minDiff;
    }
    
    // BFS로 방문 가능한 정점 개수 반환
    private int search(int start, int n) {
        int vertexCount = 0;
        
        boolean[] visited = new boolean[n + 1];
        visited[start] = true;
        
        Queue<Integer> queue = new ArrayDeque<>();
        queue.offer(start);
        
        while (!queue.isEmpty()) {
            int curr = queue.poll();
            
            vertexCount += 1;
            
            for (int next : graph[curr]) {
                if (visited[next]) { continue; }
                
                visited[next] = true;
                queue.offer(next);
            }
        }
        
        return vertexCount;
    }
}
