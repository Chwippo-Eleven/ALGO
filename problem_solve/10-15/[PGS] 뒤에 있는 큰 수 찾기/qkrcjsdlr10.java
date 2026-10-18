import java.util.*;

class Solution {
    public int[] solution(int[] numbers) {
        int n = numbers.length;
        int[] answer = new int[n];
        Arrays.fill(answer, -1);

        ArrayDeque<Integer> st = new ArrayDeque<>();

        for(int i = 0; i < n; i++){
            while(!st.isEmpty() && numbers[st.peekLast()] < numbers[i]){
                int num = st.pollLast();
                answer[num] = numbers[i];
            }
            st.addLast(i);
        }

        return answer;
    }
}