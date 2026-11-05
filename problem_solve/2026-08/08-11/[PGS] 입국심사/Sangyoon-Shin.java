import java.util.*;

class Solution {
    public long solution(int n, int[] times) {
        Arrays.sort(times);

        long start = 0;
        long end = (long)times[times.length - 1] * n;
        long res = end;

        while (start <= end){

            long mid = (start + end) / 2;
            long cnt = 0;

            for (int t : times){
                cnt += mid / t;
            }
            if (cnt >= n){ // n명 보다 더 많은 사람을 받을 수 있네? 시간 줄여
                res = mid;
                end = mid - 1;
            } else { // n명 못받으면 시간 늘리고
                start = mid + 1;
            }
        }
        return res;
    }
}