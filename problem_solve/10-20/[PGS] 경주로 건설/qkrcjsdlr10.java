import java.util.*;

class Solution {

    int[] dx = {0, 1, 0, -1};
    int[] dy = {-1, 0, 1, 0};

    public class Node{
        int y, x, dir, cost;

        public Node(int y, int x, int dir, int cost){
            this.y = y;
            this.x = x;
            this.dir = dir;
            this.cost = cost;
        }
    }

    public int solution(int[][] board) {
        int answer = 0;

        PriorityQueue<Node> pq = new PriorityQueue<>((a, b) -> Integer.compare(a.cost, b.cost));
        int n = board.length;
        int[][][] cost = new int[n][n][4];
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                Arrays.fill(cost[i][j], Integer.MAX_VALUE);
            }
        }
        cost[0][0][1] = 0;
        cost[0][0][2] = 0;
        pq.add(new Node(0, 0, 1, 0));
        pq.add(new Node(0, 0, 2, 0));
        
        while(!pq.isEmpty()){
            Node cur = pq.poll();

            for(int d = 0; d < 4; d++){
                int nx = cur.x + dx[d];
                int ny = cur.y + dy[d];
                int temp = cur.cost;

                if(nx >= n || nx < 0 || ny >= n || ny < 0) continue;
                if(board[ny][nx] == 1) continue;
                int newCost = temp;
                if(cur.dir != d) newCost += 600;
                else if(cur.dir == d) newCost += 100;
                if(newCost >= cost[ny][nx][d]) continue;

                cost[ny][nx][d] = newCost;
                pq.add(new Node(ny, nx, d, newCost));
            }

        }
        answer = Math.min(cost[n-1][n-1][0], Math.min(cost[n-1][n-1][1], Math.min(cost[n-1][n-1][2], cost[n-1][n-1][3])));
        return answer;
    }
}