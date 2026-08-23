import java.util.*;

class Solution {
    public int solution(String str1, String str2) {
        Map<String, Integer> map1 = makeMultiSet(str1);
        Map<String, Integer> map2 = makeMultiSet(str2);

        int intersection = getIntersectionSize(map1, map2);

        // 집합 1 사이즈 + 집합 2 사이즈 - 교집합 크기 => 합집합 크기
        int union = getSize(map1) + getSize(map2) - intersection;

        if (union == 0){
            return 65536;
        } else {
            return intersection * 65536 / union;
        }
    }
    public Map<String, Integer> makeMultiSet(String s){
        // 1. 소문자로 통일시켜주고
        // 2. 둘 다 알파벳이여야 하나의 문자열로 취급
        Map<String, Integer> map = new HashMap<>();

        s = s.toLowerCase();

        for (int i = 0; i < s.length() - 1; i++){
            char first = s.charAt(i);
            char second = s.charAt(i + 1);

            if (!isAlpha(first) || !isAlpha(second)){
                continue;
            }

            String cur = s.substring(i, i + 2);
            map.put(cur, map.getOrDefault(cur, 0) + 1);
        }
        return map;
    }
    public boolean isAlpha(char c){
        return c >= 'a' && c <= 'z';
    }
    public int getSize(Map<String, Integer> map){

        int cnt = 0;

        for (int count : map.values()){
            cnt += count;
        }
        return cnt;
    }
    public int getIntersectionSize(Map<String, Integer> map1, Map<String, Integer> map2){
        int size = 0;

        // map1, map2에 모두 속한다면 둘 중 최솟값이 교집합 크기에 들어감
        for (String key : map1.keySet()){
            if (map2.containsKey(key)){
                size += Math.min(map1.get(key), map2.get(key));
            }
        }
        return size;
    }
}