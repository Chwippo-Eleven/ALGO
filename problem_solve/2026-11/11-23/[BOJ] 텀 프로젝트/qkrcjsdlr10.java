package boj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.StringTokenizer;

public class boj9466 {

    static int n;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int tc = Integer.parseInt(br.readLine());
        for (int t = 0; t < tc; t++) {
            n = Integer.parseInt(br.readLine());
            StringTokenizer st = new StringTokenizer(br.readLine());
            int[] arr = new int[n + 2];
            int[] indegree = new int[n + 2];
            ArrayDeque<Integer> q = new ArrayDeque<>();
            for (int i = 1; i <= n; i++) {
                arr[i] = Integer.parseInt(st.nextToken());
                indegree[arr[i]]++;
            }

//        for (int i : indegree) {
//            System.out.print(i + " ");
//        }

            for (int i = 1; i <= n; i++) {
                if(indegree[i] == 0){
                    q.addLast(i);
                }
            }

            int cnt = 0;

            while(!q.isEmpty()){
                int cur = q.pollFirst();
                cnt++;

                int next = arr[cur];
                indegree[next]--;

                if(indegree[next] == 0){
                    q.addLast(next);
                }

            }

            sb.append(cnt).append("\n");
        }


        System.out.println(sb);
    }

}
