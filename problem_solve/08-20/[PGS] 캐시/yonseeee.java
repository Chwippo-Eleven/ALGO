import java.util.*;
class Solution {
    public int solution(int cacheSize, String[] cities) {
        int answer = 0;
        
        Map<String, Integer> map = new HashMap<>();
        
        Set<String> set = new HashSet<>();

        //이걸 못찾네....
        if(cacheSize==0)return cities.length*5;
        
        for(String city: cities){
            city=city.toLowerCase();
            
            //hit
            if(set.contains(city)){
                answer++;
            }
            //miss
            else{
                //캐시 사이즈보다 작을때는 그냥 넣기
                if(set.size()<cacheSize){
                    set.add(city);
                }else{
                    int max=Integer.MIN_VALUE;
                    String tmp="";
                    for(String value: set){
                        if(max<map.get(value)){
                            max=map.get(value);
                            tmp=value;
                        }
                    }
                    set.remove(tmp);
                    set.add(city);
                }
                
                answer+=5;
                
            }
            //참조될 때마다 시간 갱신
            map.put(city, 0);
        

            //참조되었던 애들 시간 +1
            for(String key:map.keySet()){
                map.put(key, map.get(key)+1);
            }

        }
        return answer;
    }
}
