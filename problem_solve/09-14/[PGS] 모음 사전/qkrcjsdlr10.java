import java.util.*;

class Solution {

    static char[] v = {'A', 'E', 'I', 'O', 'U'};
    static List<List<Character>> list;

    static void dfs(int depth, List<Character> cur) {
        if (depth == 6) return;

        list.add(new ArrayList<>(cur));

        for (int i = 0; i < 5; i++) {
            cur.add(v[i]);
            dfs(depth + 1, cur);
            cur.remove(cur.size() - 1);
        }
    }

    public int solution(String word) {
        list = new ArrayList<>();

        dfs(0, new ArrayList<>());

        List<Character> target = new ArrayList<>();

        for (char c : word.toCharArray()) {
            target.add(c);
        }

        return list.indexOf(target);
    }
}