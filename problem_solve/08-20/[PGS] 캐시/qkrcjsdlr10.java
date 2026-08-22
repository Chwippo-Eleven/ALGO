import java.util.*;

class Solution {
    
    public int solution(int cacheSize, String[] cities) {
        int answer = 0;
        
        Deque<String> dq = new ArrayDeque<>();
        if(cacheSize == 0){
            return cities.length * 5;
        }
        
        for (String cur : cities) {
            cur = cur.toUpperCase();

            if (dq.contains(cur)) {
                int size = dq.size();

                for (int i = 0; i < size; i++) {
                    String temp = dq.pollFirst();

                    if (!temp.equals(cur)) {
                        dq.addLast(temp);
                    }
                }

                dq.addLast(cur);
                answer += 1;
            }else {
                if (dq.size() == cacheSize) {
                    dq.pollFirst();
                }

                dq.addLast(cur);
                answer += 5;
            }
        }
        
        return answer;
    }
}