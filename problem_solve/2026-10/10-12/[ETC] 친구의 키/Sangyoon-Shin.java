import java.io.*;
import java.util.*;

public class Main {
    static ArrayList<Integer>[] g;
    static int[] in;
    static int n, m, a, b;
    public static void main(String[] args) throws IOException{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        g = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++){
            g[i] = new ArrayList<>();
        }

        in = new int[n + 1];

        for (int i = 0; i < m; i++){
            st = new StringTokenizer(br.readLine());
            a = Integer.parseInt(st.nextToken());
            b = Integer.parseInt(st.nextToken());

            g[a].add(b);
            in[b]++;
        }

        ArrayDeque<Integer> q = new ArrayDeque<>();
        StringBuilder sb = new StringBuilder();

        for (int i = 1; i <= n; i++){
            if (in[i] == 0){
                q.offerLast(i);
            }
        }

        while (!q.isEmpty()){
            int cur = q.pollFirst();
            sb.append(cur).append(" ");

            for (int next : g[cur]){
                in[next]--;
                if (in[next] == 0){
                    q.offerLast(next);
                }
            }
        }
        System.out.println(sb);

    }
}