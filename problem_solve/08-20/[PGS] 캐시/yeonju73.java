import java.util.*;
/*
    LRU -> 가장 오래 참조되지 않은 페이지 교체
    히트 -> 캐시에 존재할 때 -> 실행시간 1
        set에서 삭제 후 다시 맨 뒤에 삽입
    miss -> 캐시에X -> 실행시간 5
        set에 자리있으면 그냥 넣기
        꽉 차있으면 맨 앞을 삭제한 후 입력값 맨 뒤에 삽입
*/
class Solution {
    public int solution(int cacheSize, String[] cities) {
        int answer = 0;
        
        // 캐시 사이즈 0일 때 처리
        if (cacheSize == 0)
            return cities.length * 5;
        
        LinkedHashSet<String> set = new LinkedHashSet<>(cacheSize);
        
        for(String city: cities) {
            city = city.toLowerCase();
            // cache hit
            if (set.contains(city)){
                set.remove(city);
                set.add(city);
                answer += 1;
                continue;
            }
            // cache miss
            // 꽉차있을 때 맨 앞 요소 삭제
            if (set.size() == cacheSize) {
                set.removeFirst();
            } 
            set.add(city);
            answer += 5;
        }
        
        return answer;
    }
}
