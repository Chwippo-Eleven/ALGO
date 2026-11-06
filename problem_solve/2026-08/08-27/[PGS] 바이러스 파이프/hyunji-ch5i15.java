import java.util.*;

class Solution {
    static int n, k, answer;
    static List<int[]> [] adj; // adj[node] = { {neighbor, type}, ... }
    
    public int solution(int n, int infection, int[][] edges, int k) {
        this.n = n;
        this.k = k;
        adj = new List[n+1];
        for (int i=1; i<=n; i++) {
            adj[i] = new ArrayList<>();
        }
        for (int[]e : edges) {
            adj[e[0]].add(new int[]{e[1], e[2]});
            adj[e[1]].add(new int[]{e[0], e[2]});
        }
        
        boolean[] infected = new boolean[n+1];
        infected[infection] = true;
        answer = 1;
        
        backtrack(infected, 1, 0, 0);
        infected[infection] = true;
        
        answer = 1; // 시작 감염 노드
        
        backtrack(infected, 1, 0, 0);
        return answer;
    }
    
    static void backtrack(boolean[] infected, int count, int depth, int lastType) {
        answer = Math.max(answer, count);
        if (depth == k || count == n) return; // 종료조건
        
        for (int t =1; t<=3; t++) {
            if (t==lastType) continue; // 같은 타입 연속으로 여는 건 의미 없음
            
            boolean[] next = infected.clone();
            int newCount = spread(next, count, t);
            backtrack(next, newCount, depth+1, t);
        }
    }
    
    // 현재 감염된 노드들에서 타입 t 간선만 타고 퍼뜨리기 (멀티소스 BFS)
    static int spread(boolean[] infected, int count, int type) {
        Queue<Integer> q = new ArrayDeque<>();
        for (int i=1; i<=n; i++) {
            if (infected[i]) q.offer(i);
        }
        
        while (!q.isEmpty()) {
            int cur = q.poll();
            for (int[] nb: adj[cur]) {
                int next = nb[0], t = nb[1];
                if (t == type && !infected[next]) {
                    infected[next] = true;
                    count++;
                    q.offer(next);
                }
            }
        }
        return count;
    }
    
    
    
    
    
    
    
    
    
}