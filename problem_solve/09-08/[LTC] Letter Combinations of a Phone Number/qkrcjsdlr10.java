class Solution {
    static char[][] arr = new char[10][4];
    static List<String> res;
    public static void dfs(int depth, int n, ArrayDeque<Character> temp, String digit){
        if(depth == n){
            StringBuilder sb = new StringBuilder();

            for (char c : temp) {
                sb.append(c);
            }

            res.add(sb.toString());
            return;
        }
        int cur = digit.charAt(depth) - '0';
        int length = 3;
        if(cur == 7 || cur == 9){
            length = 4;
        }

        for (int i = 0; i < length; i++) {
            temp.addLast(arr[cur][i]);
            dfs(depth + 1, n, temp, digit);
            temp.pollLast();
        }

    }
    public List<String> letterCombinations(String digits) {

        int n = digits.length();
        res = new ArrayList<>();
        char start = 'a';

        if (digits.isEmpty()) {
            return res;
        }

        for (int i = 2; i < 10; i++) {
            int size = (i == 7 || i == 9) ? 4 : 3;

            for (int j = 0; j < size; j++) {
                arr[i][j] = start++;
            }
        }
        dfs(0, n, new ArrayDeque<>(), digits);

        return res;
    }
}