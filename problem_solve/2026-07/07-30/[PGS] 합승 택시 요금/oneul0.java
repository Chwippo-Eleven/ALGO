import java.util.*;
class Solution {
    class Edge implements Comparable<Edge> {
        int to, cost;
        Edge(int to, int cost){
            this.to = to;
            this.cost = cost;
        }
        @Override
        public int compareTo(Edge o){
            return Integer.compare(this.cost, o.cost);
        }
    }
    int n;
    List<List<Edge>> gr = new ArrayList<>();
    public int solution(int n, int s, int a, int b, int[][] fares) {
        this.n = n;
        for(int i = 0; i<=n; i++){
            gr.add(new ArrayList<>());
        }
        for(int[] fare : fares){
            gr.get(fare[0]).add(new Edge(fare[1], fare[2]));
            gr.get(fare[1]).add(new Edge(fare[0], fare[2]));
        }

        //겹칠때?
        /*
        a, b와 가까운 임의의 지점 p에 대해
        s-p, a-p, b-p를 구하고
        합치면 됨?
        임의의 점 p는 s, a, b일 수도 있음
        근데 p에서의 거리의 합이 가장 작은 경우는 특정 노드에 쏠릴 수 있음
        */
        int[] ap = dijikstra(a);
        int[] bp = dijikstra(b);
        int[] sp = dijikstra(s);
        int answer = Integer.MAX_VALUE;
        int idx = 0;
        for(int i =1 ;i<=n; i++){
            answer = Math.min(answer, sp[i] + ap[i] + bp[i]);
        }
        return answer;
    }

    public int[] dijikstra(int start){
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        pq.offer(new Edge(start, 0));
        int[] costs = new int[n+1];
        Arrays.fill(costs, 30000000);
        costs[start] = 0;

        while(!pq.isEmpty()){
            Edge cur = pq.poll();

            for(Edge next : gr.get(cur.to)){
                int nextCost = cur.cost + next.cost;
                if(costs[next.to] > nextCost){
                    pq.offer(new Edge(next.to, nextCost));
                    costs[next.to] = nextCost;
                }
            }
        }

        return costs;
    }
}