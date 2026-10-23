import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class swea3376_파도반_수열 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int tc = Integer.parseInt(br.readLine());

        for (int t = 1; t <= tc; t++) {
            int n = Integer.parseInt(br.readLine());

            long[] dp = new long[105];

            dp[1] = 1;
            dp[2] = 1;
            dp[3] = 1;
            dp[4] = 2;
            dp[5] = 2;

            for (int i = 6; i < n + 1; i++) {
                dp[i] = dp[i - 1] + dp[i - 5];
            }

            sb.append("#").append(t).append(" ").append(dp[n]).append("\n");
        }
        System.out.println(sb);


    }

}
