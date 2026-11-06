class Solution {
    public int solution(int n, int[][] q, int[] ans) {
        int answer = 0;

        // 30C5 = 142,506번
        for(int i=1;i<n-3;i++){
            for(int j=i+1;j<n-2;j++){
                for(int k=j+1;k<n-1;k++){
                    for(int l=k+1;l<n;l++){
                        for(int m=l+1;m<=n;m++){
                            int[]tmp={i,j,k,l,m};
                            if(isCandidate(tmp, q, ans))answer++;
                        }
                    }
                }
            }
        }
        return answer;
    }
    
    public boolean isCandidate(int[] tmp, int[][] q, int[] ans){
        int len=ans.length;
        
        for(int i=0;i<len;i++){
            int cnt=0;
            for(int j=0;j<5;j++){
                for(int k=0;k<5;k++){
                    if(q[i][j]==tmp[k]) cnt++;
                }
            }
            if(cnt!=ans[i])return false;
        }
        
        return true;
    }
}
