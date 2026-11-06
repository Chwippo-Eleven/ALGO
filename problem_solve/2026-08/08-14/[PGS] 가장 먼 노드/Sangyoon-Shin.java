import java.util.*;

class Solution {
    static ArrayList<Integer>[] g;
    static boolean[] visited;
    static int[] dist;
    public int solution(int n, int[][] edge) {
        // 2차원 배열 -> 그래프
        // 1에서 bfs. 거리 배열에 기록하고 max값 갖는 idx수 리턴

        g = new ArrayList[n + 1];
        visited = new boolean[n + 1];
        dist = new int[n + 1];

        for (int i = 1; i <= n; i++){
            g[i] = new ArrayList<>();
        }

        for (int i = 0; i < edge.length; i++){
            int v = edge[i][0];
            int w = edge[i][1];

            g[v].add(w);
            g[w].add(v);
        }

        bfs();

        int max = Arrays.stream(dist).max().getAsInt();

        int cnt = 0;
        for (int i = 2; i <= n; i++){
            if (dist[i] == max){
                cnt++;
            }
        }
        return cnt;
    }
    public void bfs(){
        ArrayDeque<Integer> q = new ArrayDeque<>();

        visited[1] = true;
        q.addLast(1);

        while (!q.isEmpty()){
            int cur = q.removeFirst();
            for (int next : g[cur]){
                if (!visited[next]){
                    visited[next] = true;
                    dist[next] = dist[cur] + 1;
                    q.addLast(next);
                }
            }
        }
    }
}