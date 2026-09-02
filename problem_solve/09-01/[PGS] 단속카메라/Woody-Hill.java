import java.util.PriorityQueue;

class Solution {
    
    static class Route implements Comparable<Route> {
        int entry;
        int exit;
        
        Route(int entry, int exit) {
            this.entry = entry;
            this.exit  = exit;
        }
        
        @Override
        public int compareTo(Route other) {
            return this.exit - other.exit;
        }
    }
    
    public int solution(int[][] routes) {
        
        PriorityQueue<Route> pq = new PriorityQueue<>();
        
        for (int[] route : routes) {
            pq.add(new Route(route[0], route[1]));
        }
        
        int cameraCount = 0;
        
        while (!pq.isEmpty()) {
            Route route = pq.poll();
            
            // 진출 지점이 가장 빠른 경로의 끝에 카메라 설치
            int cameraPoint = route.exit;
            cameraCount += 1;
            
            // 해당 카메라에 걸리는 경로는 우선순위 큐에서 빼기
            while (!pq.isEmpty() && pq.peek().entry <= cameraPoint) {
                pq.poll();
            }
        }
        
        return cameraCount;
    }
}
