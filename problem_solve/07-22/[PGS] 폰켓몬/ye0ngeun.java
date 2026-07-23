class Solution {
    public int solution(int[] numbers, int target) {
        return dfs(numbers, target, 0, 0);
    }
    
    public int dfs(int[] numbers, int target, int index, int total) {
        if (index == numbers.length) {
            if (total == target) {
                return 1;
            }
            return 0;
        }
        
        int plus = dfs(numbers, target, index + 1, total + numbers[index]);
        int minus = dfs(numbers, target, index + 1, total - numbers[index]);
        
        return plus + minus;
    }
}
