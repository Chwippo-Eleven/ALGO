class Solution {
    public boolean solution(int[][] key, int[][] lock) {
        
        int n = lock.length;
        int m = key.length;
        
        for (int ro = 1 - m; ro < n + m; ro++) {
            for (int co = 1 - m; co < n + m; co++) {
                int[][] key1 = key.clone();
                if (canUnlock(n, m, ro, co, key1, lock)) return true;
                int[][] key2 = rotate90CCW(key1);
                if (canUnlock(n, m, ro, co, key2, lock)) return true;
                int[][] key3 = rotate90CCW(key2);
                if (canUnlock(n, m, ro, co, key3, lock)) return true;
                int[][] key4 = rotate90CCW(key3);
                if (canUnlock(n, m, ro, co, key4, lock)) return true;
            }
        }
        
        return false;
    }
    
    private boolean canUnlock(int n, int m, int ro, int co, int[][] key, int[][] lock) {

        // 2차원 배열 Deep Copy 필요!
        int[][] copiedLock = new int[n][];
        
        for (int i = 0; i < n; i++) {
            copiedLock[i] = lock[i].clone();
        }
        
        for (int r = Math.max(ro, 0); r < Math.min(m + ro, n); r++) {
            for (int c = Math.max(co, 0); c < Math.min(m + co, n); c++) {
                copiedLock[r][c] += key[r - ro][c - co];
            }
        }
        
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                // 정확히 맞물린 것만 가능하다!
                if (copiedLock[r][c] != 1) return false;
            }
        }
        
        return true;
    }
    
    private int[][] rotate90CCW(int[][] arr) {
        int row = arr.length;
        int col = arr[0].length;
        
        int[][] rotateArr = new int[col][row];
        
        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                rotateArr[col - 1 - c][r] = arr[r][c];
            }
        }
        
        return rotateArr;
    }
}
