import java.io.*;
import java.util.*;

class Solution {
    public int solution(int[] nums) {
        Set<Integer> set = new HashSet<>();

        for (int i = 0; i < nums.length; i++){
            if (!set.contains(nums[i])){
                set.add(nums[i]);
            }
        }


        int res = 0;
        if (set.size() >= nums.length / 2){
            res = nums.length / 2;
        } else {
            res = set.size();
        }
        return res;
    }
}
