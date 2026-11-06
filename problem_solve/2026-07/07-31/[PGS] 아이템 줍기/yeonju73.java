import java.util.*;

class Solution {
    
    int[][] direc = {
        {0, 1},
        {0, -1},
        {1, 0},
        {-1, 0}
    };
    
    public int solution(int[][] rectangle, int characterX, int characterY, int itemX, int itemY) {        
        // 최대 좌표 50
        int[][] board = new int[101][101];
        
        // 직사각형 그리기
        for(int[] r : rectangle){
            int x1 = r[0] * 2;
            int y1 = r[1] * 2;
            int x2 = r[2] * 2;
            int y2 = r[3] * 2;
            
            for(int i = x1; i <= x2; i++) {
                for(int j = y1; j <=y2; j++) {
                    if (i == x1 || i == x2 || j == y1 || j == y2){
                        // 이미 다른 사각형의 내부인 공간은 테두리로 표시하지 않게!!!
                        if (board[i][j] != -1)
                            board[i][j] = 1;
                    } else {
                        board[i][j] = -1;
                    }
                }
            }
            
        }
        
        // bfs
        Queue<List<Integer>> queue = new ArrayDeque<>();
        boolean[][] visited = new boolean[101][101];
        int value = 0;
        
        queue.offer(new ArrayList<>(List.of(characterX * 2, characterY * 2)));
        visited[characterX*2][characterY*2] = true;
        
        while (!queue.isEmpty()) {
            List<Integer> node = queue.poll();
            
            // 도착지 체크
            if (node.get(0) == itemX*2 && node.get(1) == itemY*2){
                break;
            }
            
            for(int i = 0; i < 4; i++) {
                int nextX = node.get(0) + direc[i][0];
                int nextY = node.get(1) + direc[i][1];
                
                if (nextX >= 0 && nextX < 101 &&  nextY >= 0 && nextY < 101 && !visited[nextX][nextY] && board[nextX][nextY] == 1){
                    queue.offer(new ArrayList<>(List.of(nextX, nextY)));
                    visited[nextX][nextY] = true;
                    board[nextX][nextY] = board[node.get(0)][node.get(1)] + 1;
                    
                }
            }
            
        }
        return (board[itemX*2][itemY*2]-1) / 2;
    }
}
