import java.io.*;
import java.util.*;

public class Main {
    static int max = 1000000;
    static boolean[] v = new boolean[max + 1];
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());

        int res = bfs(n);
        System.out.println(res);
    }
    public static int bfs(int start){

        ArrayDeque<int[]> q = new ArrayDeque<>();

        v[start] = true;
        q.offerLast(new int[] {start, 0});

        while (!q.isEmpty()){
            int[] cur = q.removeFirst();
            int n = cur[0];
            int cnt = cur[1];

            if (n == 1) return cnt;

            for (int i = 0; i < 4; i++) {
                int next = 0;

                if (i == 0) {
                    next = n - 1;
                } else if (i == 1) {
                    next = n + 1;
                } else if (i == 2) {
                    if (n % 2 != 0) continue;
                    next = n / 2;
                } else {
                    if (n % 3 != 0) continue;
                    next = n / 3;
                }

                if (next < 1 || next > max) continue;
                if (v[next]) continue;

                v[next] = true;
                q.offer(new int[]{next, cnt + 1});
            }
        }
        return 0;
    }
}