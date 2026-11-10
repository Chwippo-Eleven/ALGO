import java.io.*;
import java.util.*;
public class Main {
    static final int INF = Integer.MAX_VALUE;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int n = Integer.parseInt(br.readLine());

        int[][] cost = new int[n][n]; //i 에서 j를 가는데 필요한 비용

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                cost[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int[][] dp = new int[n][1 << n];
        for(int i = 0; i<n; i++){
            Arrays.fill(dp[i], INF);
        }
        dp[0][1<<0] = 0;

        for(int mask = 1; mask< (1<<n); mask++){
            for (int s = 0; s < n; s++) {
                if(dp[s][mask] == INF) continue;
                for (int next = 0; next < n; next++) {
                    if((mask & (1<<next)) != 0) continue; //이미 방문 했으면 continue
                    if(cost[s][next] == 0) continue;
                    int nextMask = (mask | (1<<next));
                    
                    dp[next][nextMask] = Math.min(dp[next][nextMask], dp[s][mask] + cost[s][next]);
                }
            }
        }
        
        int answer = INF;
        int fullMask = (1<<n)-1;
        //다시 돌아왔을 때 최소비용이 되는 것 찾기
        for(int i = 1; i<n; i++){
            if(dp[i][fullMask] == INF) continue;
            if(cost[i][0] == 0) continue;
            answer = Math.min(answer, dp[i][fullMask]+cost[i][0]);
        }
        System.out.print(answer);
    }
}
