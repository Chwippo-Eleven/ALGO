import java.util.*;

class Solution {
    
    public int solution(int n, int s, int a, int b, int[][] fares) {
        // 지점 n
        // 출발 s
        // A도착 a
        // B도착 b
        List<List<int[]>> g = new ArrayList<>();
        for (int i=0; i<=n; i++){
            g.add(new ArrayList<>());
        }
        // 양방향 연결
        for (int[] x : fares){
            g.get(x[0]).add(new int[]{x[1], x[2]});
            g.get(x[1]).add(new int[]{x[0], x[2]});
        }
        
        //s시작 에서의 모든 정점까지의 거리들
        int[] sDist = ttt(s, g, n);
        //a시작 에서의 모든 정점까지의 거리들
        int[] aDist = ttt(a, g, n);
        //b시작 에서의 모든 정점까지의 거리들
        int[] bDist = ttt(b, g, n);

        //답
        int answer = Integer.MAX_VALUE;

        // 시작에서 i까지 + a에서 i까지 + b에서 i까지. 즉, i까지 같이 가고 i이후로 따로 가는거임
        for (int i = 1; i <= n; i++) {
            answer = Math.min(answer,
                    sDist[i] + aDist[i] + bDist[i]);
        }
        
        return answer;
    }
    public int[] ttt(int start, List<List<int[]>> g, int n) {

        int[] dist = new int[n + 1];// 해당 지점까지 거리 저장할 배열
        Arrays.fill(dist, Integer.MAX_VALUE);//최대로 초기화
        dist[start] = 0;//시작점은 당연히 거리0

        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        //가중치 있는 다익스트라는 우선순위큐로 걸고, 거리로 오름차순 정렬도 걸어줌
        pq.add(new int[]{start, 0}); // 시작점 넣어주고 시작

        while (!pq.isEmpty()) {

            int[] x = pq.poll();
            int pollStart = x[0];//현재 정점 위치
            int pollDist = x[1];//시작부터 현재까지의 누적 거리

            if (pollDist > dist[pollStart]) // 이미 저장된 거리가 작으면 갱신X
                continue;

            for (int[] xx : g.get(pollStart)) {//현재 정점에서부터 이어진 다음 정점들 탐색

                int next = xx[0]; // 다음 정점
                int newDist = pollDist + xx[1]; // 다음정점까지의 거리 갱신

                if (newDist < dist[next]) { // 만약 거리가 더 작으면 갱신
                    dist[next] = newDist; // 갱신
                    pq.add(new int[]{next, newDist}); // 다시 갱신을 위해 넣어줌
                }
            }
        }

        return dist;
    }
}
