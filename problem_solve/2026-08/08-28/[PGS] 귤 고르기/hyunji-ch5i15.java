import java.util.*;
// key: 숫자, value: 출현 횟수
class Solution {
    public int solution(int k, int[] tangerine) {
        HashMap<Integer, Integer> map = new HashMap<>();
        int n = tangerine.length;
        for (int i=0; i<n; i++) {
            map.put(tangerine[i], map.getOrDefault(tangerine[i], 0) + 1);
        }
        // value 작은 순 정렬
        List<Map.Entry<Integer, Integer>> entryList = new ArrayList<>(map.entrySet());
        entryList.sort(Map.Entry.comparingByValue());
        
        int answer = entryList.size(); // 가능한 최댓값 설정
        int cnt = n-k;
        for (Map.Entry<Integer,Integer> entry: entryList) {
            int count = entry.getValue();
            if (cnt >= count) {
                cnt -= count;
                answer--;
            } else break;
        }
        return answer;
    }
}