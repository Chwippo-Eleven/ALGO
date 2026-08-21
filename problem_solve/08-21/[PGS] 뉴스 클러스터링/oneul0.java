import java.util.*;
class Solution {
    public int solution(String str1, String str2) {
        Map<String, Integer> map1 = new HashMap<>(), map2 = new HashMap<>();
        addChunk(map1, str1.toLowerCase());
        addChunk(map2, str2.toLowerCase());
        if(map1.isEmpty() && map2.isEmpty()) return 65536;

        Map<String, Integer> union = new HashMap<>(map1), intersection = new HashMap<>();
        map2.forEach((k, v) -> union.merge(k, v, Math::max));
        map1.forEach((k, v) -> { if(map2.containsKey(k)) intersection.put(k, Math.min(v, map2.get(k))); } );
        double unionSum = union.values().stream().mapToDouble(v -> v.doubleValue()).sum();
        double intersectionSum = intersection.values().stream().mapToDouble(v -> v.doubleValue()).sum();
        return (int)((intersectionSum / unionSum)*65536);
    }
    public void addChunk(Map<String, Integer> map, String str){
        for(int i = 0; i<str.length()-1; i++) {
            String key = str.substring(i, i+2);
            if(key.charAt(0)<'a' || key.charAt(1)<'a' || key.charAt(0)>'z' || key.charAt(1)>'z') continue;
            map.compute(key, (k, v) -> v == null ? 1 : ++v);
        }
    }
}
//중복원소도 가능함