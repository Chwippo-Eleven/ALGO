import java.util.*;

class Solution {
    public int solution(int[] nums) {
        int answer = 0;
        
        Set<Integer> types = new HashSet<>();
        
        for (int num : nums) {
            types.add(num);
        }
        
        return Math.min(nums.length / 2, types.size());
    }
}
