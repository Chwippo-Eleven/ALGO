class Solution {
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        makePermute(result, nums, new int[nums.length], 0, new boolean[nums.length]);
        return result;
    }
    public void makePermute(List<List<Integer>> result, int[] nums, int[] arr, int idx, boolean[] visited){
        if(idx == nums.length){
            List<Integer> tmp = new ArrayList<>();
            for(int val : arr){
                tmp.add(val);
            }
            result.add(tmp);
            return;
        }

        for(int i = 0; i<arr.length; i++){
            if(!visited[i]){
                visited[i] = true;
                arr[idx++] = nums[i];
                makePermute(result, nums, arr, idx, visited);
                idx--;
                visited[i] = false;
            }
        }
    }
}