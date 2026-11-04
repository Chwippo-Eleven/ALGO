import java.util.*;
class Solution {
    public String[] solution(String[][] tickets) {
        Arrays.sort(tickets, (a, b) -> {
            if (a[1].compareTo(b[1]) != 0) return a[1].compareTo(b[1]);
            return a[0].compareTo(b[0]);
        });

        List<String> ans = dfs(tickets, new ArrayList<>(), new boolean[tickets.length], "ICN");

        String[] answer = new String[ans.size()];
        for (int i = 0; i < ans.size(); i++) {
            answer[i] = ans.get(i);
        }
        return answer;
    }

    public List<String> dfs(String[][] tickets, List<String> ans, boolean[] chk, String cur) {
        ans.add(cur);

        if (ans.size() == tickets.length + 1) {
            return ans;
        }

        for (int i = 0; i < tickets.length; i++) {
            if (cur.equals(tickets[i][0]) && !chk[i]) {
                chk[i] = true;

                List<String> result = dfs(tickets, new ArrayList<>(ans), chk, tickets[i][1]);
                if (result != null) return result;

                chk[i] = false;
            }
        }

        return null;
    }

}