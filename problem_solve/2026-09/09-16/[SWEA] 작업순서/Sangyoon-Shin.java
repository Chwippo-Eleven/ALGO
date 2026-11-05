import java.io.*;
import java.util.*;

public class Solution {
    static int vertex, edge, v, w;
    static ArrayList<Integer>[] g;
    static ArrayList<Integer> order;
    static int[] inCnt;
    static ArrayDeque<Integer> q;

    public static void main(String[] args) throws IOException{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        for (int tc = 1; tc <= 10; tc++) {

            st = new StringTokenizer(br.readLine());

            vertex = Integer.parseInt(st.nextToken());
            edge = Integer.parseInt(st.nextToken());

            g = new ArrayList[vertex + 1]; // 그래프

            for (int i = 1; i <= vertex; i++) {
                g[i] = new ArrayList<>();
            }

            order = new ArrayList<>(); // 결과 담을 리스트
            inCnt = new int[vertex + 1]; // 진입 차수 담을 배열

            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < edge; i++) {
                v = Integer.parseInt(st.nextToken());
                w = Integer.parseInt(st.nextToken());

                // 그래프에 추가 + 진입차수 업데이트
                g[v].add(w);
                inCnt[w]++;
            }

            // 위상정렬 알고리즘 사용할 것
            // 1. 진입차수가 0인 애를 큐에 넣는다
            // 2-1. 큐에서 값을 뽑고, 그 노드에서 뻗는 간선을 지운다
            // 2-2. 간선을 지움으로써 어떤 노드는 진입차수가 0이 될텐데, 그 노드를 다시 큐에 넣고 반복한다

            q = new ArrayDeque<>();
            for (int i = 1; i <= vertex; i++) {
                if (inCnt[i] == 0) {
                    q.offerLast(i);
                }
            }

            topologicalSort();

            StringBuilder sb = new StringBuilder();
            sb.append("#").append(tc).append(" ");

            for (int res : order) {
                sb.append(res).append(" ");
            }

            System.out.println(sb);
        }
    }

    public static void topologicalSort() {

        while (!q.isEmpty()) {

            int cur = q.pollFirst();
            // 큐에서 뽑은 값은 결과 리스트에 추가
            order.add(cur);

            for (int next : g[cur]) { // 현재 노드에서 연결된 노드와 간선 연결 끊고, 진입차수 업데이트
                inCnt[next]--;

                if (inCnt[next] == 0) {
                    q.offerLast(next); // 진입차수 0된 애는 새롭게 추가해주기
                }
            }
        }
    }
}