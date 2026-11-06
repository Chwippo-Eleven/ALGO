import java.util.*;

class Solution {
    static int[] makeNum = new int[5];
    static int answer = 0;

    public int solution(int n, int[][] q, int[] ans) {

        answer = 0;

        TTT(n, 1, 0, q, ans);

        return answer;
    }

    public void TTT(int n, int start, int len, int[][] q, int[] ans) {

        if (len == 5) { // 5칸차면 검사
            checkNum(q, ans);
            return;
        }
        

        for (int i = start; i <= n; i++) { // 1부터 n까지 채우면서

            makeNum[len] = i;

            TTT(n, i + 1, len + 1, q, ans);
        }
    }

    public void checkNum(int[][] q, int[] ans) {

        for (int i = 0; i < q.length; i++) {

            Set<Integer> set = new HashSet<>(); // 중복 찾기 위한 set

            for (int j = 0; j < 5; j++) { // 만든거 넣어줌
                set.add(makeNum[j]);
            }

            for (int j = 0; j < 5; j++) { // q넣어줌
                set.add(q[i][j]);
            }

            if (ans[i] != 10 - set.size()) {//하나도 안겹치면 10개일텐데. 1개가 겹치면 9개가 저장될거니 10-size로 겹치는거 찾기
                return;
            }
        }
        //다통과면 더해줌
        answer++;
    }
}
