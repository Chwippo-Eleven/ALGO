import java.util.ArrayDeque;
import java.util.Queue;

class Solution {
    
    private static int H, W;
    private static char[][] maze;
    
    private static int[] dr = {-1, 1, 0, 0};
    private static int[] dc = {0, 0, -1, 1};
    
    public int solution(String[] maps) {
        
        H = maps.length;
        W = maps[0].length();
        
        maze = new char[H][W];
        
        // 이차원 char 배열로 변환
        for (int r = 0; r < H; r++) {
            for (int c = 0; c < W; c++) {
                maze[r][c] = maps[r].charAt(c);
            }
        }
        
        // 시작 지점에서 레버까지 먼저 이동
        int startToLeverTime = bfs('S', 'L');
        
        if (startToLeverTime == -1) {
            return -1;  // 실패 시 -1 반환
        }
        
        // 레버 작동 후 끝 지점까지 이동
        int leverToEndTime = bfs('L', 'E');
        
        if (leverToEndTime == -1) {
            return -1;  // 실패 시 -1 반환
        }
        
        return startToLeverTime + leverToEndTime;
    }
    
    
    // 시작 심볼에서 끝 심볼까지 이동하는 데 걸리는 시간 반환
    private int bfs(char startSymbol, char endSymbol) {
        
        int startRow = 0;
        int startCol = 0;
        
        // 시작 심볼의 위치 찾기
        FindStart:
        for (int r = 0; r < H; r++) {
            for (int c = 0; c < W; c++) {
                if (maze[r][c] == startSymbol) {
                    startRow = r;
                    startCol = c;
                    break FindStart;
                }
            }
        }
        
        boolean[][] visited = new boolean[H][W];
        
        // 큐의 원소: (행, 열, 시간)을 저장한 정수 배열
        Queue<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[] {startRow, startCol, 0});
        visited[startRow][startCol] = true;
        
        while (!queue.isEmpty()) {
            int[] state = queue.poll();
            int row  = state[0];
            int col  = state[1];
            int time = state[2];
            
            for (int dir = 0; dir < 4; dir++) {
                int r = row + dr[dir];
                int c = col + dc[dir];
                
                if (!isIn(r, c) || visited[r][c] || maze[r][c] == 'X') {
                    continue;
                }
                
                if (maze[r][c] == endSymbol) {
                    return time + 1;
                }
                
                visited[r][c] = true;
                queue.offer(new int[] {r, c, time + 1});
            }
        }
        
        // 탐색 실패 시 -1 반환
        return -1;
    }
    
    private boolean isIn(int row, int col) {
        return 0 <= row && row < H && 0 <= col && col < W;
    }
}
