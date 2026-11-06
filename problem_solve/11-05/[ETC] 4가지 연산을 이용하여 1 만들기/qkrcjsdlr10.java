import java.util.*;

public class Main {

    static int[] dp;
    static int res, mx;

    public static void dfs(int n, int cnt){
        if(n == 1){
            dp[1] =  Math.min(dp[1], cnt);
            return;
        }

        if(n >= mx) return;
        //dp 메모제이션
        if(dp[n] <= cnt){
            return;
        }

        dp[n] = cnt;

        if(n % 2 == 0) dfs(n / 2, cnt + 1);
        if(n % 3 == 0) dfs(n / 3, cnt + 1);
        dfs(n - 1, cnt + 1);
        dfs(n + 1, cnt + 1);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        // Please write your code here.
        mx = n + 3;
        dp = new int[n + 10];
        Arrays.fill(dp, Integer.MAX_VALUE);

        dfs(n, 0);

        System.out.println(dp[1]);
    }
}