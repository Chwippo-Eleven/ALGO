import java.util.*;

class Solution {
    public int solution(int[] queue1, int[] queue2) {
        int answer = 0;
        int size = 0;
        
        Queue<Integer> q1 = new ArrayDeque<>();
        Queue<Integer> q2 = new ArrayDeque<>();
        
        size = 3 * (queue1.length + queue2.length);
        
        long sum1 = 0L, sum2 = 0L, total = 0L;
        
        for(int n : queue1){
            q1.offer(n);
            sum1 += n;
        }
        for(int n : queue2){
            q2.offer(n);
            sum2 += n;
        }
        total = sum1 + sum2;
        // System.out.println(sum1 + " " + sum2 + " " + total);
        
        // if(total % 2 == 1) return -1;
        
        while(size > 0){
            size--;
            int num = 0;
            // System.out.println(sum1 + " " + sum2);
            
            if(sum1 == sum2) break;
            else if(sum1 > sum2){
                num = q1.poll();
                q2.offer(num);
                
                sum1 -= num;
                sum2 += num;
            }else{
                num = q2.poll();
                q1.offer(num);
                
                sum2 -= num;
                sum1 += num;
            }
            
            answer++;
        }
        
        if(sum1 != sum2){
            answer = -1;
        }
        
        return answer;
    }
}