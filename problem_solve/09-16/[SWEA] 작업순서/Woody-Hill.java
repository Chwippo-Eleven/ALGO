import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.StringTokenizer;
 
public class Solution {
     
    private static int V;
    private static List<Integer>[] graph;
     
    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
         
        int T = 10;
         
        for (int testCase = 1; testCase <= T; testCase++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
             
            V = Integer.parseInt(st.nextToken());
            int E = Integer.parseInt(st.nextToken());
             
            graph = new List[V + 1];
             
            int[] inDegree = new int[V + 1];            // 정점별 진입 차수의 배열
            Set<Integer> entryVertex = new HashSet<>(); // 가능한 시작 정점의 집합
             
            for (int i = 1; i <= V; i++) {
                graph[i] = new ArrayList<>(V);
                entryVertex.add(i);
            }
             
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < E; i++) {
                int v1 = Integer.parseInt(st.nextToken());
                int v2 = Integer.parseInt(st.nextToken());
                 
                graph[v1].add(v2);
                 
                inDegree[v2] += 1;
                entryVertex.remove(v2);
            }
             
            int[] order = topologySort(entryVertex, inDegree);
             
            bw.write("#" + testCase);
            for (int v : order) {
                bw.write(" " + v);
            }
            bw.write("\n");
        }
        bw.flush();
        bw.close();
        br.close();
    }
     
    private static int[] topologySort(Set<Integer> entryVertex, int[] inDegree) {
         
        int[] order = new int[V];
        int index = 0;
         
        Queue<Integer> queue = new ArrayDeque<>();
        queue.addAll(entryVertex);
         
        while (!queue.isEmpty()) {
            int current = queue.poll();
             
            order[index++] = current;
             
            for (int next : graph[current]) {
                inDegree[next] -= 1;
                 
                if (inDegree[next] == 0) {
                    queue.offer(next);
                }
            }
        }
         
        return order;
    }
}
