import java.util.*;


public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        int[] a = new int[m];
        int[] b = new int[m];
        for (int i = 0; i < m; i++) {
            a[i] = sc.nextInt();
            b[i] = sc.nextInt();
        }
        // Please write your code here.
        StringBuilder sb = new StringBuilder();
        int[] indegree = new int[n + 1];

        ArrayDeque<Integer> q = new ArrayDeque<>();
        List<Integer>[] graph = new ArrayList[n + 1];

        for(int i = 0; i <= n; i++){
            graph[i] = new ArrayList<>();
        }

        for(int i = 0; i < m; i++){
            int v = a[i];
            int u = b[i];

            if(v == u) continue;
            indegree[u]++;
            graph[v].add(u);
        }

        for (int i = 1; i <= n; i++) {
            if (indegree[i] == 0) {
                q.offer(i);
            }
        }

        while(!q.isEmpty()){
            int cur = q.pollFirst();
            sb.append(cur + " ");
            for(int next : graph[cur]){
                indegree[next]--;
                if(indegree[next] == 0){
                    q.addLast(next);
                }
            }
        }

        System.out.print(sb);
    }
}