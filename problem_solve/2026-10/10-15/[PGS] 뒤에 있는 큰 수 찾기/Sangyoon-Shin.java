import java.util.*;

class Solution {
    public int[] solution(int[] numbers) {
        int[] res = new int[numbers.length];
        Arrays.fill(res, -1);

        ArrayDeque<Integer> st = new ArrayDeque<>();

        int max = 0;
        for (int i = 0; i < numbers.length; i++){
            if (st.isEmpty()){
                st.offerLast(i);
            } else {
                if (numbers[st.peekLast()] < numbers[i]){
                    while (!st.isEmpty() && numbers[st.peekLast()] < numbers[i]){
                        res[st.pollLast()] = numbers[i];
                    }
                }
                st.offerLast(i);
            }
        }
        return res;
    }
}