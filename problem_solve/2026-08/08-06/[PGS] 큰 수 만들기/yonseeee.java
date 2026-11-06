import java.util.*;

class Solution {
    public String solution(String number, int k) {
        String answer = "";
        
        Deque<Integer> stack = new ArrayDeque<>();
        
        for(int i=0;i<number.length();i++){
            int num=number.charAt(i)-'0';
            
            //스택에서 현재 숫자보다 작은 애들 k개 제거
            while(!stack.isEmpty()&&k>0&&stack.peekLast()<num){
                stack.pollLast();
                k--;
            }
            stack.offerLast(num);
        
        }
        
        //내림차순인 경우 스택에 그대로 쌓이기 때문에 마지막 k개 제거
        while(k>0){
            stack.pollLast();
            k--;
        }

        for(int value:stack){
            answer+=value;
        }
        return answer;
    }
}
