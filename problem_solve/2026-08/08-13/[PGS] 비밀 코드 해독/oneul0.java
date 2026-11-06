import java.util.*;
class Solution {
    int n, m;
    int[] ans;
    long[] question;
    Set<Long> visited = new HashSet<>();
    public int solution(int n, int[][] q, int[] ans) {
        this.n = n;
        this.ans = ans;
        this.m = q.length;
        this.question = new long[m];
        
        for(int i = 0; i<m; i++){
            long mask = 0L;
            for(int t : q[i]){
                mask = turnOn(mask, t);
            }
            question[i] = mask;
        }
        
        return comb(0L, 0);
    }
    
    public int comb(long mask, int cur){
        int result = 0;
        if(cur == 5){
            if(visited.contains(mask)) return 0;
            visited.add(mask);
            //chk
            return chk(mask);
        }
        //make comb
        for(int i = 1; i<=n; i++){
            if(isOn(mask, i)) continue;
            result += comb(turnOn(mask, i), cur+1);
        }
        return result;
    }
    
    public int chk(long candidate){
        for(int i = 0; i<m; i++){
            //q와 비교해서
            //ans와 다르면 컷
            int cnt = Long.bitCount(candidate & question[i]);
            if(ans[i] != cnt) return 0;
        }
        return 1;
    }
    
    public boolean isOn(long mask, int idx) {
        return (mask & (1L << idx)) != 0;
    }

    public long turnOn(long mask, int idx) {
        return mask | (1L << idx);
    }

    public long turnOff(long mask, int idx) {
        return mask & ~(1L << idx);
    }
}
//q의 조합과 생성한 조합을 비교했을 때 ans(응답 결과)와 동일하다면 정답이 될 수 있는 것