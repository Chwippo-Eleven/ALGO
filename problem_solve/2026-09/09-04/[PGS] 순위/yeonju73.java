import java.util.*;

class Solution {
    public int solution(int n, int[][] results) {
        int answer = 0;
        
        // graph[a][b] == true면 a가 b를 이김
        boolean[][] graph = new boolean[n][n];
        
        for(int[] result: results) {
            int a = result[0] - 1;
            int b = result[1] - 1;
            graph[a][b] = true;
        }
        
        // 플로이드-워셜
        for (int k = 0; k < n; k++){
            for (int i = 0; i < n; i++){
                for (int j = 0; j < n; j++){
                    // i > k , k > j => i > j 
                    if(graph[i][k] && graph[k][j]) {
                        graph[i][j] = true;
                    }
                }
            }
        }
        
        for (int i = 0; i < n; i++) {
            int count = 0;
            // i 가 관계를 가지고 있는 선수의 수
            for (int j = 0; j < n; j++) {
                if (graph[i][j] || graph[j][i]) count++;
            }
            // 자기자신 빼고 전부 승패 기록이 있다면
            if (count == n-1) answer++;
        }
        
        return answer;
    }
}
