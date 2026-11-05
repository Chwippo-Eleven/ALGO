import java.util.*;

class Solution {
    public int solution(int cacheSize, String[] cities) {
        // 근데, 큐에 들어있던 애면 가장 최근에 사용한 것으로 상태 업데이트 해줘야함.
        ArrayDeque<String> q = new ArrayDeque<>(); // 오래된 애 관리
        Set<String> set = new HashSet<>(); // 큐에 들어있는

        int time = 0;
        for (int i = 0; i < cities.length; i++){
            String cur = cities[i].toLowerCase();

            if (set.contains(cur)){ // 1. 큐에 들어있는 애라면, +1초
                q.remove(cur);
                q.addLast(cur);
                time++;
            } else { // 2. 큐에 들어있지 않던 경우면, +5초
                time += 5;
                if (q.size() < cacheSize){ // 2-1. 넣을 공간이 있는 경우
                    q.addLast(cur);
                    set.add(cur);
                } else if (q.size() >= cacheSize && !q.isEmpty()){ // 2-2. 남은 공간이 없는 경우
                    String del = q.removeFirst();
                    set.remove(del);

                    set.add(cur);
                    q.addLast(cur);
                }
            }
        }
        return time;
    }
}