class Solution {
    
    static final int N = 5;
    
    static final char PARTICIPANT = 'P';
    static final char EMPTY_TABLE = 'O';
    static final char PARTITION   = 'X';
    
    static final int[] dr = {-1, 0, 1, 0};
    static final int[] dc = {0, 1, 0, -1};
    
    public int[] solution(String[][] places) {
        
        int[] result = new int[5];  // 테스트 케이스 5개
        
        OuterLoop:  // 각 테스트 케이스에 대해
        for (int tc = 0; tc < 5; tc++) {
            
            char[][] room = new char[N][];
            
            for (int i = 0; i < N; i++) {
                room[i] = places[tc][i].toCharArray();
            }   // char형 2차원 배열로 변환
            
            for (int r = 0; r < N; r++) {
                for (int c = 0; c < N; c++) {
                    // 사람 있는 칸만 검사하기
                    if (room[r][c] != PARTICIPANT) { continue; }
                    
                    // 1차 이동 방향 : 상하좌우
                    for (int dir1 = 0; dir1 < 4; dir1++) {
                        int nr = r + dr[dir1];
                        int nc = c + dc[dir1];
                        
                        if (!isIn(nr, nc)) { continue; }
                        
                        if (room[nr][nc] == PARTICIPANT) {
                            result[tc] = 0;
                            continue OuterLoop;
                        } else if (room[nr][nc] == PARTITION) {
                            continue;
                        }
                        
                        // 2차 이동 방향 : 1차 이동 방향과 그 양옆 방향
                        for (int dir2 = dir1 - 1; dir2 <= dir1 + 1; dir2++) {
                            int nnr = nr + dr[Math.floorMod(dir2, 4)];
                            int nnc = nc + dc[Math.floorMod(dir2, 4)];
                            
                            if (!isIn(nnr, nnc)) { continue; }
                            
                            if (room[nnr][nnc] == PARTICIPANT) {
                                result[tc] = 0;
                                continue OuterLoop;
                            }
                        }
                    }
                }
            }
            
            result[tc] = 1;
        }
        
        return result;
    }
    
    private boolean isIn(int r, int c) {
        return 0 <= r && r < N && 0 <= c && c < N;
    }
}
