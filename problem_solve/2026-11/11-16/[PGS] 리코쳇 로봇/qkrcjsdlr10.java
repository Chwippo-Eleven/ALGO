import java.util.*;


class Solution {

    int n = 0;
    int m = 0;
    int[] dy = {-1, 0, 1, 0};
    int[] dx = {0, 1, 0, -1};
    int[][] grid;

    public boolean isIn(int y, int x){
        return y < n && y >= 0 && x < m && x >= 0;
    }

    public int[] searchEnd(int y, int x, int d){
        while(true){
            int ny = y + dy[d];
            int nx = x + dx[d];
            if(!isIn(ny, nx)) break;
            if(grid[ny][nx] == -1){
                break;
            }
            x = nx;
            y = ny;
        }
        // System.out.println(y + " " + x);
        return new int[]{y, x};
    }

    public class Pair{
        int first, second;

        Pair(int first, int second){
            this.first = first;
            this.second = second;
        }
    }

    public int solution(String[] board) {
        int answer = 0;
        int tx = 0;
        int ty = 0;

        n = board.length;
        m = board[0].length();
        grid = new int[n][m];
        boolean[][] vis = new boolean[n][m];

        ArrayDeque<Pair> q = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            for(int j = 0; j < m; j++){
                if(board[i].charAt(j) == '.'){
                    grid[i][j] = 0;
                }else if(board[i].charAt(j) == 'D'){
                    grid[i][j] = -1;
                }else if(board[i].charAt(j) == 'G'){
                    tx = j;
                    ty = i;
                    grid[i][j] = -2;
                }else if(board[i].charAt(j) == 'R'){
                    grid[i][j] = 1;
                    q.addLast(new Pair(i, j));
                    vis[i][j] = true;
                }
            }
        }
        boolean find = false;
        while(!q.isEmpty()){
            if(find) break;
            Pair cur = q.pollFirst();
            int y = cur.first;
            int x = cur.second;
            // System.out.println(y + " " + x);
            for(int d = 0; d < 4; d++){
                int[] next = searchEnd(y, x, d);
                int ny = next[0];
                int nx = next[1];
                // System.out.println(ny + " " + nx);
                if(vis[ny][nx]) continue;
                if(grid[ny][nx] == -2){
                    grid[ny][nx] = grid[y][x] + 1;
                    find = true;
                    break;
                }
                // System.out.println(ny + " " + nx);
                vis[ny][nx] = true;
                grid[ny][nx] = grid[y][x] + 1;
                q.addLast(new Pair(ny, nx));
            }

        }

//         for(int[] row : grid){
//             for(int x : row){
//                 System.out.print(x + " ");
//             }

//          System.out.println();
//         }


        return (grid[ty][tx] != -2)? grid[ty][tx]-1 : -1;
    }
}