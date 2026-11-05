import java.util.*;
class Solution {
    public int[] solution(int[] progresses, int[] speeds) {
        List<Integer> results = new ArrayList<>();
        Queue<Integer> q = new ArrayDeque<>();
        for(int i = 0; i<progresses.length; i++){
            int left = 100-progresses[i];
            int day = left/speeds[i];
            if(left % speeds[i] > 0) day+=1;
            q.offer(day);
        }
        int count;
        while(!q.isEmpty()){
            count = 1;
            int cur = q.poll();
            while(!q.isEmpty() && q.peek()<=cur){
                count++;
                q.poll();
            }
            results.add(count);
        }
        int[] answer = new int[results.size()];
        for(int i = 0; i<results.size(); i++){
            answer[i] = results.get(i);
        }
        return answer;
    }
}