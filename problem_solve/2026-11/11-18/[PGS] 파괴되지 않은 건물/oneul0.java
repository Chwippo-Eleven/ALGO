class Solution {
    int n, m;
    int[][] board;
    int[][] diff;
    public int solution(int[][] board, int[][] skill) {
        this.board = board;
        this.n = board.length;
        this.m = board[0].length;
        this.diff = new int[n+1][m+1];
        for(int[] s : skill){
            inflict(s);
        }
        for(int i = 0; i<n; i++){
            for(int j = 0; j<m; j++){
                diff[i][j] += 
                    (i > 0 ? diff[i-1][j] : 0) 
                    + (j > 0 ? diff[i][j-1] : 0) 
                    - (i > 0 && j > 0 ? diff[i-1][j-1] : 0);
            }
        }
        int answer = 0;
        for(int i = 0; i<n; i++){
            for(int j = 0; j<m; j++){
                answer += (board[i][j] + diff[i][j] > 0 ? 1 : 0);
            }
        }
        
        return answer;
    }
    public void inflict(int[] info){
        int sx = info[1];
        int sy = info[2];
        int ex = info[3];
        int ey = info[4];
        int val = info[0] == 1 ? -info[5] : info[5];
        
        diff[sx][sy] += val; //시작 지점
        diff[sx][ey+1] -= val; //시작행 끝 지점
        diff[ex+1][sy] -= val; //끝행
        diff[ex+1][ey+1] += val; //끝행 끝 지점
    }
}
