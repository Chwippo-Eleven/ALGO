import java.util.*;

class Solution {
    public int[] solution(int[] progresses, int[] speeds) {

        ArrayDeque<Integer> q = new ArrayDeque<>();
        int len = progresses.length;
        ArrayList<Integer> res = new ArrayList<>();

        for (int i = 0; i < len; i++){
            int t = 100 - progresses[i];
            int day = t % speeds[i] == 0 ? t / speeds[i] : t / speeds[i] + 1;

            if (q.isEmpty()){
                q.offerLast(day);
            } else {
                // 맨 앞 작업이랑 비교
                int remain = q.peekFirst();
                if (day <= remain){ // 앞 작업때문에 무조건 기다려야하는 상황
                    q.offerLast(day);
                } else {
                    res.add(q.size());
                    q.clear();
                    q.offerLast(day);
                }
            }
        }
        // 마지막에 남아있는 작업있으면 처리
        if (!q.isEmpty()){
            res.add(q.size());
        }

        int[] result = new int[res.size()];
        for (int i = 0; i < res.size(); i++){
            result[i] = res.get(i);
        }
        return result;
    }
}