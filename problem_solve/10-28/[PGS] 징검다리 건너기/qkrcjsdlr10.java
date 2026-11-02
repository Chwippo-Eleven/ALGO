import java.util.*;

class Solution {

    public boolean check(int mid, int k, int[] stones){
        int cnt = 0;

        for(int stone : stones){
            if(stone < mid) cnt++;
            else cnt = 0;

            if(cnt >= k) return false;
        }

        return true;
    }

    public int solution(int[] stones, int k) {
        int answer = 0;

        int left = 0;
        int right = Arrays.stream(stones).max().getAsInt();

        while(left <= right){
            int mid = (left + right) / 2;

            boolean isValid = check(mid, k, stones);
            if(isValid){
                left = mid + 1;
                answer = mid;
            }else{
                right = mid - 1;
            }
        }



        return answer;
    }
}