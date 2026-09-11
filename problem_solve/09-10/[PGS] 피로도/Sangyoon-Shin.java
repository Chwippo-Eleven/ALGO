import java.util.*;

class Solution {
    static int res;
    public int solution(int k, int[][] dungeons) {

        res = 0;

        boolean[] visited = new boolean[dungeons.length];
        dfs(0, dungeons, k, 0, visited);
        return res;
    }
    public void dfs(int depth, int[][] d, int cur, int cnt, boolean[] visited){

        res = Math.max(res, cnt);

        if (depth == d.length){ // 모두 탐색한 경우
            return;
        }
        if (cur <= 0){ // 체력없어서 더 들어갈 곳 없음
            return;
        }
        for (int i = 0; i < d.length; i++){
            if (!visited[i]){
                if (cur >= d[i][0]){ // 입구컷 안당하는 조건
                    visited[i] = true;
                    dfs(depth + 1, d, cur - d[i][1], cnt + 1, visited);
                    visited[i] = false;
                }
            }
        }
    }
}