import java.util.*;
class Solution {
    Set<Integer> visited = new HashSet<>();
    int n, result = 0, MAX = 10_000_001;
    int[] nums;
    boolean[] notPrime = new boolean[MAX];
    public int solution(String numbers) {
        notPrime[0] = notPrime[1] = true;
        for(int i = 2; i<MAX; i++){
            if(notPrime[i]) continue;
            for(int j = i+i; j<MAX; j+=i){
                notPrime[j] = true;
            }
        }
        n = numbers.length();
        nums = new int[n];
        for(int i = 0; i<n; i++){
            nums[i] = numbers.charAt(i)-'0';
        }
        permute(new StringBuilder(), 0, 0);
        return result;
    }
    public void permute(StringBuilder sb, int idx, int mask){
        if(!sb.isEmpty()){
            int num = Integer.parseInt(sb.toString());
            if(!notPrime[num] && !visited.contains(num)) {
                result++;
                visited.add(num);
            }    
        }
        
        if(idx == n) return ;
        for(int i = 0; i<n; i++){
            if((mask & (1<<i)) == 0){
                sb.append(nums[i]);
                permute(sb, idx+1, mask|(1<<i));
                sb.deleteCharAt(sb.length()-1);
            }
        }
    }
}