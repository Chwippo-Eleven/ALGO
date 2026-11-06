import java.util.*;

class Solution {
    public long solution(int n, int[] times) {
        long answer = 0;
        
        long mx = 0;
        for(long x : times){
            mx = Math.max(mx, x);
        }
        
        long left = 0;
        long right = mx * n;
        
        // System.out.println(left + " " + right);
        
        while(left < right){
            long mid = (left + right) / 2;
            long totalPeople = 0l;
            
            for(long time : times){
                totalPeople += mid / time;
            }
            
            if(totalPeople >= n){
                right = mid;
            }else{
                left = mid + 1;
            }
        }
        
        answer = left;
        
        return answer;
    }
}