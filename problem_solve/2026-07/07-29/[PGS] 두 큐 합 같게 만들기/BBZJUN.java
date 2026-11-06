import java.util.*;

class Solution {
    public int solution(int[] queue1, int[] queue2) {
        Deque<Integer> q1 = new ArrayDeque<>();
        Deque<Integer> q2 = new ArrayDeque<>();

        long sum1 = 0;
        long sum2 = 0;
        int len1 = 0;
        int len2 = 0;
        for (int x : queue1) {
            q1.offerLast(x);
            sum1 += x;
            len1++;
        }

        for (int x : queue2) {
            q2.offerLast(x);
            sum2 += x;
            len2++;
        }

        long total = sum1 + sum2;

        if (total % 2 == 1) // 홀수는 2개로 쪼개서 더한거와 같을 수가 없음
            return -1;

        long target = total / 2;

        int count = 0;
        
        while (true) {
            
            if (count > (len1+len2)*2) // 최대 이동 A로 이동 len2, B로 이동 len1 => len1+len2 에다가 안되면 여기서 반대로도 돌아야함 + len1+len2즉, len1+len2 *2
                break;

            if (sum1 == target) // 한쪽만 타겟과같아지면 다른쪽도 같음 ㅇㅇ
                return count;

            if (sum1 > target) { // sum1이 크면 여기서는 빼서 sum2에준다
                int x = q1.pollFirst();
                q2.offerLast(x);
                sum1 -= x;
            } else {// sum2이 크면 여기서는 빼서 sum1에준다
                int x = q2.pollFirst();
                q1.offerLast(x);
                sum1 += x;
            }

            count++;
        }

        return -1;
    }
}
