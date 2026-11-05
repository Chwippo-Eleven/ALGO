import java.util.Stack;

class Solution {
    class State {
        int sum;
        int idx;
        
        State(int sum, int idx) {
            this.sum = sum;
            this.idx = idx;
        }
    }
    
    public int solution(int[] numbers, int target) {
        int n = numbers.length;
        
        Stack<State> stack = new Stack<>();
        stack.push(new State(0, 0));
        
        int answer = 0;
        
        while (!stack.isEmpty()) {
            State current = stack.pop();
            int currSum = current.sum;
            int currIdx = current.idx;
            
            if (currIdx == n) {
                if (currSum == target) {
                    answer += 1;
                }
                continue;
            }
            
            stack.push(new State(currSum + numbers[currIdx], currIdx + 1));
            stack.push(new State(currSum - numbers[currIdx], currIdx + 1));
        }
        
        return answer;
    }
}
