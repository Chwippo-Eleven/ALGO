class Solution {
    static int answer = 0;
    
    public int solution(int[] numbers, int target) {       
        dfs(numbers.length, numbers, target, 0, 0);
        return answer;
    }
    
    public static void dfs(int n, int[] numbers, int target, int sum, int count){
        if (count == n){
            if (sum == target)
                answer++;
            return;
        }
        dfs(n, numbers, target, sum+numbers[count], count+1);
        dfs(n, numbers, target, sum-numbers[count], count+1);
    }
}
