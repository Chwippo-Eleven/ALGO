import java.util.Arrays;
import java.util.PriorityQueue;

class Solution {
    
    private final int INF = Integer.MAX_VALUE;
    
    private final int[] dr = {-1, 1, 0 ,0};
    private final int[] dc = {0, 0, -1, 1};
    
    public int solution(int[][] board) {
        
        int n = board.length;
        
        // 불필요한 방문을 피하기 위한 최소 비용 배열. [행, 열, 방향]으로 접근
        int[][][] minCost = new int[n][n][4];
        
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                Arrays.fill(minCost[r][c], INF);    // 초기화
            }
        }
        
        // (행, 열, 방향, 비용)을 원소로 갖는 우선순위 큐. 비용의 오름차순으로 정렬!!!
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[3] - b[3]);
        
        // 0: UP, 1: DOWN, 2: LEFT, 3: RIGHT
        
        // 시작 지점 설정: 아래, 오른쪽 방향만 이동 가능!
        pq.offer(new int[] {0, 0, 1, 0});
        minCost[0][0][1] = 0;
        
        pq.offer(new int[] {0, 0, 3, 0});
        minCost[0][0][3] = 0;
        
        // 현재 탐색중인 경로 중 최소 비용의 경로를 뽑아가며 도착지까지 이동!
        while (!pq.isEmpty()) {
            int[] state = pq.poll();
            
            int currRow  = state[0];
            int currCol  = state[1];
            int currDir  = state[2];
            int currCost = state[3];
            
            // 불필요한 상태 버리기
            if (currCost > minCost[currRow][currCol][currDir]) continue;
            
            for (int dir = 0; dir < 4; dir++) {
                int nextRow = currRow + dr[dir];
                int nextCol = currCol + dc[dir];
                
                // 범위 밖이거나 벽이면 건너뛰기
                if (!isIn(nextRow, nextCol, n) || board[nextRow][nextCol] == 1) continue;
                
                // 직선 경로는 100원이고 코너는 100+500=600원
                int nextCost = currCost + (dir == currDir ? 100 : 600);
                
                if (nextCost < minCost[nextRow][nextCol][dir]) {
                    pq.offer(new int[] {nextRow, nextCol, dir, nextCost});
                    minCost[nextRow][nextCol][dir] = nextCost;
                }
            }
        }
        
        // 아래 또는 오른쪽 방향으로 들어오는 경우만 가능. 둘 중 최소가 최소 비용!
        return Math.min(minCost[n - 1][n - 1][1], minCost[n - 1][n - 1][3]);
    }
    
    private boolean isIn(int r, int c, int n) {
        return 0 <= r && r < n && 0 <= c && c < n;
    }
}
