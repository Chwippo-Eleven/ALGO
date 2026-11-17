class Solution {
    
    private static final int INF = Integer.MAX_VALUE;
    
    private static final int[] dr = {0, 1, 0, -1};
    private static final int[] dc = {1, 0, -1, 0};
    
    public int[] solution(int rows, int columns, int[][] queries) {
        
        // 1-based Index
        int[][] mat = new int[rows + 1][columns + 1];
        
        for (int r = 1; r <= rows; r++) {
            for (int c = 1; c <= columns; c++) {
                mat[r][c] = (r - 1) * columns + c;
            }
        }
        
        int q = queries.length;
        int[] result = new int[q];
        
        for (int i = 0; i < q; i++) {
            result[i] = rotateMatrix(mat, queries[i]);
        }
        
        return result;
    }
    
    private int rotateMatrix(int[][] mat, int[] query) {
        
        int minValue = INF;
        
        int x1 = query[0], y1 = query[1];
        int x2 = query[2], y2 = query[3];
        
        int[] cornerRow = {x1, x2, x2, x1};
        int[] cornerCol = {y2, y2, y1, y1};
        
        int r = x1;
        int c = y1;
        
        int dir = 0;
        int val = mat[r][c];
        
        while (dir < 4) {
            minValue = Math.min(minValue, val);
            
            int nr = r + dr[dir];
            int nc = c + dc[dir];
            
            // 값 변경
            int tmp = mat[nr][nc];
            mat[nr][nc] = val;
            val = tmp;
            
            // 위치 이동 & 다음 방향 결정
            r = nr;
            c = nc;
            
            if (r == cornerRow[dir] && c == cornerCol[dir]) {
                dir++;
            }
        }
        
        return minValue;
    }
}
