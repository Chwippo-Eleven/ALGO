import java.util.*;

// 자카드 유사도
class Solution {
    public int solution(String str1, String str2) {
        long answer = 0L;
        
        // 대문자로 처리
        String newStr1 = str1.toUpperCase();
        String newStr2 = str2.toUpperCase();
        
        // s1 다중 집합
        List<String> list1 = new ArrayList<>();
        for (int i = 0; i < newStr1.length() - 1; i++){
            char c1 = newStr1.charAt(i);
            char c2 = newStr1.charAt(i+1);
            // 문자만 넣기
            if (Character.isLetter(c1) && Character.isLetter(c2)){
                StringBuilder sb = new StringBuilder();
                sb.append(c1).append(c2);
                list1.add(sb.toString());
            }
        }
        
        // s2 다중 집합
        List<String> list2 = new ArrayList<>();
        for (int i = 0; i < newStr2.length() - 1; i++){
            char c1 = newStr2.charAt(i);
            char c2 = newStr2.charAt(i+1);
             // 문자만 넣기
            if (Character.isLetter(c1) && Character.isLetter(c2)){
                StringBuilder sb = new StringBuilder();
                sb.append(c1).append(c2);
                list2.add(sb.toString());
            }
        }
        
        // 교집합구하기
        int kyozip = 0;
        // list1돌려서 list2에 있으면
        for (String s: list1){
            if (list2.contains(s)){
                //list2에서 지우기
                list2.remove(s);
                kyozip++;
            }
        }
        
        // 합집합
        int hapzip = list1.size() + list2.size();
        
        // 예외 처리
        if (hapzip == 0){
            return 65536;
        }
        
        answer = (long)(((double)kyozip/hapzip)*65536);
        
        return (int)answer;
    }
}
