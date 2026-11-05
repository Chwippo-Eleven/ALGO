// 재준 제출
class Solution {
    List<List<Integer>> ans = new ArrayList<>(); // 정답용 모으기
    boolean[] vi;//방문 체크
    public List<List<Integer>> permute(int[] nums) {
        vi = new boolean[nums.length];
        
        List<Integer> arr = new ArrayList<>();// 하나씩 넣어줄 배열

        dfs(nums, arr);
        
        return ans;
    }

    private void dfs(int[] nums, List<Integer> arr) {
        // 숫자 선택 완 -> 답에 넣어주기
        if (arr.size() == nums.length) {
            ans.add(new ArrayList<>(arr));
            return;
        }

        // 숫자 넣어주기(방문 안 한 거)
        for (int i = 0; i < nums.length; i++) {
            if (vi[i]) {
                continue;
            }

            // 고르고
            vi[i] = true;
            arr.add(nums[i]);

            dfs(nums, arr);

            // 백트래킹
            arr.remove(arr.size() - 1);
            vi[i] = false;
        }
    }
}
