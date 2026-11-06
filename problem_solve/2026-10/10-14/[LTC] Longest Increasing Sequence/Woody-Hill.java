class Solution {
    public int lengthOfLIS(int[] nums) {
        
        int len = 0;
        int[] dp = new int[nums.length];

        dp[len++] = nums[0];

        for (int i = 1; i < nums.length; i++) {
            int num = nums[i];
            
            if (num > dp[len - 1]) {
                dp[len++] = num;
            } else {
                // Binary Search
                int left  = 0;
                int right = len;

                while (left < right) {
                    int mid = (left + right) / 2;

                    if (dp[mid] >= num) {
                        right = mid;
                    } else {
                        left  = mid + 1;
                    }
                }

                dp[left] = num;
            }
        }

        return len;
    }
}
