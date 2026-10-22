import java.util.*;

class Solution {
    static boolean[] check_user;
    static String[] a, b;
    static int res;
    static Set<Integer> set;
    public int solution(String[] user_id, String[] banned_id) {

        a = user_id;
        b = banned_id;

        check_user = new boolean[user_id.length];
        set = new HashSet<>();

        dfs(0);

        return set.size();
    }
    public static void dfs(int bIdx){

        if (bIdx == b.length){

            // 내가 몇 번째 아이디를 뽑았는지를 비트마스크로 처리하기
            int mask = 0;

            for (int i = 0; i < check_user.length; i++){
                if (check_user[i]) {
                    mask = mask | (1 << i);
                }
            }

            set.add(mask);
            return;
        }
        for (int i = 0; i < a.length; i++){
            if (check_user[i]) continue; // 이미 본 애면 건너뛰고
            if (!isPossible(a[i], b[bIdx])) continue; // 쌍을 못 만드는 조합이면 건너뜀

            check_user[i] = true;
            dfs(bIdx + 1);
            check_user[i] = false;
        }
    }
    public static boolean isPossible(String s1, String s2){

        if (s1.length() != s2.length()) {
            return false;
        }

        for (int i = 0; i < s1.length(); i++){
            if (s1.charAt(i) != s2.charAt(i)) {
                if (s2.charAt(i) == '*'){
                    continue;
                } else {
                    return false;
                }
            }
        }
        return true;
    }
}