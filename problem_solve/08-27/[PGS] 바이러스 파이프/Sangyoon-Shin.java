import java.util.*;

class Solution {
    static int K, start, N;
    static int res;
    static ArrayList<int[]>[] g;
    public int solution(int n, int infection, int[][] edges, int k) {

        // 3개를 순서있게 k번 뽑아야하잖아?
        K = k;
        N = n;
        start = infection;
        res = 0;

        g = new ArrayList[N + 1];
        for (int i = 1; i <= N; i++){
            g[i] = new ArrayList<>();
        }

        for (int[] edge : edges){
            int v = edge[0];
            int w = edge[1];
            int type = edge[2];

            g[v].add(new int[] {w, type});
            g[w].add(new int[] {v, type});
        }

        int[] cur = new int[k];
        dfs(cur, -1, 0);

        return res;
    }
    public void dfs(int[] cur, int prev, int depth){
        if (depth == K){
            // 파이프를 열 순서를 구했으니까, 순서대로 열었다 닫았다 해보면서 감염시켜보자
            boolean[] visited = new boolean[N + 1];
            visited[start] = true;
            infect(cur, visited);
            return;
        }
        for (int i = 1; i <= 3; i++){
            if (i != prev){
                cur[depth] = i;
                dfs(cur, i, depth + 1);
            }
        }
    }
    public void infect(int[] cur, boolean[] visited){
        ArrayDeque<Integer> q = new ArrayDeque<>();
        for (int type : cur){
            // 현재 타입에 해당하는 간선을 다 열어봐야함.
            // 여기서 핵심은 일단 감염된 애들을 다 넣는 것임. -> 감염된 애들을 통해서만 해당되는 타입의 간선과 연결된 노드로 전달되니까
            for (int i = 1; i <= N; i++){
                if (visited[i]){
                    q.addLast(i);
                }
            }
            while (!q.isEmpty()){
                // 이제 이미 감염되어있던 애들 중에서 현재 검사하는 타입의 간선을 공유하는 노드랑 연결해주기
                int current = q.removeFirst();
                for (int[] next : g[current]){
                    int nextNode = next[0];
                    int edgeType = next[1];
                    if (edgeType == type && !visited[nextNode]){
                        visited[nextNode] = true;
                        q.addLast(nextNode);
                    }
                }
            }
        }
        // 한 순열에 대한 bfs가 끝났을 때, 방문한 노드 개수를 구해야함
        int cnt = 0;
        for (boolean b : visited){
            if (b){
                cnt++;
            }
        }
        res = Math.max(res, cnt);
    }
}