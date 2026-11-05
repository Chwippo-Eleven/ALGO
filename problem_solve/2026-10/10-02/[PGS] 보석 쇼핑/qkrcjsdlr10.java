import java.util.*;

class Solution {
    public int[] solution(String[] gems) {
        int[] answer = new int[2];

        int start = 0;
        int end = 0;

        Set<String> st = new HashSet<>(Arrays.asList(gems));
        int n = st.size();

        Map<String, Integer> mp = new HashMap<>();

        int mn = Integer.MAX_VALUE;
        int as = 0;
        int ae = 0;

        while (end < gems.length) {

            // end 위치 보석 추가
            mp.put(gems[end], mp.getOrDefault(gems[end], 0) + 1);
            end++;

            // 모든 종류를 모았으면 start 줄이기
            while (mp.size() == n) {

                if (mn > end - start) {
                    mn = end - start;
                    as = start;
                    ae = end;
                }

                // start 쪽 보석이 중복이면 제거하고 start 증가
                if (mp.get(gems[start]) > 1) {
                    mp.put(gems[start], mp.get(gems[start]) - 1);
                    start++;
                } else {
                    break;
                }
            }
        }

        answer[0] = as + 1;
        answer[1] = ae;

        return answer;
    }
}