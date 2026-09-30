import java.util.*;

class Solution {
    public int[] solution(int[] progresses, int[] speeds) {
        int n = progresses.length;
        List<Integer> days = new ArrayList<>();

        int day = 0;

        for (int i = 0; i < n; i++) {
            if (progresses[i] + day * speeds[i] < 100) {
                int remain = 100 - (progresses[i] + day * speeds[i]);
                day += (remain + speeds[i] - 1) / speeds[i];
            }

            days.add(day);
        }

        List<Integer> result = new ArrayList<>();

        int cnt = 1;

        for (int i = 1; i < n; i++) {
            if (days.get(i).equals(days.get(i - 1))) {
                cnt++;
            } else {
                result.add(cnt);
                cnt = 1;
            }
        }

        result.add(cnt);

        int[] answer = new int[result.size()];

        for (int i = 0; i < result.size(); i++) {
            answer[i] = result.get(i);
        }

        return answer;
    }
}