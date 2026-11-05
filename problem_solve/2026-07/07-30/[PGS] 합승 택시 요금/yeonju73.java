import java.util.*;

class Solution {
    class Node implements Comparable<Node> {
        int index;
        int distance;
        
        public Node(int index, int distance){
            this.index = index;
            this.distance = distance;
        }
        
        @Override
        public int compareTo(Node other) {
            return Integer.compare(this.distance, other.distance);
        }
    }
    
    public int solution(int n, int s, int a, int b, int[][] fares) {
        int edge = fares.length;
        
        ArrayList<ArrayList<Node>> graph = new ArrayList<>();
        
        // 초기화
        for(int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }
        
        // 양방향 그래프
        for(int[] f: fares){
            graph.get(f[0]).add(new Node(f[1], f[2]));
            graph.get(f[1]).add(new Node(f[0], f[2]));
        }
        
        // 최단 거리 테이블
        int[] distS = new int[n + 1];
        int[] distA = new int[n + 1];
        int[] distB = new int[n + 1];
        
        Arrays.fill(distS, Integer.MAX_VALUE);
        Arrays.fill(distA, Integer.MAX_VALUE);
        Arrays.fill(distB, Integer.MAX_VALUE);
        
        dijkstra(s, graph, distS);
        dijkstra(a, graph, distA);
        dijkstra(b, graph, distB);
        
        // 모든 정점 X를 합승 해제 지점으로 가정하고 최소 비용 계산
        int answer = Integer.MAX_VALUE;
        for(int x = 1; x <= n; x++) {
            if(distS[x] != Integer.MAX_VALUE && distA[x] != Integer.MAX_VALUE && distB[x] != Integer.MAX_VALUE) {
                int cost = distS[x] + distA[x] + distB[x];
                answer = Math.min(answer, cost);
            }
        }
        
        return answer;
    }
    
    public void dijkstra(int start, ArrayList<ArrayList<Node>> graph, int[] d) {
        // 거리가 짧은 순으로 보기 위해 우선순위 큐에 삽입
        PriorityQueue<Node> pq = new PriorityQueue<>();
        
        pq.offer(new Node(start, 0));
        d[start] = 0;
        
        while (!pq.isEmpty()) {
            Node node = pq.poll();
            int dist = node.distance;
            int now = node.index;
            
            // 현재 노드가 이미 처리된 적이 있는 노드면 무시
            if (d[now] < dist) continue;
            
            // 인접한 다른 노드 확인
            for(int i = 0; i < graph.get(now).size(); i++) {
                int cost = d[now] + graph.get(now).get(i).distance;
                
                // 현재 노드 통해서 인접노드로 가는 거리 < 인접 노드로 직행
                if (cost < d[graph.get(now).get(i).index]) {
                    // 인접 노드로 가는 최단 거리 갱신
                    d[graph.get(now).get(i).index] = cost;
                    pq.offer(new Node(graph.get(now).get(i).index, cost));
                    
                }
            }
            
        }
        
    }
}
