
import java.util.*;
import java.io.*;

public class Main {
    static int n;
    static int[][] cost, dp;
    static final int INF = 1000000000;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        cost = new int[n][n];
        dp = new int[1 << n][n];

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                cost[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int res = dfs(0, 0);
        System.out.println(res);
    }

    public static int dfs(int visit, int now) {

        // 현재 도시 방문 처리
        visit |= (1 << now);

        // 10000 - 1 = 1111로 4개 도시 방문 배열 체크
        if (visit == (1 << n) - 1) {
            if (cost[now][0] > 0) {
                return cost[now][0];
            }
            return INF;
        }

        if (dp[visit][now] > 0) {
            return dp[visit][now];
        }

        dp[visit][now] = INF;

        for (int i = 0; i < n; i++) {
            if (i == now || cost[now][i] == 0) continue; // 이동 불가능
            if ((visit & (1 << i)) != 0) continue; // 이미 방문

            // 다음 도시 거쳐서 가는 최소비용
            int temp = dfs(visit, i) + cost[now][i];

            dp[visit][now] = Math.min(dp[visit][now], temp);
        }

        return dp[visit][now];
    }
}
