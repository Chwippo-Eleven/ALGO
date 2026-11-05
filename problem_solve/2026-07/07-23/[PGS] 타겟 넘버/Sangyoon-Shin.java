import java.io.*;
import java.util.*;

class Solution {
    static int cnt;
    public int solution(int[] numbers, int target) {
        cnt = 0;
        dfs(0, 0, numbers, target);
        return cnt;
    }
    public void dfs(int idx, int sum, int[] arr, int t){
        if (idx == arr.length){
            if (sum == t){
                cnt++;
            }
            return;
        }
        // 더하거나 빼거나 2가지. 2^20
        dfs(idx + 1, sum + arr[idx], arr, t);
        dfs(idx + 1, sum - arr[idx], arr, t);
    }
}
