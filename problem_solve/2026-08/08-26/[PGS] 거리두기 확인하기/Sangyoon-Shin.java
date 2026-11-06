import java.util.*;

class Solution {
    static final int SZ = 5;
    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, -1, 1};
    public int[] solution(String[][] places) {

        int tc = places.length;
        int[] res = new int[tc];

        for (int t = 0; t < tc; t++){
            res[t] = isPossible(places[t]); // 각 테스트케이스가 실행 가능한지 확인
        }
        return res;

    }
    public boolean bfs(int r, int c, String[] map){
        ArrayDeque<int[]> q = new ArrayDeque<>();
        boolean[][] visited = new boolean[SZ][SZ];

        visited[r][c] = true;
        q.addLast(new int[] {r, c, 0});

        while (!q.isEmpty()){
            int[] cur = q.removeFirst();

            for (int i = 0; i < 4; i++){
                int ddr = cur[0] + dr[i];
                int ddc = cur[1] + dc[i];
                int dist = cur[2];

                if (dist == 2){
                    continue;
                }

                if (!isIn(ddr, ddc)){
                    continue;
                }
                if (visited[ddr][ddc]){
                    continue;
                }
                if (map[ddr].charAt(ddc) == 'P'){ // 거리가 2 미만인 지점에서 P가 있는거니까 실패
                    return false;
                }
                if (map[ddr].charAt(ddc) == 'X'){
                    continue;
                }
                q.addLast(new int[] {ddr, ddc, dist + 1});
                visited[ddr][ddc] = true;

            }
        }
        return true;
    }
    public int isPossible(String[] map){
        for (int r = 0; r < 5; r++){
            for (int c = 0; c < 5; c++){
                if (map[r].charAt(c) == 'P'){ // P인 지점에서 bfs
                    boolean find = bfs(r, c, map);
                    if (!find){ // 한 번이라도 실패하면 끝
                        return 0;
                    }
                }
            }
        }
        return 1;
    }
    public boolean isIn(int r, int c){
        return r >= 0 && r < SZ && c >= 0 && c < SZ;
    }
}