class Solution {
    class Entry {
        String city;
        int requestTime;
        
        Entry(String city, int requestTime) {
            this.city = city;
            this.requestTime = requestTime;
        }
    }
    
    public int solution(int cacheSize, String[] cities) {

        // 예외 케이스 처리
        if (cacheSize == 0) {
            return cities.length * 5;
        }
        
        Entry[] cache = new Entry[cacheSize];
        
        int currSize = 0;
        int currTime = 0;
        
        OuterLoop:
        for (String city : cities) {
            city = city.toLowerCase();
            
            // Cache Hit
            for (Entry e : cache) {
                if (e != null && city.equals(e.city)) {
                    e.requestTime = currTime;
                    currTime += 1;
                    continue OuterLoop;
                }
            }
            
            // Cache Miss
            if (currSize < cacheSize) {
                cache[currSize++] = new Entry(city, currTime);
            } else {
                int removeIndex = 0;
                for (int i = 1; i < cacheSize; i++) {
                    if (cache[i].requestTime < cache[removeIndex].requestTime) {
                        removeIndex = i;
                    }
                }
                cache[removeIndex] = new Entry(city, currTime);
            }
            currTime += 5;
        }
        
        return currTime;
    }
}
