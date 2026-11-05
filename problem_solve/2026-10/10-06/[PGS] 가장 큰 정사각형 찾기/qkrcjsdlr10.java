import java.util.*;

class Solution
{
    public int solution(int [][]board)
    {
        int answer = 1234;
        int mx = 0;
        int n = board.length;
        int m = board[0].length;

        for(int i = 0; i < n; i++){
            for(int j = 0; j < m; j++){
                if(board[i][j] == 0) continue;

                if(i > 0 && j > 0){
                    board[i][j] = Math.min(board[i-1][j-1],
                            Math.min(board[i-1][j], board[i][j-1])) + 1;
                }

                mx = Math.max(mx, board[i][j]);
            }
        }

        return mx * mx;
    }
}