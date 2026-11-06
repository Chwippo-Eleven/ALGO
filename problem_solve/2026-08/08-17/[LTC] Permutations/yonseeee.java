class Solution {
    List<List<Integer>> result;
    public List<List<Integer>> permute(int[] nums) {
        
        result=new ArrayList<>();
        dfs(new ArrayList<>(), nums, new boolean[nums.length]);

        return result;
    }

    public void dfs(List<Integer> current, int[]nums, boolean[] visited){

        if(current.size()==nums.length){
            result.add(new ArrayList<>(current));
            return;
        }

        for(int i=0;i<nums.length;i++){
            if(!visited[i]){
                current.add(nums[i]);
                visited[i]=true;

                dfs(current, nums, visited);

                current.remove(current.size()-1);
                visited[i]=false;
            }
        }

    }
}
