import java.util.*;

class Solution {
    public long solution(int n, int[] times) {
        Arrays.sort(times);

        long left = 1;
        long right = (long) times[times.length - 1] * n; //최대시간. 다 마지막 심사대에서 받을 경우

        while (left <= right) {
            long mid = (left + right) / 2;

            // mid분 동안 심사 가능한 총 인원 수
            long count = 0;
            for (int t : times) {
                count += mid / t;
            }

            if (count >= n) {
                right = mid - 1;  // 더 짧은 시간도 가능할 수도
            } else {
                left = mid + 1;   // 시간 부족
            }
        }

        return left;
    }
}
