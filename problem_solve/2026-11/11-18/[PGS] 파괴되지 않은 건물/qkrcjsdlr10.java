class Solution {

    int[][] diff;

    public void func(int sy, int sx, int ey, int ex, int type, int value){
        if(type == 1) value *= -1;
        diff[sy][sx] += value;
        diff[sy][ex + 1] -= value;
        diff[ey + 1][sx] -= value;
        diff[ey + 1][ex + 1] += value;

    }

    public int solution(int[][] board, int[][] skill) {
        int answer = 0;

        int n = board.length;
        int m = board[0].length;

        diff = new int[n + 1][m + 1];

        for(int i = 0; i < skill.length; i++){
            func(skill[i][1], skill[i][2], skill[i][3], skill[i][4], skill[i][0], skill[i][5]);
        }

        for(int i = 0; i < n; i++){
            for(int j = 1; j < m; j++){
                diff[i][j] += diff[i][j - 1];
            }
        }

        for(int i = 0; i < m; i++){
            for(int j = 1; j < n; j++){
                diff[j][i] += diff[j - 1][i];
            }
        }

        for(int i = 0; i < n; i++){
            for(int j = 0; j < m; j++){
                board[i][j] += diff[i][j];
            }
        }

        for(int[] row : board){
            for(int x : row){
                // System.out.print(x + " ");
                if(x > 0) answer++;
            }
            System.out.println();
        }

        return answer;
    }
}