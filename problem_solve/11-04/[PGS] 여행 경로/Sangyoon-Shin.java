
import java.util.*;

class Solution {
    static String[][] t;
    static ArrayList<String> res, path;
    static boolean[] v;

    public String[] solution(String[][] tickets) {

        t = tickets;
        res = new ArrayList<>(); // 결과
        path = new ArrayList<>(); // 후보
        v = new boolean[t.length];

        // 도착지 기준 정렬
        // 정렬 안하고 조합 다 만들어두고 기존 경로랑 비교하는 방식 사용해도 시간초과 나진 않았음.
        Arrays.sort(t, (a, b) -> a[1].compareTo(b[1]));

        path.add("ICN");

        dfs("ICN", 0);

        String[] ans = new String[res.size()];
        for (int i = 0; i < ans.length; i++){
            ans[i] = res.get(i);
        }
        return ans;
    }

    public static void dfs(String cur, int cnt){

        if (!res.isEmpty()) return;

        if (cnt == t.length){
            // 첫 번째 조합 완성이면 사전순으로 가장 앞선 결과
            res = new ArrayList<>(path);
            return;
        }

        for (int i = 0; i < t.length; i++){

            if (v[i]) continue;
            if (!t[i][0].equals(cur)) continue;

            String next = t[i][1];
            v[i] = true;
            path.add(next);
            dfs(next, cnt + 1);
            v[i] = false;
            path.remove(path.size() - 1);

            if (!res.isEmpty()) return;
        }
    }
}
