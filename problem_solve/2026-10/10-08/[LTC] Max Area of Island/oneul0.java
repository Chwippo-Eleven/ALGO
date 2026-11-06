class Solution {
    public int maxAreaOfIsland(int[][] grid) {
        int maxArea = 0;
        int m = grid.length;
        int n = grid[0].length;

        boolean[][] vis = new boolean[m][n];

        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                if (grid[r][c] == 1 && !vis[r][c]) {
                    maxArea = Math.max(maxArea, dfs(grid, r, c, vis));
                }
            }
        }

        return maxArea;
    }

    private int dfs(int[][] grid, int r, int c, boolean[][] vis) {
        if (r < 0 || r >= grid.length || c < 0 || c >= grid[0].length || grid[r][c] == 0 || vis[r][c]) {
            return 0;
        }

        int dist = 0;

        vis[r][c] = true;
        int[] dr = new int[] {-1,0,1,0};
        int[] dc = new int[] {0,1,0,-1};
        for(int i  = 0; i < 4; i++){
            int nr = r + dr[i];
            int nc = c + dc[i];

            dist += dfs(grid, nr, nc, vis);
        }

        return 1 + dist;
    }
}
