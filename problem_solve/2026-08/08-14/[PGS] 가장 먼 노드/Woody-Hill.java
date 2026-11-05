import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {
    
    final int INF = Integer.MAX_VALUE;
    
    public int solution(int n, int[][] edge) {
        // 리스트의 배열로 그래프 생성
        List<Integer>[] graph = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }
        
        // 노드 a와 b를 연결
        for (int[] e : edge) {
            graph[e[0]].add(e[1]);
            graph[e[1]].add(e[0]);
        }
        
        // 시작점을 1로 하는 너비 우선 탐색
        int[] dist = searchByBFS(n, 1, graph);
        dist[0] = -1;   // 갈 수 없는 0번 노드 지우기(최댓값 탐색에서 배제)
        
        int farthestDist  = 0;
        int farthestCount = 0;
        
        for (int d : dist) {
            if (d > farthestDist) {
                farthestDist  = d;
                farthestCount = 1;
            } else if (d == farthestDist) {
                farthestCount += 1;
            }
        }
        
        return farthestCount;
    }
    
    // 너비 우선 탐색을 통해 시작 노드 s와의 거리를 반환하는 함수
    public int[] searchByBFS(int n, int s, List<Integer>[] graph) {
        int[] dist = new int[n + 1];
        Arrays.fill(dist, INF);
        
        ArrayDeque<Integer> queue = new ArrayDeque<>();
        
        dist[s] = 0;
        queue.add(s);
        
        while (!queue.isEmpty()) {
            int curr = queue.poll();
            
            for (int next : graph[curr]) {
                if (dist[next] != INF) continue; // 방문한 노드 건너뛰기
                
                dist[next] = dist[curr] + 1;
                queue.add(next);
            }
        }
        
        return dist;
    }
}
