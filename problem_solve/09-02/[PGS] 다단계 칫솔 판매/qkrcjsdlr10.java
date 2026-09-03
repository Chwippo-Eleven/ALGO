import java.util.*;

class Solution {

    static void dfs(Map<String, Integer> res, Map<String, String> parent, String cur, int val) {
        int give = val / 10;
        int keep = val - give;

        res.put(cur, res.get(cur) + keep);

        String p = parent.get(cur);

        if (p.equals("-") || give == 0) {
            return;
        }

        dfs(res, parent, p, give);
    }

    public int[] solution(String[] enroll, String[] referral, String[] seller, int[] amount) {

        int n = enroll.length;
        int[] ans = new int[n];

        Map<String, Integer> money = new HashMap<>();
        Map<String, String> parent = new HashMap<>();

        for (int i = 0; i < n; i++) {
            parent.put(enroll[i], referral[i]);
            money.put(enroll[i], 0);
        }

        for (int i = 0; i < seller.length; i++) {
            dfs(money, parent, seller[i], amount[i] * 100);
        }

        for (int i = 0; i < n; i++) {
            ans[i] = money.get(enroll[i]);
        }

        return ans;
    }
}