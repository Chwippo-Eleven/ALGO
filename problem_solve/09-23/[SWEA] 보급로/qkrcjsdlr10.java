package swea;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Swea1249 {

    static int n;
    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {-1, 0, 1, 0};

    static class Node{
        int x, y, cost;

        Node(int y, int x, int cost){
            this.y = y;
            this.x = x;
            this.cost = cost;
        }
    }

    public static boolean isIn(int y, int x){
        return 0 <= y && y < n && 0 <= x && x < n;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int tc = Integer.parseInt(br.readLine());
        for (int t = 1; t <= tc; t++) {
            n = Integer.parseInt(br.readLine());

            int[][] board = new int[n][n];
            int[][] dist = new int[n][n];

            PriorityQueue<Node> pq = new PriorityQueue<>((a, b) -> a.cost - b.cost);


            for (int i = 0; i < n; i++) {
//            StringTokenizer st = new StringTokenizer(br.readLine());
                String value = br.readLine();
                for (int j = 0; j < n; j++) {
                    board[i][j] = Integer.parseInt(String.valueOf(value.charAt(j)));
                }
            }

            for (int i = 0; i < n; i++) {
                Arrays.fill(dist[i], Integer.MAX_VALUE);
            }

            dist[0][0] = 0;
            pq.add(new Node(0, 0, 0));

            while(!pq.isEmpty()){
                Node cur = pq.poll();

                if(cur.cost > dist[cur.y][cur.x]){
                    continue;
                }

                for (int d = 0; d < 4; d++) {
                    int nx = cur.x + dx[d];
                    int ny = cur.y + dy[d];

                    if(!isIn(ny, nx)) continue;
                    int nextCost = dist[cur.y][cur.x] + board[ny][nx];
                    if(nextCost < dist[ny][nx]){
                        dist[ny][nx] = nextCost;
                        pq.add(new Node(ny, nx, nextCost));
                    }
                }

            }
            sb.append("#").append(t).append(" ").append(dist[n - 1][n - 1]).append("\n");
        }
        System.out.println(sb);
    }
}
