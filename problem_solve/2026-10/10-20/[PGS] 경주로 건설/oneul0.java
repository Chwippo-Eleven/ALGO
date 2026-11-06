import java.util.*;
class Solution {

    class Node implements Comparable<Node>{
        int x, y, cost, lastDirection;
        Node(int x, int y, int lastDirection, int cost){
            this.x = x;
            this.y = y;
            this.lastDirection = lastDirection;
            this.cost = cost;
        }

        @Override
        public int compareTo(Node o){
            return Integer.compare(this.cost, o.cost);
        }
    }
    
    int[] dx = {-1,1,0,0}, dy = {0,0,-1,1};
    int answer = Integer.MAX_VALUE, N = 0;
    public int solution(int[][] board) {
        this.N = board.length;
        PriorityQueue<Node> pq = new PriorityQueue<>();
        int[][][] chk = new int[N][N][4];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                Arrays.fill(chk[i][j], Integer.MAX_VALUE);
            }
        }

        for(int d = 0; d<4; d++){
            int nx = dx[d];
            int ny = dy[d];
            if(nx<0 || nx>=N || ny<0 || ny >= N || board[nx][ny] == 1) continue;
            pq.offer(new Node(nx, ny, d, 100));
            chk[nx][ny][d] = 100;
        }

        while(!pq.isEmpty()){
            Node cur = pq.remove();

            if(cur.x == N-1 && cur.y == N-1) break;

            for(int d = 0; d<4; d++){
                int nx = cur.x + dx[d];
                int ny = cur.y + dy[d];
                if(nx<0 || nx>=N || ny<0 || ny >= N || board[nx][ny] == 1) continue;

                int cost = cur.cost + (cur.lastDirection == d ? 100 : 600);

                if(chk[nx][ny][d] > cost) {
                    pq.offer(new Node(nx, ny, d, cost));
                    chk[nx][ny][d] = cost;
                }
            }
        }

        for(int num : chk[N-1][N-1]){
            answer = Math.min(num, answer);
        }
        return answer;
    }
}

