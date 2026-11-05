import java.util.*;

class Solution {
    static List<String> res;
    static String[] s;
    static int[] num;
    public List<String> letterCombinations(String digits) {
        s = new String[] {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};

        num = new int[digits.length()];

        for (int i = 0; i < num.length; i++){
            num[i] = Integer.parseInt(String.valueOf(digits.charAt(i)));
        }

        res = new ArrayList<>();
        dfs(0, 0, "");
        return res;
    }
    public static void dfs(int idx, int depth, String cur){

        // idx는 현재 몇 번 번호를 보고있는지
        // 그럼 그 번호에 해당하는 번호 수 만큼 넣어야겠지.

        if (depth == num.length){
            res.add(cur);
            return;
        }

        int val = num[idx]; // 지금 보려는 번호
        for (int i = 0; i < s[val].length(); i++){ // 그 번호에 해당되는 문자들 보면서 넣어주기
            String tmp = cur;
            cur += s[val].charAt(i);
            dfs(idx + 1, depth + 1, cur);
            cur = tmp;
        }
    }
}