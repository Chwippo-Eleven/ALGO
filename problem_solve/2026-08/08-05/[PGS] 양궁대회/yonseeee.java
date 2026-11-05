import java.util.*;
class Solution {
    
    int[] apeach;
    int[]lion=new int[11];
    int[]answer={-1};
    int max=0;
    public int[] solution(int n, int[] info) {
        apeach=info;
        dfs(n,0);
        return answer;
    }
    
    void dfs(int remain, int index){
        
        if(index==10){
            lion[10]=remain;
            
            check();
            
            lion[10]=0;
            
            return;
        }
        //1. 라이언이 획득
        int arrow=apeach[index]+1;
        if(remain>=arrow){
            lion[index]=arrow;
            dfs(remain-arrow, index+1);
            lion[index]=0;
        }
        
        //2. 라이언이 획득x
        dfs(remain, index+1);
    }
    void check(){
        int lion_score=0;
        int apeach_score=0;
        
        for(int i=0;i<11;i++){
            if(lion[i]==0&&apeach[i]==0){
                continue;
            }
            int score=10-i;
            
            if(lion[i]>apeach[i]){
                lion_score+=score;
            }else apeach_score+=score;
        }
        
        int diff=lion_score-apeach_score;
        
        if(diff<=0) return;
        
        if(diff>max){
            max=diff;
            answer=Arrays.copyOf(lion,11);
        }
        
        else if(diff==max&&greatherThan(lion, answer)){
            answer=Arrays.copyOf(lion,11);
        }
    }
    boolean greatherThan(int[] candidate, int[] current){
        for(int i=10;i>=0;i--){
            if(candidate[i]!=current[i]){
                return candidate[i]>current[i];
            }
        }
        
        return false;
    }
}
