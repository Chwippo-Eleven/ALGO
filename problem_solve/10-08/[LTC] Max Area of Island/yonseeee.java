class Solution {
    static int m, n;
    static int[] dr = {1, -1, 0, 0};
    static int[] dc = {0, 0, 1, -1};
    static int maxArea;
    public int maxAreaOfIsland(int[][] grid) {
        
        m = grid.length;
        n = grid[0].length;
        maxArea = 0;


        for(int i = 0; i < m; i++){
            for(int j = 0; j < n; j++){
                if(grid[i][j] == 1){
                    int cnt = dfs(i, j, grid);
                    System.out.println(cnt);
                    maxArea = Math.max(maxArea, cnt);
                }
            }
        }

        return maxArea;
    }

    private int dfs(int cr, int cc, int[][] grid){

        grid[cr][cc] = 0;

        int area = 1; 

        for(int d = 0; d < 4; d++){
            int nr = cr + dr[d];
            int nc = cc + dc[d];

            if(inRange(nr, nc) && grid[nr][nc] == 1){
                area += dfs(nr, nc, grid);
            }
        }

        return area;
        
    } 
    private boolean inRange(int r, int c){
        return r >=0 && r < m && c >= 0 && c < n;
    }
}
