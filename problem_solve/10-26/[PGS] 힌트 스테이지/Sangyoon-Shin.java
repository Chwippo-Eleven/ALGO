
import java.util.*;

class Solution {
    static int res;
    static int[][] c, h;
    static int[] buy;

    public int solution(int[][] cost, int[][] hint) {

        c = cost;
        h = hint;

        res = Integer.MAX_VALUE;
        buy = new int[cost.length];

        dfs(0, 0);

        return res;
    }

    public static void dfs(int idx, int sum) {

        if (sum >= res) return;

        if (idx == c.length) {
            res = Math.min(res, sum);
            return;
        }

        // 현재 스테이지에서 사용할 수 있는 힌트 개수
        // 사용할 수 있는 양보다 힌트를 많이 가지고 있으면 다 못씀
        int cnt = buy[idx];
        if (cnt >= c[idx].length) {
            cnt = c[idx].length - 1;
        }

        int cost = sum + c[idx][cnt];

        // 1. 구매 x
        dfs(idx + 1, cost);

        // 2. 구매 o
        if (idx < h.length) {

            for (int i = 1; i < h[idx].length; i++) {
                int stage = h[idx][i] - 1;
                buy[stage]++;
            }

            // 티켓 구매 비용 추가하고 백트래킹
            dfs(idx + 1, cost + h[idx][0]);

            for (int i = 1; i < h[idx].length; i++) {
                int stage = h[idx][i] - 1;
                buy[stage]--;
            }
        }
    }
}
