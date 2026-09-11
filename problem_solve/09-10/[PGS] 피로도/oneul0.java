class Solution {
    int n;
    int max = 0;
    int[][] dungeons;
    public int solution(int k, int[][] dungeons) {
        this.n = dungeons.length;
        this.dungeons = dungeons;
        comb(k, 0);
        return max;
    }
    
    public void comb(int hp, int mask){
        max = Math.max(max, Integer.bitCount(mask));
        
        for(int i = 0; i<n; i++){
            if(hp >= dungeons[i][0] && ((mask & (1<<i)) == 0)){
                comb(hp-dungeons[i][1], mask | (1<<i));
            }
        }
    }
}