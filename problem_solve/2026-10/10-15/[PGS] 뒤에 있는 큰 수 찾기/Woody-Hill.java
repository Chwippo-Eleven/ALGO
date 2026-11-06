import java.util.ArrayDeque;
import java.util.Deque;

class Solution {
    
    static class State {
        int value;
        int index;
        
        State(int value, int index) {
            this.value = value;
            this.index = index;
        }
    }
    
    public int[] solution(int[] numbers) {
        
        int n = numbers.length;
        int[] result = new int[n];
        
        Deque<State> stack = new ArrayDeque<>();
        
        for (int i = 0; i < n; i++) {
            int num = numbers[i];
            
            while (!stack.isEmpty()) {
                State top = stack.peek();
                
                if (num <= top.value) break;
                
                result[top.index] = num;
                stack.pop();
            }
            
            stack.push(new State(num, i));
        }
        
        while (!stack.isEmpty()) {
            State top = stack.pop();
            result[top.index] = -1;
        }
        
        return result;
    }
}
