import java.util.*;

class Solution {
    public int solution(String str1, String str2) {
        HashMap<String, Integer> map1 = new HashMap<>();
        HashMap<String, Integer> map2 = new HashMap<>();
        // 1. str1 조각 만들기
        for (int i=0; i<str1.length()-1; i++) {
            char a = str1.toLowerCase().charAt(i);
            char b = str1.toLowerCase().charAt(i+1);
            if (isAlphabet(a) && isAlphabet(b)) {
                // key: 조각, value: 출연횟수
                String sa = String.valueOf(a);
                String sb = String.valueOf(b);
                map1.put(sa+sb, map1.getOrDefault(sa+sb,0)+1);
            }
        }
        // 2. str2 조각 만들기
        for (int i=0; i<str2.length()-1; i++) {
            char a = str2.toLowerCase().charAt(i);
            char b = str2.toLowerCase().charAt(i+1);
            if (isAlphabet(a) && isAlphabet(b)) {
                // key: 조각, value: 출연횟수
                String sa = String.valueOf(a);
                String sb = String.valueOf(b);
                map2.put(sa+sb, map2.getOrDefault(sa+sb,0)+1);
            }
        }
        // 3. 교집합 구하기 (이게 관건인 듯)
        // 둘 중 아무 맵이나 순회하여 교집합 구하기
        int both = 0;
        for (String puzzle: map1.keySet()) {
            if (map2.containsKey(puzzle)) {
                both += Math.min(map1.get(puzzle), map2.get(puzzle));
            }
        }
        // 4. 합집합 계산 = 전체 조각 수 - 교집합
        // 4-1) 각 map1별 value 합계 구하기
        int hap = 0;
        for (String s: map1.keySet()) {
            hap += map1.get(s);
        }
        // 4-2) 각 map2별 value 합계 구하기
        for (String s: map2.keySet()) {
            hap += map2.get(s);
        }
        hap -= both;  // 중복된 교집합(both)를 hap에서 빼줘야 합집합 완성
        // 5. 최종 계산
        int answer = 0;
        if (hap == 0) return 65536;
        else {
            answer = (int)((double) both / hap * 65536);
        }
        return answer;
    }
    // 알파벳 판별 함수
    private boolean isAlphabet(char s) {
        if ('a' <= s && s <= 'z') {
            return true;
        }
        return false;
    }
}