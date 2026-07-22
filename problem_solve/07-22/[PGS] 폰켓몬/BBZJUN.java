import java.util.*;

class Solution {
    public int solution(int[] nums) {
        int pick = nums.length / 2; // N/2 마리
        Set<Integer> set = new HashSet<>(); // 중복 제거용 set

        for (int n : nums) { //중복 제거
            set.add(n);
        }
        
        // 내가 고르기로 한거 > 중복제거한 폰켓몬 -> 중복제거한 폰켓몬 수 (내가 고르기로 한거를 하면 중복되는게 들어가서 고를 수 있는건 중복제거한 수만큼)
        // 내가 고르기로 한거 < 중복제거한 폰켓몬 -> 내가 고르기로 한거 (내가 고르기로 한 만큼만 최대로 가져오는게 조건)
        return Math.min(set.size(), pick);
    }
}
