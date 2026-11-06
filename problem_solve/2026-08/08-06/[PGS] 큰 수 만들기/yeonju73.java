import java.util.*;
/*
    숫자는 왼쪽의 값이 클 수록 큼
    내 바로 뒤에 있는 숫자보다 내가 작으면 나를 지움
    stack으로 처음부터 넣는다
    뒤에 넣을 숫자가 stack top 보다 크면 stack pop
    pop은 총 k 번 하기
    9876 같이 역으로 정렬되어 있는 숫자는 이 방식이 안되니까
    지운 값이 없을 경우 뒤에서 부터 k 개 잘라줘야 한다.
*/
class Solution {
    public String solution(String number, int k) {
        StringBuilder sb = new StringBuilder();
        ArrayDeque<Character> stack = new ArrayDeque<>();
        
        for(char c: number.toCharArray()){
            if(stack.isEmpty() || k == 0){
                stack.push(c);
                
            } else {
                // stack이 비어있지 않고 peek 가 작으며, 더 빼야할 수가 남아있을 때 계속 반복
                while (!stack.isEmpty() && stack.peek() < c && k>0){
                    stack.pop();
                    k--;
                } 
                stack.push(c);
                
            }
        }
        
        while (k > 0){
            stack.pop();
            k--;
        }
        
        for(char c: stack){
            sb.append(c);
        }
        
        return sb.reverse().toString();
    }
}
