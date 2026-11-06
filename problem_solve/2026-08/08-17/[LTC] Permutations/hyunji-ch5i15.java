import java.util.*;
class Solution {
    int n;
    List<List<Integer>> answer = new ArrayList<>();
    boolean[] visited;
    List<Integer> list = new ArrayList<>();
    int cur = 0;
    int pSize = 1;
    public List<List<Integer>> permute(int[] nums) {
        n = nums.length;
        visited = new boolean[n];
        // n! 크기 계산할 반복문 (n최대 5라 ㄱㅊ) 
        for (int i=n; i>0; i--) {
            pSize *= i;
        }
        // List<List<Integer>> 초기화 
        for (int i=0; i<pSize; i++) {
            answer.add(new ArrayList<>());
        }

        dfs(0, nums);
        return answer;
    }
    // 순열
    private void dfs(int depth, int[] nums) {
        // 종료조건
        if (depth == n) {
            if (list.size() == n) {
                answer.get(cur).addAll(list);
            }
            if (cur<pSize-1) cur++; 
            return;
        }

        for (int i=0; i<n; i++) {
            if (visited[i]) continue;
            list.add(nums[i]);
            visited[i] = true;
            dfs(depth+1, nums);
            visited[i] = false;
            list.remove(Integer.valueOf(nums[i]));
        }
    }
}