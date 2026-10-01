import java.util.*;

class Solution {
    public int[] solution(int[] progresses, int[] speeds) {
        int[] answer = {};
        int n= progresses.length;
        
        
        Queue<Integer> queue = new ArrayDeque<>();
        
        for(int i=0;i<n;i++){
            int day=(int)(Math.ceil((double)(100-progresses[i])/speeds[i]));
            // System.out.println(day);
            
            queue.offer(day);
        }
        
        
        List<Integer>tmp=new ArrayList<>();
        while(!queue.isEmpty()){
            int criterion=queue.poll();
            int cnt=1;
            while(!queue.isEmpty()&&criterion>=queue.peek()){
                queue.poll();
                cnt++;
            }
            tmp.add(cnt);
        }
        
        answer=new int[tmp.size()];
        for(int i=0;i<tmp.size();i++){
            answer[i]=tmp.get(i);
        }
        return answer;
    }
}
