import java.util.*;
class Solution {
    public int solution(int k, int[] tangerine) {
        Map<Integer, Integer> map = new HashMap<>();
        for(int i = 0; i<tangerine.length; i++){
            map.compute(tangerine[i], (key, value) -> value == null ? 1 : ++value);
        }
        List<Integer> counts = new ArrayList<>(map.values());
        counts.sort(Collections.reverseOrder());
        int answer = 0, count = 0;
        for (int c : counts){
            count += c;
            answer++;
            if(k<=count) return answer;
        }
        return 0;
    }
}