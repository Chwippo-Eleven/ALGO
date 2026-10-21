import java.io.*;
import java.util.*;

public class Solution {
    static int t, n;
    static long[] dp;
    public static void main(String[] args) throws IOException{
        // TODO Auto-generated method stub
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        t = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= t; tc++) {
            n = Integer.parseInt(br.readLine());

            long res = 0;
            if (n <= 3) {
                res = 1;
            } else {
                dp = new long[n + 1];
                dp[1] = dp[2] = dp[3] = 1;
                for (int i = 4; i <= n; i++) {
                    dp[i] = dp[i - 2] + dp[i - 3];

                }
                res = dp[n];
            }

            System.out.println("#" + tc + " " + res);
        }
    }

}