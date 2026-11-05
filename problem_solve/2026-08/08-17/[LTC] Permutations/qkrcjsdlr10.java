class Solution {

    static List<List<Integer>>answer;
    static boolean[] vis;

    public static void dfs(int depth, int n, List<Integer> arr, int[] nums){
        if(depth == n){
            answer.add(new ArrayList<>(arr));

            return;
        }

        for(int i = 0; i < n; i++){
            if(vis[i] == true) continue;
            arr.add(nums[i]);
            vis[i] = true;
            dfs(depth + 1, n, arr, nums);
            arr.remove(arr.size() - 1);
            vis[i] = false;
        }
    }

    public List<List<Integer>> permute(int[] nums) {
        int n = nums.length;
        answer = new ArrayList<>();
        vis = new boolean[n + 1];

        dfs(0, n, new ArrayList<>(), nums);

        return answer;
    }
}