import java.util.*;
class Solution {
    public boolean solution(int[][] key, int[][] lock) {
        boolean answer = true;
        
        int M=key.length;
        int N=lock.length;
        
        int size=N*3;
        int[][]board=new int[size][size];
        
        for(int i=0;i<N;i++){
            for(int j=0;j<N;j++){
                board[i+N][j+N]=lock[i][j];
            }
        }
        
        for(int r=0;r<4;r++){
            for(int y=0;y<=size-M;y++){
                for(int x=0;x<=size-M;x++){
                    
                    //key 끼우기
                    for(int i=0;i<M;i++){
                        for(int j=0;j<M;j++){
                            board[y+i][x+j]+=key[i][j];
                        }
                    }
                    
                    //lock 영역 확인
                    if(check(board, N))return true;
                    
                    //원상 복구
                    for(int i=0;i<M;i++){
                        for(int j=0;j<M;j++){
                            board[y+i][x+j]-=key[i][j];
                        }
                    }
                    
                    
                }
            }
            key=rotate(key);
        }
        
        
        return false;
    }
    private static boolean check(int[][] board, int N){
        for(int i=N;i<2*N;i++){
            for(int j=N;j<2*N;j++){
                if(board[i][j]!=1){
                    return false;
                }
            }
        }
        
        return true;
        
    }
    
    private int[][] rotate(int[][] key){
        int m=key.length;
        int[][]rotated=new int[m][m];
        
        for(int i=0;i<m;i++){
            for(int j=0;j<m;j++){
                rotated[j][m-1-i]=key[i][j];
            }
        }
        return rotated;
    }
}
