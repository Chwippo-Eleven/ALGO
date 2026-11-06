class Solution {
    public int lengthOfLIS(int[] nums) {
        int[] tails = new int[nums.length];
        int len = 0;

        for (int num : nums) {
            int left = 0;
            int right = len;

            while (left < right) {
                int mid = (left + right) / 2;

                if (tails[mid] < num) {
                    left = mid + 1;
                } else {
                    right = mid;
                }
            }

            tails[left] = num;

            if (left == len) {
                len++;
            }
        }

        return len;
    }
}