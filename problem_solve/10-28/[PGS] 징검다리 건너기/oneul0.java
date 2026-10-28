import java.util.*;

class Solution {
    public int solution(int[] stones, int k) {
        int l = 0;
        int r = Arrays.stream(stones).max().orElse(0);
        int maxVal = 0;
        
        while(l<=r){
            boolean canGo = true;
            int count = 0;
            int mid = (l+r) >>> 1;
            
            for(int s : stones){
                if(s<=mid){
                    count++;
                    if(count >= k){
                        canGo = false;
                        break;
                    }
                }
                else count = 0;
            }
            
            if(!canGo) r = mid-1;
            else l = mid+1;
        }
        return l;
    }
}