import java.util.*;

class Solution {

    Map<List<Character>, Integer> mp = new HashMap<>();

    void dfs(int idx, List<Character> arr, char[] order, int[] course) {

        for (int len : course) {
            if (arr.size() == len) {
                List<Character> key = new ArrayList<>(arr);
                key.sort(null);

                mp.put(key, mp.getOrDefault(key, 0) + 1);
            }
        }

        for (int i = idx; i < order.length; i++) {
            arr.add(order[i]);
            dfs(i + 1, arr, order, course);
            arr.remove(arr.size() - 1);
        }
    }

    public String[] solution(String[] orders, int[] course) {
        List<String> res = new ArrayList<>();
        int[] mx = new int[11];

        for (String order : orders) {
            dfs(0, new ArrayList<>(), order.toCharArray(), course);
        }

        for (var e : mp.entrySet()) {
            int len = e.getKey().size();
            int cnt = e.getValue();

            if (cnt >= 2) {
                mx[len] = Math.max(mx[len], cnt);
            }
        }

        for (var e : mp.entrySet()) {
            List<Character> key = e.getKey();
            int cnt = e.getValue();

            if (cnt < 2 || cnt != mx[key.size()]) continue;

            StringBuilder sb = new StringBuilder();

            for (char c : key) {
                sb.append(c);
            }

            res.add(sb.toString());
        }

        Collections.sort(res);

        return res.toArray(new String[0]);
    }
}