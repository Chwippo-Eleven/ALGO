import java.util.*;

class Solution {
    public int solution(int[] queue1, int[] queue2) {

        long sum = 0;
        long target = 0;
        long q1Sum = 0;
        long q2Sum = 0;

        long firstQ1 = 0;
        long firstQ2 = 0;


        ArrayDeque<Long> q1 = new ArrayDeque<>();
        ArrayDeque<Long> q2 = new ArrayDeque<>();

        for (int i = 0; i < queue1.length; i++){
            long qq1 = (long)queue1[i];
            long qq2 = (long)queue2[i];
            q1Sum += qq1;
            q2Sum += qq2;
            sum += (qq1 + qq2);
            q1.addLast(qq1);
            q2.addLast(qq2);
        }

        target = div(sum);
        firstQ1 = q1Sum;
        firstQ2 = q2Sum;

        int cnt = 0;
        while (true){
            if (cnt >= 4 * queue1.length){ // 얼마나 돌려봐야 정답인 경우가 없는지 확인할 수 있는지..?
                return -1;
            }
            if (q1Sum == q2Sum && (q1Sum != 0 || q2Sum != 0)){
                return cnt;
            }
            // q1의 합이 더 큰 경우, q1에서 빼서 q2에 넣기
            if (q1Sum > q2Sum){
                long del = q1.removeFirst();
                q1Sum -= del;
                q2Sum += del;
                q2.addLast(del);
            } else {
                long del = q2.removeFirst();
                q2Sum -= del;
                q1Sum += del;
                q1.addLast(del);
            }
            cnt++;
        }

    }
    static int div(long sum){
        if (sum % 2 == 0){
            return (int)(sum / 2);
        } else {
            return (int)(sum / 2 + 1);
        }
    }
}