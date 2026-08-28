import java.util.*;

class Solution {
    public int solution(int k, int[] tangerine) {
        int answer = 0;
        
        //크기별 귤 개수 세기
        Map<Integer, Integer> map = new HashMap<>();
        for(int t:tangerine){
            map.put(t, map.getOrDefault(t,0)+1);
        }
        
        
        //정렬 위해 list 사용
        List<Integer> list = new ArrayList<>();
        int index=0;
        for(int v: map.values()){
            list.add(v);
        }
        
        //내림차순 정렬
        Collections.sort(list, (a,b)->b-a);
        
        
        for(int i=0;i<list.size();i++){
            answer+=list.get(i);
            if(answer>=k)return i+1;
        }
        
        //사용은 안되지만 컴파일 에러 막기
        return 0;
    }
}
