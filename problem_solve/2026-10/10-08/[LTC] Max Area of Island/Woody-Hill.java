class Solution {

    final int[] dr = {-1 ,0};
    final int[] dc = {0, -1};

    int m, n;
    int[][] parent;
    int[][] area;

    public int maxAreaOfIsland(int[][] grid) {
        m = grid.length;
        n = grid[0].length;

        parent = new int[m][n];
        area   = new int[m][n];

        int maxArea = 0;

        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                if (grid[r][c] == 0) continue;

                parent[r][c] = r * n + c;
                area[r][c] = 1;

                for (int dir = 0; dir < 2; dir++) {
                    int nr = r + dr[dir];
                    int nc = c + dc[dir];

                    if (!isIn(nr, nc) || grid[nr][nc] == 0) continue;

                    union(r, c, nr, nc);
                }

                int p = find(r, c);

                int pr = p / n;
                int pc = p % n;

                maxArea = Math.max(maxArea, area[pr][pc]);
            }
        }

        return maxArea;
    }

    private int find(int r, int c) {
        int p = parent[r][c];
        if (p == r * n + c) return p;

        int pr = p / n;
        int pc = p % n;

        return parent[r][c] = find(pr, pc);
    }

    private void union(int r1, int c1, int r2, int c2) {
        int p1 = find(r1, c1);
        int p2 = find(r2, c2);

        if (p1 == p2) return;

        if (p1 > p2) {
            int tmp = p1;
            p1 = p2;
            p2 = tmp;
        }

        int pr1 = p1 / n;
        int pc1 = p1 % n;

        int pr2 = p2 / n;
        int pc2 = p2 % n;

        parent[pr2][pc2] = p1;
        area[pr1][pc1] += area[pr2][pc2];
    }

    private boolean isIn(int r, int c) {
        return 0 <= r && r < m && 0 <= c && c < n;
    }
}
