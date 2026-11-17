import java.util.*;

class Solution {
    static int x1, y1, x2, y2;
    static int[][] map;
    public int[] solution(int rows, int columns, int[][] queries) {

        map = new int[rows][columns];

        int val = 1;
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < columns; j++){
                map[i][j] = val++;
            }
        }

        int[] res = new int[queries.length];
        for (int i = 0; i < queries.length; i++){
            int[] arr = queries[i];
            x1 = arr[0] - 1;
            y1 = arr[1] - 1;
            x2 = arr[2] - 1;
            y2 = arr[3] - 1;

            res[i] = rotate(x1, y1, x2, y2);
        }

        return res;
    }
    public int rotate(int x1, int y1, int x2, int y2){

        int prev = map[x1][y1];
        int cur = Integer.MAX_VALUE;

        // 상단
        for (int c = y1 + 1; c <= y2; c++){
            int next = map[x1][c];
            map[x1][c] = prev;
            prev = next;

            cur = Math.min(cur, next);
        }

        // 오른쪽
        for (int r = x1 + 1; r <= x2; r++){
            int next = map[r][y2];
            map[r][y2] = prev;
            prev = next;

            cur = Math.min(cur, next);
        }

        // 하단
        for (int c = y2 - 1; c >= y1; c--){
            int next = map[x2][c];
            map[x2][c] = prev;
            prev = next;

            cur = Math.min(cur, next);
        }

        // 왼쪽
        for (int r = x2 - 1; r >= x1; r--){
            int next = map[r][y1];
            map[r][y1] = prev;
            prev = next;

            cur = Math.min(cur, next);
        }

        return cur;
    }
}
