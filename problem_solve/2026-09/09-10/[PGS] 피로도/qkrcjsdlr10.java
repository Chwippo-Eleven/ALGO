class Solution {
    
    static int n = 0;
    static boolean[] vis;
    static int res = 0;
    public static void dfs(int[][] dungeons, int cnt, int isSelect, int curHp){
        res = Math.max(cnt, res);
        
        for(int i = 0; i < n; i++){
            if((isSelect & (1 << i)) != 0) continue;
            if(curHp < dungeons[i][0]) continue;
            dfs(dungeons, cnt + 1, isSelect | (1 << i), curHp - dungeons[i][1]);
        }
    }
    
    public int solution(int k, int[][] dungeons) {
        int answer = -1;
        n = dungeons.length;
        
        dfs(dungeons, 0, 0, k);
        
        answer = res;
        
        return answer;
    }
}