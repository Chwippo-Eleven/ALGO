import java.util.*;

class Solution {
    public long solution(int n, int[] times) {
        long answer = 0;
        int  max=Integer.MIN_VALUE;
        for(int time:times){
            max=Math.max(max, time);
        }
        long left=1;
        long right=(long)max*n;//제일 오래 걸릴 때 시간
        
        while(left<=right){
            
            long mid=left+(right-left)/2;
            
            long cnt=0;
            for(int time:times){
                cnt+=mid/time;
                if(cnt>=n){//n명을 처리할 수 있는 시간인지 확인
                    break;
                }
            }
            if(cnt>=n){
                answer=mid;
                right=mid-1;//아직 최소값을 찾아야 해
            }else{
                left=mid+1;
            }

        }
        return answer;
    }
}
