class Solution {
    public int solution(int[][] board) {
        final int H = board.length;
        final int W = board[0].length;
        
        int[][] prefix = new int[H + 1][W + 1];
        
        // 2차원 누적합 배열 생성
        for (int r = 1; r <= H; r++) {
            for (int c = 1; c <= W; c++) {
                prefix[r][c] = board[r - 1][c - 1] 
                    + prefix[r - 1][c] + prefix[r][c - 1] - prefix[r - 1][c - 1];
            }
        }
        
        int maxS = 0;   // 가장 큰 정사각형의 한 변의 길이
        
        // 가능성 있는 시작지점만 탐색한다!
        for (int r = 0; r + maxS < H; r++) {
            for (int c = 0; c + maxS < W; c++) {
                int sizeLimit = Math.min(H - r, W - c); // 길이 제한 구하기
                
                // 기존보다 큰 경우만 테스트한다
                for (int s = maxS + 1; s <= sizeLimit; s++) {
                    int row = r + s;
                    int col = c + s;
                    
                    int area = prefix[row][col] 
                        - prefix[r][col] - prefix[row][c] + prefix[r][c];
                    
                    if (area != s * s) { break; }
                    
                    maxS = s;   // 영역 안이 모두 1인 경우에만 갱신
                }
            }
        }

        return maxS * maxS;
    }
}
