class Solution
{
    public int solution(int[][] board)
    {
        int n = board.length;
        int m = board[0].length;
        int answer = 0;
        int[][] dp = new int[n+1][m+1]; //이 지점을 오른쪽 하단 꼭짓점으로 하는 가장 큰 정사각형의 한 변의 길이
        for(int row = 1; row<=n; row++){
            for(int col = 1; col<=m; col++){
                if(board[row-1][col-1] == 1){ //사각형이 될 수 있는 왼쪽 위 꼭짓점 발견
                    dp[row][col] = Math.min(dp[row-1][col], Math.min(dp[row][col-1], dp[row-1][col-1]))+1;
                    //왼쪽, 위, 대각선 세 방향의 dp 값 중 최솟값을 구함
                    //세 방향이 모두 만족하는 최대 크기는 최솟값까지이므로 현재 칸을 포함해 그 크기보다 1 큰 정사각형을 만들 수 있음
                    
                    answer = Math.max(answer, dp[row][col]);
                }
            }
        }
        return answer*answer;
    }
}