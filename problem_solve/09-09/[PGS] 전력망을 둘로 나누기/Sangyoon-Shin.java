import java.util.*;

class Solution {
    static ArrayList<Integer>[] g;
    static int res, cnt;
    public int solution(int n, int[][] wires) {

        g = new ArrayList[n + 1];

        for (int i = 1; i <= n; i++){
            g[i] = new ArrayList<>();
        }

        for (int i = 0; i < wires.length; i++){
            int v = wires[i][0];
            int w = wires[i][1];

            g[v].add(w);
            g[w].add(v);
        }

        // 연결됐던 와이어 하나씩 끊어보면서 dfs

        res = Integer.MAX_VALUE;

        for (int i = 0; i < wires.length; i++){
            int v = wires[i][0];
            int w = wires[i][1];

            boolean[] visited = new boolean[n + 1];
            cnt = 0;
            dfs(v, v, w, visited); // (v, w) 전선이 있다 하면, v부터 dfs 돌도록.
            res = Math.min(res, Math.abs(cnt - (n - cnt)));
        }
        return res;
    }
    public static void dfs(int start, int v, int w, boolean[] visited){
        visited[start] = true;
        cnt++;

        for (int next : g[start]){
            // 끊어진 관계를 이렇게 표현할 수 있지
            if ((start == v && next == w) || (start == w && next == v)){
                continue;
            }
            if (!visited[next]){
                dfs(next, v, w, visited);
            }
        }
    }
}