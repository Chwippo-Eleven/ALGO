import java.io.*;
import java.util.*;

public class Main {
    static int t, n;
    static int[] in, arr;
    static ArrayDeque<Integer> q;
    public static void main(String[] args) throws IOException{
        // TODO Auto-generated method stub

        // union find
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        t = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= t; tc++) {
            n = Integer.parseInt(br.readLine());
            st = new StringTokenizer(br.readLine());

            in = new int[n + 1];
            arr = new int[n + 1];
            q = new ArrayDeque<>();

            for (int i = 1; i <= n; i++) {
                int target = Integer.parseInt(st.nextToken());
                in[target]++;
                arr[i] = target;
            }

            // 위상정렬 사용
            for (int i = 1; i <= n; i++) {
                if (in[i] == 0) {
                    q.offerLast(i);
                }
            }

            int cnt = 0; // 사이클이 없는 노드 찾기

            while (!q.isEmpty()) {
                int cur = q.pollFirst();
                cnt++;

                int next = arr[cur];
                in[next]--;

                if (in[next] == 0) {
                    q.offerLast(next);
                }
            }

            System.out.println(cnt);
        }
    }
}
