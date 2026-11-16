import java.util.*;

class Solution {

    int[][] board;

    public int rotArr(int sy, int sx, int ey, int ex){
        int mn = Integer.MAX_VALUE;
        int temp = board[sy][ex];
        mn = Math.min(mn, temp);

        for(int i = ex; i > sx; i--){
            board[sy][i] = board[sy][i-1];
            mn = Math.min(mn, board[sy][i]);
        }

        int temp2 = board[ey][ex];
        mn = Math.min(mn, temp2);

        for(int i = ey; i > sy + 1; i--){
            board[i][ex] = board[i - 1][ex];
            mn = Math.min(mn, board[i][ex]);
        }

        board[sy + 1][ex] = temp;
        temp = board[ey][sx];
        mn = Math.min(mn, temp);

        for(int i = sx; i < ex - 1; i++){
            board[ey][i] = board[ey][i+1];
            mn = Math.min(mn, board[ey][i]);
        }

        board[ey][ex - 1] = temp2;

        for(int i = sy; i < ey - 1; i++){
            board[i][sx] = board[i + 1][sx];
            mn = Math.min(mn, board[i][sx]);
        }

        board[ey - 1][sx] = temp;
        mn = Math.min(mn, temp);

        return mn;
    }

    public int[] solution(int rows, int columns, int[][] queries) {
        int[] answer = new int[queries.length];
        board = new  int[rows + 5][columns + 4];

        int num = 1;
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < columns; j++){
                board[i][j] = num++;
            }
        }

        for(int i = 0; i < queries.length; i++){
            int res = rotArr(queries[i][0] - 1
                    , queries[i][1] - 1
                    , queries[i][2] - 1
                    , queries[i][3] - 1);
            answer[i] = res;
        }

        return answer;
    }
}