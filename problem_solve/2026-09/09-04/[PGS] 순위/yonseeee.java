class Solution {
    public int solution(int n, int[][] results) {
        int answer = 0;
        
        int[][]win=new int[n+1][n+1];
        //이겼으면 1, 졌으면 2, 관계가 없으면 0
        for(int[] result:results){
            int winner=result[0];
            int loser=result[1];
            win[winner][loser]=1;
            win[loser][winner]=2;
        }

        // 플로이드-워셜, i가 k를 이기고 k가 j를 이기면 => i가 j를 이긴다
        for(int k=1;k<=n;k++){
            for(int i=1;i<=n;i++){
                for(int j=1;j<=n;j++){
                    if(win[i][k]==1&&win[k][j]==1){
                        win[i][j]=1;win[j][i]=2;
                    }
                }
            }
        }

        //모든 노드들과 관계가 정의되어 있으면 개수 반영
        for(int i=1;i<=n;i++){
            int cnt=0;
            for(int j=1;j<=n;j++){
                if(win[i][j]!=0)cnt++;
            }
            if(cnt==n-1)answer++;
        }
        return answer;
    }
}
