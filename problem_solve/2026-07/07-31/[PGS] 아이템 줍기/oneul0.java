import java.util.*;
class Solution {
    class Point {
        int x, y, dist;
        Point(int x, int y, int dist){
            this.x = x;
            this.y = y;
            this.dist = dist;
        }
    }
    final int SIZE = 101;
    int[][] board = new int[SIZE][SIZE];
    int[] dx = {-1,1,0,0}, dy = {0,0,-1,1};
    public int solution(int[][] rectangle, int characterX, int characterY, int itemX, int itemY) {
        for(int[] rect : rectangle){
            draw(rect); 
        }
        for(int[] rect : rectangle){
            removeInSide(rect); 
        }
        
        return bfs(characterX*2, characterY*2, itemX*2, itemY*2)/2;
    }
    
    public int bfs(int sx, int sy, int ex, int ey){
        Queue<Point> q = new ArrayDeque<>();
        q.offer(new Point(sx, sy, 0));
        boolean[][] visited = new boolean[SIZE][SIZE];
        visited[sx][sy] = true;
        
        while(!q.isEmpty()){
            Point cur = q.poll();
            
            for(int i = 0; i<4; i++){
                int nx = cur.x + dx[i];
                int ny = cur.y + dy[i];
                if(nx<0 || ny<0 || nx>=SIZE || ny>=SIZE) continue;
                if(board[nx][ny] == 0) continue;
                if(nx == ex && ny == ey) return cur.dist+1;
                if(!visited[nx][ny]) {
                    q.offer(new Point(nx, ny, cur.dist + 1));
                    visited[nx][ny] = true;
                }
            }
        }
        return -1;
    }
    
    public void draw(int[] rect){
        int sr = rect[0]*2;
        int sc = rect[1]*2;
        int er = rect[2]*2;
        int ec = rect[3]*2;
        for(int r = sr; r<=er; r++){
            for(int c = sc; c<=ec; c++){
                board[r][c] = 1;
            }
        }
    }
    public void removeInSide(int[] rect){
        int sr = rect[0]*2;
        int sc = rect[1]*2;
        int er = rect[2]*2;
        int ec = rect[3]*2;
        for(int r = sr+1; r<er; r++){
            for(int c = sc+1; c<ec; c++){
                board[r][c] = 0;
            }
        }
    }
    
}
//사각형 그리고 bfs나 다익스트라 돌리면 될 듯
