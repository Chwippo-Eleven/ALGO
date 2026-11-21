class Solution {
    public int solution(int[][] board, int[][] skill) {
        int N = board.length;
        int M = board[0].length;
        
        // 2차원 차분 배열 (내구도의 변화량 기록)
        int[][] dif = new int[N + 1][M + 1];
        
        for (int[] action : skill) {
            int type = action[0];
            int r1 = action[1], c1 = action[2];
            int r2 = action[3], c2 = action[4];
            int degree = action[5];
            
            // 공격이면 부호 음수로 변환
            if (type == 1) {
                degree *= (-1);
            }
            
            // 차분 배열 갱신
            dif[r1][c1] += degree;
            dif[r1][c2 + 1] -= degree;
            dif[r2 + 1][c1] -= degree;
            dif[r2 + 1][c2 + 1] += degree;
        }
        
        // Row Update
        for (int i = 0; i <= N; i++) {
            for (int j = 0; j < M; j++) {
                dif[i][j + 1] += dif[i][j];
            }
        }
        
        // Column Update
        for (int j = 0; j <= M; j++) {
            for (int i = 0; i < N; i++) {
                dif[i + 1][j] += dif[i][j];
            }
        }
        
        int answer = 0;
        
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                // (원래 내구도 + 변화량)으로 최종 내구도 계산
                if (board[i][j] + dif[i][j] > 0) {
                    answer++;
                }
            }
        }
        
        return answer;
    }
}
