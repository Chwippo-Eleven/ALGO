    import java.util.*;

    class Solution {

        StringBuilder sb = new StringBuilder();
        List<String> ans = new ArrayList<>();
        Map<Integer, String> m = new HashMap<>();

        String digits;

        public List<String> letterCombinations(String digits) {

            if (digits.length() == 0) {
                return ans;
            }

            this.digits = digits;

            m.put(2, "abc");
            m.put(3, "def");
            m.put(4, "ghi");
            m.put(5, "jkl");
            m.put(6, "mno");
            m.put(7, "pqrs");
            m.put(8, "tuv");
            m.put(9, "wxyz");

            TTT(0);

            return ans;
        }

        public void TTT(int dep) {

            if (dep == digits.length()) {
                ans.add(sb.toString());
                return;
            }

            // 현재
            int num = digits.charAt(dep) - '0';

            String str = m.get(num);

            for (int i = 0; i < str.length(); i++) {

                sb.append(str.charAt(i));

                TTT(dep + 1);

                sb.deleteCharAt(sb.length() - 1);
            }
        }
    }
