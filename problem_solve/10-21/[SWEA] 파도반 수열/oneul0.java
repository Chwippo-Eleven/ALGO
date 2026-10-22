import java.util.*;
import java.io.*;

class Solution
{
	public static void main(String args[]) throws Exception
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		int T = Integer.parseInt(br.readLine());
		long[] dp = new long[101];
        dp[1] = dp[2] = dp[3] = 1;
        for(int i = 4; i<=100; i++){
            dp[i] = dp[i-2] + dp[i-3];
        }
		for(int test_case = 1; test_case <= T; test_case++)
		{
			int N = Integer.parseInt(br.readLine());
            bw.write("#"+test_case+" "+dp[N]+"\n");
		}
        bw.flush();
        br.close();
        bw.close();
	}
}