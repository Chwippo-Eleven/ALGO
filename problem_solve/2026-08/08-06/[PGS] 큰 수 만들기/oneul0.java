import java.util.*;
class Solution {
    public String solution(String number, int k) {
        StringBuilder sb = new StringBuilder();
        sb.append(number.charAt(0));

        for(int i =1; i<number.length(); i++){
            //이전에 온 수가 지금 만난 수보다 더 작은 경우
            while(k > 0
                && sb.length()>0
                && sb.charAt(sb.length()-1) < number.charAt(i)){
                sb.deleteCharAt(sb.length()-1);
                k--;
            }
            sb.append(number.charAt(i));
        }
        if(k>0){
            sb.delete(sb.length()-k, sb.length());
        }
        return sb.toString();
    }
}
//단조감소스택으로 다음과 같이 구현
//이전의 값이 이후에 오는 값보다 작으면 pop
//