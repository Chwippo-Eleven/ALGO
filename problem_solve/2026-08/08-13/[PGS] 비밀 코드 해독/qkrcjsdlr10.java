import java.util.*;

class Solution {
    public static int answer = 0;
    
    public static boolean checkAnswer(ArrayList<Integer> arr, int[][] q, int[] ans){
        for(int k = 0; k < q.length; k++){
            int cnt = 0;

            for(int i = 0; i < 5; i++){
                for(int j = 0; j < 5; j++){
                    if(q[k][i] == arr.get(j)){
                        cnt++;
                    }       
                }
            }
            if(cnt != ans[k]){
                return false;
            }
        }
        return true;
    }
    
    public static void dfs(int depth, int n, int start, ArrayList<Integer> arr, int[][] q, int[] ans){
        if(depth == 5){
            boolean check = checkAnswer(arr, q, ans);
            if(check == true){
                answer++;    
            }
            return;
        }
        
        for(int i = start; i <= n; i++){
            arr.add(i);
            dfs(depth + 1, n, i+1, arr, q, ans);
            arr.remove(arr.size() - 1);
        }
    }
    
    
    
    public int solution(int n, int[][] q, int[] ans) {
        
        
        dfs(0, n, 1, new ArrayList<Integer>(), q, ans);
        
        return answer;
    }
}