import java.util.*;

class Solution {
    public int solution(int[] queue1, int[] queue2) {
        int answer = 0;
        
        Queue<Integer> q1 = new ArrayDeque<>();
        Queue<Integer> q2 = new ArrayDeque<>();
        
        long sum1=0, sum2=0;
        for(int value:queue1){
            sum1+=value;
            q1.offer(value);
        }
        for(int value:queue2){
            sum2+=value;
            q2.offer(value);
        }
        
        long half = (sum1+sum2)/2;
        

        while(sum1!=sum2){
            if(sum1<half){
                int elem=q2.poll();
                q1.offer(elem);
                
                sum1+=elem; sum2-=elem;
            }else if(sum2<half){
                int elem=q1.poll();
                q2.offer(elem);
                
                sum1-=elem; sum2+=elem;
            }
            answer++;
            if(answer>queue1.length*4){
                return -1;
            }
        }
        
        return answer;
    }
}
