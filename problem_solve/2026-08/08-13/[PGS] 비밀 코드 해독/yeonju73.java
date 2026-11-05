import java.util.*;

// 백트래킹 + 조합
// 조합 - 이미 선택한 숫자보다 큰 숫자만 다음에 선택하면 됨
// 모든 시도 후에 사이즈 5인 경우,
// 각 q를 순회하면서 개수만큼 일치하는지 확인
// 모든 q에서 만족하면 정답 count +1

class Solution {
    int count = 0;
    
    public int solution(int n, int[][] q, int[] ans) {
        // 시작점이 될 수 있는 수 선정
        for(int i = 1; i <= n-4; i++) {
            int[] code = new int[5];
            code[0] = i;
            
            dfs(code, n, i, 1, q, ans);
        }
        return count;
    }
    
    public void dfs(int[] code, int n, int maxValue, int size, int[][] q, int[] ans) {
        if(size == 5){
            // 모든 p를 만족하는지 체크
            for(int i = 0; i < q.length; i++){
                int[] current = q[i];
                int currentAns = ans[i];
                
                // 일치 개수 count
                for(int cod: current) {
                    for(int c: code) {
                        // 일치하면
                        if(c == cod){
                            currentAns--;
                        }
                        // 앞으로 더 봐도 가망이 없음
                        else if(c > cod){
                            continue;
                        }
                    }
                }
                // 이번 턴의 일치 개수가 일치하지 않으면 
                if(currentAns != 0) {
                    return;
                }
            }
            count++;
            return;
        }
        
        // 더 큰 수를 조합에 추가해서 dfs
        for(int i = maxValue+1; i <= n; i++){
            code[size] = i;
            dfs(code, n, i, size+1, q, ans);
        }
    }
}
