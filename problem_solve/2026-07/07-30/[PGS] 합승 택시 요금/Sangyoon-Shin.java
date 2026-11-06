import java.util.*;

// graph[idx] = idx -> next 까지 가는 비용이 cost인 간선
class Edge{
    int next;
    int cost;
    Edge(int next, int cost){
        this.next = next;
        this.cost = cost;
    }
}

// 우선순위큐에 넣을 후보값
class State{
    int node;
    int dist;
    State(int node, int dist){
        this.node = node;
        this.dist = dist;
    }
}

class Solution {
    public int solution(int n, int s, int a, int b, int[][] fares) {

        // 리스트 배열로 그래프 생성
        List<Edge>[] graph = new ArrayList[n + 1];

        for (int i = 1; i <= n; i++){
            graph[i] = new ArrayList<>();
        }

        for (int i = 0; i < fares.length; i++){
            int v = fares[i][0];
            int w = fares[i][1];
            int cost = fares[i][2];

            graph[v].add(new Edge(w, cost));
            graph[w].add(new Edge(v, cost));
        }

        int[] distS = dijkstra(n, s, graph);
        int[] distA = dijkstra(n, a, graph);
        int[] distB = dijkstra(n, b, graph);

        int ans = Integer.MAX_VALUE;

        // k는 어디까지 같이 갔는지를 의미함
        // s -> k + k -> a + k -> b 의 값이 가장 작은 걸 구해야함
        // k -> a = a -> k 와 동일함으로 s, a, b에서 k까지 가는 거리를 다 구해보는 것
        for (int k = 1; k <= n; k++){
            int totalCost = distS[k] + distA[k] + distB[k];
            ans = Math.min(ans, totalCost);
        }
        return ans;

    }
    static int[] dijkstra(int n, int start, List<Edge>[] graph){
        // 다익스트라 알고리즘
        // 1. 모든 정점 무한대로, 시작지점 0으로 초기화
        int[] dist = new int[n + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[start] = 0;

        // 2. 누적거리 짧은 애부터 탐색하기 위한 우선순위큐
        PriorityQueue<State> pq = new PriorityQueue<>
                ((s1, s2) -> Integer.compare(s1.dist, s2.dist));

        // 3. 큐에 시작지점 넣고 큐가 빌때까지 반복
        pq.offer(new State(start, 0));

        while (!pq.isEmpty()){
            State cur = pq.poll();

            int curNode = cur.node; // 지금 탐색할 정점
            int curDist = cur.dist; // 시작점 -> 현재 정점까지 거리

            if (curDist > dist[curNode]){ // dist 배열에 curNode 까지 오는 더 짧은 경로가 있으면 패스
                continue;
            }

            for (Edge edge : graph[curNode]){ // 아닌 경우에 인접 정점 모두 확인
                int nextNode = edge.next;
                int nextDist = curDist + edge.cost; // 다음 정점으로 가는 거리 = 현재 까지 온 거리 + cur -> next로 가는 비용

                if (nextDist < dist[nextNode]){ // 그게 더 짧으면 갱신해주고 큐에 넣기
                    dist[nextNode] = nextDist;
                    pq.offer(new State(nextNode, nextDist));
                }
            }
        }
        return dist;
    }
}