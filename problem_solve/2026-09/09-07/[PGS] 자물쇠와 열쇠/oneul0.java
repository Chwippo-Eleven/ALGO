class Solution {
    public boolean solution(int[][] key, int[][] lock) {
        int M = key.length;
        int N = lock.length;
        int[][][] keys = new int[4][M][M];
        rotateKey(keys, key, M);
        int holes = 0;
        for(int r= 0; r<N; r++){
            for(int c = 0; c<N; c++){
                holes += (lock[r][c] == 0) ? 1 : 0;
            }
        }
        
        if(M==N) {
            for(int r = 0; r<4; r++){
                if(isFit(0,0,M, N, keys[r], lock, holes)) return true;
            }
        }
        for(int r =0; r<4; r++){
            for(int i = -(M-1); i<N; i++){
                for(int j = -(M-1); j<N; j++){
                    if(isFit(i, j, M, N, keys[r], lock, holes)) return true;    
                }
            }
        }
        return false;
    }
    public boolean isFit(int sr, int sc, int M, int N, int[][] key, int[][] lock, final int holes){
        int cnt = 0;
        for(int i = 0; i<M; i++){
            for(int j = 0; j<M; j++){
                int r = sr + i;
                int c = sc + j;
                if(r<0 || c<0 || r>=N || c>=N) continue;
                if(key[i][j]+lock[r][c] > 1) return false; //돌기 + 돌기
                if(key[i][j] == 1 && lock[r][c] == 0) cnt++;
            }
        }
        return cnt == holes;
    }
    public void rotateKey(int[][][] keys, int[][] key, int M){
        for(int i = 0; i<M; i++){
            keys[0][i] = key[i].clone();
        }
        for(int i = 1; i<4; i++){
            for(int r= 0; r<M; r++){
                for(int c = 0; c<M; c++){
                    keys[i][r][c] = keys[i-1][M-1-c][r];
                }
            }
        }
    }
}