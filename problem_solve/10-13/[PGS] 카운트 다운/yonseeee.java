import java.util.*;

public class Main {
    static List<Integer>[]graph;
    static int[] indegree;
    static int n, m;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        m = sc.nextInt();
        int[] a = new int[m];
        int[] b = new int[m];

        indegree = new int[n+1];
        graph = new ArrayList[n+1];
        for(int i=1;i<=n;i++){
            graph[i] = new ArrayList<>();
        }
        
        for (int i = 0; i < m; i++) {
            a[i] = sc.nextInt();
            b[i] = sc.nextInt();

            graph[a[i]].add(b[i]);
            indegree[b[i]]++;
        }

        List<Integer> order = TopologicalSort();

        for(int i=0;i<order.size();i++){
            System.out.print(order.get(i)+ " ");
        }

    }
    public static List<Integer> TopologicalSort(){
        List<Integer> order = new ArrayList<>();
        Queue<Integer> queue = new ArrayDeque<>();


        for(int i=1;i<=n;i++){
            if(indegree[i]==0){
                queue.offer(i);
            }
        }

        while(!queue.isEmpty()){
            int cur = queue.poll();
            order.add(cur);

            for(int next:graph[cur]){
                if(--indegree[next]==0) queue.offer(next);
            }
        }

        return order;
    }
}
