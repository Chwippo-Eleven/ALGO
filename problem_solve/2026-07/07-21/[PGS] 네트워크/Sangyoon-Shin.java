import java.io.*;
import java.util.*;

class Solution {
    static boolean visited[];
    public int solution(int n, int[][] computers) {
        int cnt = 0;
        visited = new boolean[n];

        for (int i = 0; i < n; i++){
            if (!visited[i]){
                cnt++;
                dfs(i, n, computers);
            }
        }
        return cnt;
    }
    public void dfs(int idx, int n, int[][] c){ // 현재 idx
        visited[idx] = true;

        // [idx][0 ~ n] 까지 중에 행, 열이 같지 않은 애 중에서 값이 1인 곳으로 분기?
        for (int i = 0; i < n; i++){
            if (c[idx][i] == 1 && idx != i && !visited[i]){
                dfs(i, n, c);
            }
        }

    }
}