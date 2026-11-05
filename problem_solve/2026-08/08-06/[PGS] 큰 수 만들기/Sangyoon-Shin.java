import java.util.*;

class Solution {
    public String solution(String number, int k) {

        ArrayDeque<Integer> st = new ArrayDeque<>();

        int len = number.length() - k; // len 만큼은 채워야된다

        for (int i = 0; i < number.length(); i++){
            int cur = number.charAt(i) - '0';
            int remain = number.length() - i - 1; // 뒤에서 더 확인할 수 있는 기회

            while (!st.isEmpty() && cur > st.peekLast() && len - st.size() <= remain){
                // 맨 앞자리 수를 빼도, 현재값을 넣을거니까 remain이랑 겹치는 경계일때도 빼는게 가능
                st.removeLast();
            }
            if (st.size() < len){
                st.addLast(cur);
            }
        }
        StringBuilder sb = new StringBuilder();
        while (!st.isEmpty()){
            sb.append(st.removeLast());
        }
        return sb.reverse().toString();
    }
}