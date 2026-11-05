package ws;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class swea1267_작업순서 {


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        for (int t = 1; t <= 10; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            sb.append("#").append(t).append(" ");

            int vCnt = Integer.parseInt(st.nextToken());
            int eCnt = Integer.parseInt(st.nextToken());
            ArrayList<Integer>[] graph = new ArrayList[vCnt + 1];
            ArrayDeque<Integer> q = new ArrayDeque<>();

            int[] indegree = new int[vCnt + 1];

            for (int i = 1; i <= vCnt; i++) {
                graph[i] = new ArrayList<>();
            }

            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < eCnt; i++) {
                int v = Integer.parseInt(st.nextToken());
                int u = Integer.parseInt(st.nextToken());

                graph[v].add(u);
                indegree[u]++;
            }

            for (int i = 1; i <= vCnt; i++) {
                if(indegree[i] == 0){
                    q.addLast(i);
                }
            }

            while(!q.isEmpty()){
                int cur = q.pollFirst();

                sb.append(cur).append(" ");
                for (int next : graph[cur]) {
                    indegree[next]--;
                    if(indegree[next] == 0){
                        q.addLast(next);
                    }
                }

            }
            sb.append("\n");
        }
        System.out.println(sb);
    }
}
