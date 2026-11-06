import java.util.*;

class Solution {
    static int max;
    static int[] num;
    static int[] res;
    static boolean[] visited;
    static Set<Integer> set;
    static boolean[] prime;
    public int solution(String numbers) {

        num = new int[numbers.length()];

        for (int i = 0; i < num.length; i++){
            num[i] = Integer.parseInt(String.valueOf(numbers.charAt(i)));
        }

        max = 0;
        res = new int[num.length];
        visited = new boolean[num.length];
        set = new HashSet<>();

        dfs(0, "");

        int cnt = 0;

        prime = new boolean[max + 1];

        Arrays.fill(prime, true);
        prime[0] = prime[1] = false;

        for (int i = 2; i <= (int)Math.sqrt(max) + 1; i++){
            int j = 2;
            while (i * j <= max){
                prime[i * j] = false;
                j += 1;
            }
        }

        // 구해둔 조합에서 소수 판단
        for (int val : set){
            if (prime[val]){
                cnt++;
            }
        }
        return cnt;
    }
    public static void dfs(int cnt, String cur){

        if (cur.length() > 0){
            max = Math.max(max, Integer.parseInt(cur));
            set.add(Integer.parseInt(cur));
        }
        if (cnt == res.length){
            return;
        }

        for (int i = 0; i < num.length; i++){
            if (!visited[i]){
                cur += num[i];
                visited[i] = true;
                dfs(cnt + 1, cur);
                visited[i] = false;
                cur = cur.substring(0, cur.length() - 1);
            }
        }
    }
}