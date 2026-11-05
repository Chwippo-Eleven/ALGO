import java.util.*;

class Solution {
    static int[][] map;
    static int[][] dist;
    static boolean[][] visited;
    static int res;
    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, -1, 1};
    public int solution(int[][] rectangle, int characterX, int characterY, int itemX, int itemY) {
        map = new int[102][102]; // 핵심: 좌표를 2배로 늘려서 표현하자

        for (int[] rec : rectangle){
            draw(rec);
        }

        int startR = characterY * 2;
        int startC = characterX * 2;
        int endR = itemY * 2;
        int endC = itemX * 2;

        dist = new int[102][102];
        visited = new boolean[102][102];
        res = bfs(startR, startC, endR, endC);

        return res;
    }
    public void draw(int[] rec){
        int x1 = rec[0] * 2;
        int y1 = rec[1] * 2;
        int x2 = rec[2] * 2;
        int y2 = rec[3] * 2;

        for (int r = y1; r <= y2; r++){
            for (int c = x1; c <= x2; c++){

                // 좌하단, 우상단 좌표와 행/열 값이 같으면 가장자리
                // 한 사각형의 가장자리 부분이더라도 다른 사각형의 내부라면 가장자리가 아님
                boolean isEdge = r == y1 || r == y2 || c == x1 || c == x2;
                if (isEdge){
                    if (map[r][c] != 2){
                        map[r][c] = 1;
                    }
                } else {
                    map[r][c] = 2;
                }
            }
        }
    }
    public int bfs(int sR, int sC, int eR, int eC){
        ArrayDeque<int[]> q = new ArrayDeque<>();
        visited[sR][sC] = true;
        q.addLast(new int[] {sR, sC});

        while (!q.isEmpty()){
            int[] cur = q.removeFirst();
            if (cur[0] == eR && cur[1] == eC){ // 도착 지점 도달시 좌표 2배로 늘렸으므로 이동거리도 절반으로
                return dist[cur[0]][cur[1]] / 2;
            }
            for (int i = 0; i < 4; i++){
                int ddr = cur[0] + dr[i];
                int ddc = cur[1] + dc[i];
                if (ddr >= 0 && ddr < 102 && ddc >= 0 && ddc < 102){
                    if (map[ddr][ddc] == 1 && !visited[ddr][ddc]){
                        visited[ddr][ddc] = true;
                        q.addLast(new int[] {ddr, ddc});
                        dist[ddr][ddc] = dist[cur[0]][cur[1]] + 1;
                    }
                }
            }
        }
        return 0;
    }
}