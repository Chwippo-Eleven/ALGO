class Solution {
    int maxScoreDiff = Integer.MIN_VALUE;
    int[] answer = new int[11];
    public int[] solution(int n, int[] info) {
        int[] curr = new int[11];
        
        dfs(0, n, 0, 0, curr, info);
        
        return maxScoreDiff <= 0 ? new int[]{-1} : answer;
    }
    
    public void dfs(int idx, int remain, int lion, int apeach, int[] curr, int[] info){
        if(idx == 10){
            curr[idx] = remain;

            int scoreDiff = lion - apeach;
            
            if (scoreDiff > maxScoreDiff || 
                (scoreDiff == maxScoreDiff && isBetter(curr, answer))) {
                maxScoreDiff = scoreDiff;
                answer = curr.clone();
            }

            curr[idx] = 0;
            return;
        }
        
        //점수를 얻기로 결정
        int newRemain = remain - info[idx]-1;
        if(newRemain >= 0){
            curr[idx] = info[idx]+1;
            dfs(idx+1, newRemain, lion-idx+10, apeach, curr, info);
            curr[idx] = 0;
        }
        
        //점수를 못 얻거나 포기한 경우
        if(info[idx] > 0){
            dfs(idx+1, remain, lion, apeach-idx+10, curr, info);
        }
        else{
            dfs(idx+1, remain, lion, apeach, curr, info);
        }
    }
    
    //점수 차이가 같으면 더 낮은 점수에 대해 점수 얻기 진행하려고 체크하는 함수
    public boolean isBetter(int[] curr, int[] info){
        for(int i = 10; i>=0; i--){
            if(info[i] != curr[i]) return curr[i] > info[i];
        }
        return false;
    }
}