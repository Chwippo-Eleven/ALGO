class Solution {
    static int[] dr = {0, 0, 1, 1};
    static int[] dc = {0, 1, 0, 1};
    
    public int solution(int m, int n, String[] board) {
        char[][] charBoard = new char[m][n];
        
        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                charBoard[r][c] = board[r].charAt(c);
            }
        }
        
        int answer = 0;
        
        while (true) {
            boolean[][] erase = new boolean[m][n];
            
            // Check board to find 2x2 Boxes
            for (int r = 0; r < m - 1; r++) {
                for (int c = 0; c < n - 1; c++) {
                    char ch = charBoard[r][c];
                    if (ch == '0') continue;
                    
                    boolean isBox = true;
                    
                    // Check 2x2
                    for (int d = 1; d < 4; d++) {
                        if (charBoard[r + dr[d]][c + dc[d]] != ch) {
                            isBox = false;
                            break;
                        }
                    }
                    
                    if (isBox) {
                        for (int d = 0; d < 4; d++) {
                            erase[r + dr[d]][c + dc[d]] = true;
                        }
                    }
                }
            }
            
            // Erase 2x2 Boxes
            int eraseCount = 0;
            
            for (int r = 0; r < m; r++) {
                for (int c = 0; c < n; c++) {
                    if (erase[r][c]) {
                        charBoard[r][c] = '0';
                        eraseCount += 1;
                    }
                }
            }
            
            // Update answer or Break the loop 
            if (eraseCount > 0) {
                answer += eraseCount;
            } else {
                break;
            }
            
            // Make blocks fall down 
            for (int c = 0; c < n; c++) {
                for (int r = m - 1; r >= 0; r--) {
                    if (charBoard[r][c] == '0') continue;
                    
                    int dest = r;
                    while (dest < m - 1 && charBoard[dest + 1][c] == '0') {
                        dest += 1;
                    }
                    
                    if (dest != r) {
                        charBoard[dest][c] = charBoard[r][c];
                        charBoard[r][c] = '0';
                    }
                }
            }
        }
        
        return answer;
    }
}
