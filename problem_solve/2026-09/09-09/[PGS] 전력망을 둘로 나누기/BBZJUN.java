import java.util.*;

class Solution {
    public int solution(int n, int[][] wires) {
        int answer = Integer.MAX_VALUE;
        
        boolean[] vi = new boolean[n+1];
        
        int[][] arr = new int[n+1][n+1];
        for (int[] w : wires){
            arr[w[0]][w[1]] = 1;
            arr[w[1]][w[0]] = 1;
        }
        
        int A = 0;
        int B = 0;
        for (int[] w : wires){
            arr[w[0]][w[1]] = 0;
            arr[w[1]][w[0]] = 0;
            int tmp = 0;
            vi = new boolean[n+1];
            for (int i=1; i<=n; i++){
                if (!vi[i] && tmp == 0){
                    A = TTT(i, n, vi, arr);
                    tmp = 1;
                }
                else if (!vi[i] && tmp == 1){
                    B = TTT(i, n, vi, arr);
                    break;
                }
            }
            answer = Math.min(answer, Math.abs(A-B));
            arr[w[0]][w[1]] = 1;
            arr[w[1]][w[0]] = 1;
        }
        return answer;
    }
    public static int TTT(int i, int n, boolean[] vi, int[][] arr){
        int count = 0;
        Deque<Integer> dq = new ArrayDeque<>();
        dq.add(i);
        vi[i] = true;
        while(!dq.isEmpty()){
            i = dq.poll();
            for (int k=1; k<=n; k++){
                if (!vi[k] && arr[i][k] == 1){
                    vi[k] = true;
                    count++;
                    dq.add(k);
                }
            }
        }

        
        return count;
    }
}
