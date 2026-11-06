import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

class Solution {
    public List<List<Integer>> permute(int[] nums) {
        int n = nums.length;
        boolean[] visited = new boolean[n];
        Deque<Integer> stack = new ArrayDeque<>();
        List<List<Integer>> result = new ArrayList<>();
        perm(nums, visited, stack, result);
        return result;
    }
    
    private void perm(int[] arr, boolean[] visited, Deque<Integer> stack, List<List<Integer>> result) {
        int n = arr.length;
        
        if (stack.size() == n) {
            List<Integer> pick = new ArrayList<>(stack);
            result.add(pick);
            return;
        }
        
        for (int i = 0; i < n; i++) {
            if (visited[i]) continue;
            
            visited[i] = true;
            stack.push(arr[i]);
            perm(arr, visited, stack, result);
            visited[i] = false;
            stack.pop();
        }
        
        return;
    }
}
