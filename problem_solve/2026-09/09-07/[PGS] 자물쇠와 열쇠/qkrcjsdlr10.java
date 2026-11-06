class Solution {
    
    int holeCnt = 0;
    
    public int[][] rotKey(int m, int[][] key){
        int[][] temp  = new int[m][];
        
        for(int i = 0; i < m; i++){
            temp[i] = key[i].clone();
        }
        
        for(int i = 0; i < m; i++){
            for(int j = 0; j < m; j++){
                temp[i][j] = key[j][m - i - 1];
            }
        }
        
        return temp;
    }
    
    public boolean checkKey(int[][] key, int[][] lock, int n, int m, int y, int x){
        int cnt = 0;
        
        for(int i = 0; i < m; i++){
            for(int j = 0; j < m; j++){
                int ly = y + i;
                int lx = x + j;
                
                if(lx < 0 || lx >= n || ly < 0 || ly >= n) continue;
                if(lock[ly][lx] == 1 && key[i][j] == 1) return false;
                if(lock[ly][lx] == 0 && key[i][j] == 1) cnt++;
            }
        }
        
        return holeCnt == cnt;
    }
    
    public boolean solution(int[][] key, int[][] lock) {
        boolean answer = false;
        
        int n = lock.length;
        int m = key.length;
        int cnt = 4;
        
        
        for(int[] row : lock){
            for(int v : row){
                if(v == 0) holeCnt++;
            }
        }
        
        while(cnt > 0){
            if(answer) break;
            cnt--;
            
            for(int y = 1 - m; y < n; y++){
                if(answer) break;
                for(int x = 1 - m; x < n; x++){
                    if(checkKey(key, lock, n, m, y, x)) answer = true;
                }
            }
            key = rotKey(m, key);
        }
        
        
        return answer;
    }
}