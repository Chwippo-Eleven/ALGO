import java.util.*;
/*
    자카드 유사도: 두 집합의 교집합 크기를 합집합 크기로 나눈 값
    중복 혀옹 집합에서
    교집합 -> 두 집합 중 더 많이 적게 가진 개수만큼 추가
    합집합 -> 같이 가지고 있다면 더 많이 가진 쪽의 개수만큼 추가
    
    입력으로 들어온 문자열 두 글자씩 끊기
    슬라이딩 하며 한 자씩 이동
    영문자가 아닌 것이 들어가 있으면 그 쌍 버림
    대소문자 비교X
*/
class Solution {
    public int solution(String str1, String str2) {
        int answer = 0;
        
        char[] str1Array = str1.toCharArray();
        char[] str2Array = str2.toCharArray();
        
        Map<String, Integer> str1Map = new HashMap<>();
        Map<String, Integer> str2Map = new HashMap<>();
        
        for(int i = 0; i < str1.length()-1; i++){
            // 영문자인 조합만 남김
            if(!(Character.isLetter(str1Array[i]) && Character.isLetter(str1Array[i+1]))){
                continue;
            }
            String token = str1.substring(i,i+2).toLowerCase();
            str1Map.put(token, str1Map.getOrDefault(token, 0)+1);
        }
        System.out.println(str1Map);
        
        for(int i = 0; i < str2.length()-1; i++){
            if(!(Character.isLetter(str2Array[i]) && Character.isLetter(str2Array[i+1]))){
                continue;
            }
            String token = str2.substring(i,i+2).toLowerCase();
            str2Map.put(token, str2Map.getOrDefault(token, 0)+1);
        }
        System.out.println(str2Map);
        
        // 합집합, 교집합 key 추출
        Set<String> retainAllSet = new HashSet<>(str1Map.keySet());
        Set<String> addAllSet = new HashSet<>(str1Map.keySet());
        Set<String> set2 = str2Map.keySet();
        
        retainAllSet.retainAll(set2);
        addAllSet.addAll(set2);
        
        // 교집합 계산
        int intersection = 0;
        for(String r : retainAllSet){
            intersection += Math.min(str1Map.get(r), str2Map.get(r));
        }
        
        // 합집합 계산
        int all = 0;
        for(String r : addAllSet){
            all += Math.max(str1Map.getOrDefault(r, 0), str2Map.getOrDefault(r, 0));
        }
        
        if(all != 0)
            answer = (int)((double)intersection / all * 65536);
        else answer = 65536;
        
        return answer;
    }
}
