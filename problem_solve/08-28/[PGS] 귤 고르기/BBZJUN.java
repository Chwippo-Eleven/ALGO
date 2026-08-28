// 아 머지 실수로


import java.util.*;

class Solution {
    public int solution(int k, int[] tangerine) {
        
        // 귤 카운트 map
        Map<Integer, Integer> map = new HashMap<>();
        
        
        // 세어주기
        for (int x : tangerine) {
            map.put(x, map.getOrDefault(x, 0) + 1);
        }
        
        // 귤 크기 말고 수량만 저장
        List<Integer> list = new ArrayList<>();

        // 수량만 가져오기 key, value 중 value만
        for (int count : map.values()) {
            list.add(count);
        }

        
        // 내림차순 정렬로 큰 놈부터
        list.sort((a, b) -> b - a);

        int answer = 0;
        
        // 빼면서 다 채우면 종료
        for (int count : list) {
            k = k - count;
            answer++;
            
            if (k <= 0) {
                break;
            }
        }
        
        return answer;
    }
}
