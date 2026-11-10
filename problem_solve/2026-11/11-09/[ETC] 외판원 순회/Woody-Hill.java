import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int n = Integer.parseInt(br.readLine());
        int[][] cost = new int[n][n];

        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                cost[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        
        // BFS + Bitmask DP
        int[][] dp = new int[n][1 << n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(dp[i], Integer.MAX_VALUE);
        }

        Queue<int[]> queue = new ArrayDeque<>();

        dp[0][0] = 0;
        queue.offer(new int[] {0, 0, 0});

        int allMask = (1 << n) - 1;
        int endMask = allMask - 1;

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int currLoc = current[0];
            int curMask = current[1];
            int curCost = current[2];

            if (curCost > dp[currLoc][curMask]) continue;

            if (curMask == endMask) {
                if (cost[currLoc][0] == 0) continue;

                dp[0][allMask] = Math.min(dp[0][allMask], curCost + cost[currLoc][0]);
                continue;
            }

            for (int i = 1; i < n; i++) {
                if (cost[currLoc][i] == 0 || isSet(curMask, i)) continue;

                int nextMask = setBit(curMask, i);
                int nextCost = curCost + cost[currLoc][i];

                if (nextCost < dp[i][nextMask]) {
                    dp[i][nextMask] = nextCost;
                    queue.offer(new int[] {i, nextMask, nextCost});
                }
            }
        }

        bw.write(dp[0][allMask] + "\n");

        bw.flush();
        bw.close();
        br.close();
    }

    private static boolean isSet(int mask, int index) {
        return (mask & (1 << index)) > 0;
    }

    private static int setBit(int mask, int index) {
        return mask | (1 << index);
    }
}
