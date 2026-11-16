import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

class Solution {
    
    private static final int INF = Integer.MAX_VALUE;
    
    private static int[] dr = {-1, 1, 0, 0};
    private static int[] dc = {0, 0, -1, 1};
    
    private int rowNum, colNum;
    
    public int solution(String[] board) {
        
        rowNum = board.length;
        colNum = board[0].length();
        
        int robotRow = 0, robotCol = 0;
        int goalRow = 0, goalCol = 0;
        
        for (int r = 0; r < rowNum; r++) {
            String rowStr = board[r];
            for (int c = 0; c < colNum; c++) {
                if (rowStr.charAt(c) == 'R') {
                    robotRow = r;
                    robotCol = c;
                } else if (rowStr.charAt(c) == 'G') {
                    goalRow = r;
                    goalCol = c;
                }
            }
        }
        
        // 각 칸마다 최소 이동 횟수를 기록해 visited처럼 활용
        int[][] minMove = new int[rowNum][colNum];
        
        for (int r = 0; r < rowNum; r++) {
            Arrays.fill(minMove[r], INF);
        }
        
        // (현재 행, 현재 열, 이동 횟수 기록)
        Queue<int[]> queue = new ArrayDeque<>();
        
        // 시작 지점 설정
        minMove[robotRow][robotCol] = 0;
        queue.offer(new int[] {robotRow, robotCol, 0});
        
        BFS:
        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            
            int currRow  = current[0];
            int currCol  = current[1];
            int currMove = current[2];
            
            if (currMove > minMove[currRow][currCol]) continue;
            
            for (int dir = 0; dir < 4; dir++) {
                int nextRow = currRow;
                int nextCol = currCol;
                
                while (true) {
                    int nr = nextRow + dr[dir];
                    int nc = nextCol + dc[dir];

                    if (!isIn(nr, nc) || board[nr].charAt(nc) == 'D') {
                        break;
                    }

                    nextRow = nr;
                    nextCol = nc;
                }
                
                // 이동 횟수 갱신 
                int nextMove = currMove + 1;
                
                if (nextMove < minMove[nextRow][nextCol]) {
                    minMove[nextRow][nextCol] = nextMove;
                    queue.offer(new int[] {nextRow, nextCol, nextMove});
                }
                
                if (board[nextRow].charAt(nextCol) == 'G') {
                    break BFS;
                }
            }
        }
        
        return minMove[goalRow][goalCol] == INF ? -1 : minMove[goalRow][goalCol];
    }
    
    private boolean isIn(int r, int c) {
        return 0 <= r && r < rowNum && 0 <= c && c < colNum;
    }
}
