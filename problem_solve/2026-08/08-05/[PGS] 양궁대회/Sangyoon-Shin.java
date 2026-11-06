import java.util.*;

class Solution {
    static int res;
    static int[] resArr;
    public int[] solution(int n, int[] info) {
        int[] l = new int[11];
        res = Integer.MIN_VALUE;
        resArr = new int[11];

        dfs(n, info, l, 0, 0, 0);

        if (res == Integer.MIN_VALUE){
            return new int[] {-1};
        }
        return resArr;
    }
    public static void dfs(int cnt, int[] a, int[] l, int round, int ascore, int lscore){ // 남은 화살 수, 어피치 점수판, 라이언 점수판, 몇 번째 과녁, 어치피 점수, 라이언 점수
        if (round == 10){
            l[10] = cnt;
            int diff = lscore - ascore; // 라이언 최대 점수 구하는게 아니라, 차이의 최대값 구하는거였다......
            if (diff > 0){ // 라이언 점수가 무조건 커야, 라이언 승리
                if (diff > res){
                    res = diff;
                    resArr = l.clone();
                } else if (diff == res && update(l, resArr)){ // 차이가 같은게 여러개면, 낮은 점수를 많이 맞힌 경우를 따져야함.
                    resArr = l.clone();
                }
            }
            return;
        }

        int score = 10 - round;
        int toWin = a[round] + 1; // 라이언이 이기기 위해 필요한 최소 화살 개수

        // 라이언이 이기는 경우
        if (cnt >= toWin){
            l[round] = toWin;
            dfs(cnt - toWin, a, l, round + 1, ascore, lscore + score);
            l[round] = 0;
        }

        // 라이언이 지는 경우
        int aWin = ascore;
        if (a[round] > 0){
            aWin += score;
        }
        dfs(cnt, a, l, round + 1, aWin, lscore);

    }
    public static boolean update(int[] cur, int[] prev){
        for (int i = 10; i >= 0; i--){
            if (cur[i] != prev[i]){
                return cur[i] > prev[i];
            }
        }
        return false;
    }
}