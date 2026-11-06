import java.util.*;

// 최소 비용으로 모든 섬이 통행 가능하도록 최소비용

class Solution {
    public int solution(int n, int[][] costs) {
        int answer = 0;
        
        int[][] arr = new int[n][n]; // 비용 및 연결
        
        boolean[] vi = new boolean[n]; // 방문처리
        
        for (int[] x : costs){
            // 양방향 처리
            arr[x[0]][x[1]] = x[2];
            arr[x[1]][x[0]] = x[2];
        }
        
        vi[0] = true; // 0번 섬부터 시작
        int connected = 1; // 연결된 거 체크 (0시작이라서 1초기화)
        
        while (connected < n){
            int min = Integer.MAX_VALUE;
            int minEnd = -1, minStart = -1;

            for (int end=0; end<n; end++){
                if (vi[end]){ // 이미 연결 패스
                    continue;
                }
                for (int start=0; start<n; start++){
                    if (arr[start][end]>0 && vi[start]){ // end로 올 수 있는 start 구하기
                        if (min > arr[start][end]){ // 가장 작은 값으로 방문 가능한지
                            min = arr[start][end]; //값
                            minEnd = end; // 도착점
                            minStart = start; // 시작점
                        }
                    }
                }
            }

            vi[minEnd] = true; // 도착처리
            answer += min;
            connected++;
        }

        
        
        return answer;
    }
}
