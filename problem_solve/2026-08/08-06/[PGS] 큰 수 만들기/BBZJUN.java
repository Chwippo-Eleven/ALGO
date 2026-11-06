import java.util.*;

class Solution {
    public String solution(String number, int k) {
        String answer = "";
        StringBuilder sb = new StringBuilder();
        Deque<Character> dq = new ArrayDeque<>();
        
        for (char ch : number.toCharArray()){ //하나씩 보면서
            if (dq.isEmpty()){ // 비었으면 채워주고
                dq.addLast(ch);
            }
            else{ // 만약 값이 있다면
                while (!dq.isEmpty() && dq.peekLast() < ch && k>0){ // 지금 들어있는게 다음에 들어오는거보다 작으면. 들어있는것을 지워줌 계속. 만약 값이 없거나 들어있는게 더 크면 종료
                    dq.removeLast();
                    k--; // 지웠으니까 카운트--
                }
                dq.addLast(ch); //넣어주기
            }
        }
        
        //k가 남았으면 뒤에서부터 없애주기
        while(k>0){
            dq.removeLast();
            k--;
        }
        
        //String으로 출력하기 용
        for (char ch : dq){
            sb.append(ch);
        }
        answer = sb.toString();
        
        return answer;
    }
}
