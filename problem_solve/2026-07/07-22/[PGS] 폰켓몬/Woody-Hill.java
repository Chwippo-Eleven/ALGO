import java.util.HashSet;
import java.util.Set;

class Solution {
    
    public int solution(int[] nums) {
        int n = nums.length;
        Set<Integer> pokedex = new HashSet<>();
        
        for (int pokemon : nums) {
            pokedex.add(pokemon);
        }
        
        int size = pokedex.size();
        
        return (size > n / 2) ? (n / 2) : size;
    }
}
