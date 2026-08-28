import java.util.*;
/*
    서로 다른 종류가 최소일 때
    내림차순 정렬 -> 담기 가능하면 무조건 넣기
*/
class Solution {
    public int solution(int k, int[] tangerine) {
        int answer = 0;
        Map<Integer, Integer> map = new HashMap<>();
        
        for(int t: tangerine) {
            map.put(t, map.getOrDefault(t, 0)+1);
        }
        
        List<Integer> list = new ArrayList<>(map.values());
        Collections.sort(list, Collections.reverseOrder());
        
        int count = 0;
        
        for(int tan: list) {
            // 꼭 k에 맞추지 않아도 해당 종류의 몇개만 가져와서 담을 수 있음
            if (count + tan >= k) {
                answer++;
                break;
            } else if(count + tan < k) {
                count += tan;
                answer++;
            }
        }
        return answer;
    }
}
