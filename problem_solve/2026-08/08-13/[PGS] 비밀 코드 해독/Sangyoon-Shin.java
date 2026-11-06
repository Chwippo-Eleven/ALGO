import java.util.*;

class Solution {
    static int res;
    static boolean[] num;
    static int[][] cand;
    static int[] answer;
    public int solution(int n, int[][] q, int[] ans) {

        num = new boolean[n + 1];
        cand = q;
        answer = ans;
        res = 0;

        makeCode(1, 0, n);
        return res;
    }
    public void makeCode(int cur, int depth, int n){ // 조합으로 5자리 다 만들어보기
        if (depth == 5){
            if (isPossible()){
                res++;
            }
            return;
        }
        for (int i = cur; i <= n; i++){
            num[i] = true;
            makeCode(i + 1, depth + 1, n);
            num[i] = false;
        }
    }
    public boolean isPossible(){ // 내가 만든 조합이 q에 있는 후보들의 정보와 모두 일치하는지 검사
        for (int i = 0; i < cand.length; i++){
            int cnt = 0;

            for (int j = 0; j < 5; j++){
                if (num[cand[i][j]]){
                    cnt++;
                }
            }
            if (cnt != answer[i]){
                return false;
            }
        }
        return true;
    }
}