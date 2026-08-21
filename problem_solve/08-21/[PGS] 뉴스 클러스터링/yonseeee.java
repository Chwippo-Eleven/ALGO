import java.util.*;

class Solution {
    public int solution(String str1, String str2) {
        //다중집합을 원소-개수로 생각
        Map<String, Integer> map1 = new HashMap<>();
        Map<String, Integer> map2 = new HashMap<>();
        
        makeSet(map1, str1);
        makeSet(map2, str2);
        
        //교집합
        int intersection=0;
        int union=0;
        
        Set<String> interSet=new HashSet<>();
        for(String key1:map1.keySet()){//교집합 개수 구하기 + 교집합인 원소 interSet에 추가 
            for(String key2:map2.keySet()){
                if(key1.equals(key2)){
                    intersection+=Math.min(map1.get(key1), map2.get(key2));
                    union+=Math.max(map1.get(key1), map2.get(key2));
                    
                    interSet.add(key1);
                }
            }
        }

        //위에서 구한 교집합 외에 남는 원소들 개수 세기
       for(String key1:map1.keySet()){
           if(!interSet.contains(key1)){
               union+=map1.get(key1);
           }
       }
       for(String key2:map2.keySet()){
           if(!interSet.contains(key2)){
               union+=map2.get(key2);
           }
       }
        
        if(intersection==0&&union==0)return 1*65536;
        return (int)((intersection/(double)union)*65536);
    }
    
    private boolean isAlphabet(char x){
        if((x>='a'&&x<='z') || (x>='A'&&x<='Z')){
            return true;
        }
        return false;
    }
    
    private void makeSet(Map<String, Integer> map, String x){
        for(int i=0;i<x.length()-1;i++){
            if(isAlphabet(x.charAt(i))&&isAlphabet(x.charAt(i+1))){//연속으로 2개가 알파벳인 곳 찾기
                String elem=x.charAt(i)+""+x.charAt(i+1);
                elem=elem.toLowerCase();//소문자 변환
                map.put(elem, map.getOrDefault(elem, 0)+1);
            }
          
        }
    }
}
