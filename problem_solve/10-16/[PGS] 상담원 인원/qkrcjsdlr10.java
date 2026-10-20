import java.util.*;

class Solution {

    int res;

    public void dfs(int depth, int sum, int k, int n, List<Integer> arr, int[][] reqs){
        if(depth == k - 1){
            arr.add(n - sum);
            for(int x : arr){
                System.out.print(x + " ");
            }
            time(arr, reqs, k);
            arr.remove(arr.size() - 1);
            System.out.println();
            return;
        }

        for(int i = 1; i <= n - sum - (k - depth - 1); i++){
            arr.add(i);
            dfs(depth + 1, sum + i, k, n, arr, reqs);
            arr.remove(arr.size() - 1);
        }
    }

    public void time(List<Integer> arr, int[][] reqs, int k){
        int[][] endTimes = new int[k][];
        for (int i = 0; i < k; i++) {
            endTimes[i] = new int[arr.get(i)];
        }
        int sum = 0;

        for(int[] req : reqs){
            int[] cur = endTimes[req[2] - 1];
            int mn = Integer.MAX_VALUE;
            int idx = 0;
            boolean check = false;
            for(int i = 0; i < cur.length; i++){
                int time = cur[i];
                if(time <= req[0]){
                    endTimes[req[2] - 1][i] = req[0] + req[1];
                    check = true;
                    break;
                }else{
                    if(mn > time){
                        mn = time;
                        idx = i;
                    }
                }
            }
            if(!check){
                int temp = endTimes[req[2] - 1][idx] - req[0];
                sum += temp;
                endTimes[req[2] - 1][idx] += req[1];
            }

        }

        res = Math.min(res, sum);
    }

    public int solution(int k, int n, int[][] reqs) {
        int answer = 0;
        res = Integer.MAX_VALUE;
        Arrays.sort(reqs, (a, b) -> a[0] - b[0]);
        dfs(0, 0, k, n, new ArrayList<>(), reqs);
        answer = res;
        return answer;
    }
}