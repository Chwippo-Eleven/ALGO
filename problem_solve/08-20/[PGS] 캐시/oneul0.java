import java.util.*;
class Solution {
    class Pair implements Comparable<Pair>{
        String name;
        int lastSeen;
        Pair(String name, int lastSeen){
            this.name = name.toLowerCase();
            this.lastSeen = lastSeen;
        }
        @Override
        public boolean equals(Object o){
            if(this == o) return true;
            if(!(o instanceof Pair)) return false;
            Pair other = (Pair) o;
            return Objects.equals(this.name, other.name);
        }
        @Override
        public int hashCode(){
            return name.hashCode();
        }
        @Override
        public int compareTo(Pair p){
            return p.lastSeen - this.lastSeen;
        }
    }
    public int solution(int cacheSize, String[] cities) {
        if(cacheSize == 0) return 5 * cities.length;
        int answer = 0;
        List<Pair> cache = new ArrayList<>();
        for(int i = 0; i<cities.length; i++){
            int idx = cache.indexOf(new Pair(cities[i], 0));
            //cache hit
            if(idx != -1){
                cache.get(idx).lastSeen = i;
                answer += 1;
            }
            //cache miss
            else{
                answer+=5;
                //cache full
                if(cache.size()>=cacheSize){
                    Collections.sort(cache);
                    cache.remove(cache.size()-1);
                    cache.add(new Pair(cities[i], i));
                }
                else{
                    cache.add(new Pair(cities[i], i));
                }
            }
        }
        return answer;
    }
}