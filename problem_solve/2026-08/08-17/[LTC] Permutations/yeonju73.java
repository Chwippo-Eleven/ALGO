import java.util.*;

class Solution {
    List<List<Integer>> answer = new ArrayList<>();

    public List<List<Integer>> permute(int[] nums) {
        int n = nums.length;
        boolean[] visited = new boolean[n];

        dfs(new ArrayList<>(), nums, n, visited);

        return answer;
    }
    
    public void dfs(List<Integer> arr, int[] nums, int n, boolean[] visited){
        if(arr.size() == n){
            answer.add(new ArrayList<>(arr));
            return;
        }
        for(int i = 0; i < n; i++) {
            if(!visited[i]) {
                visited[i] = true;

                arr.add(nums[i]);
                dfs(arr, nums, n, visited);

                arr.remove(arr.size()-1);
                visited[i] = false;
            }
        }
    }
}
