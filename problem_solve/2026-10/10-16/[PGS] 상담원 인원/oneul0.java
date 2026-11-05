import java.util.*;
class Solution {
    static class Node {
        int start;
        int time;
        Node(int start, int time) {
            this.start = start;
            this.time = time;
        }
    }
    
    public int solution(int k, int n, int[][] reqs) {
        List<List<Node>> list = new ArrayList<>();
        for(int i = 0; i <= n; i++) {
            list.add(new ArrayList<>());
        }
        
        for(int[] r : reqs) {
            list.get(r[2]).add(new Node(r[0],r[1]));
        }
        
        int[][] sumWait = new int[k+1][n-k+2];
        
        for(int i = 1; i <= k; i++) {     
            for(int j = 1; j <= n-k+1; j++) {
                PriorityQueue<Integer> pq = new PriorityQueue<>();
                for(Node each : list.get(i)) {
                    if(pq.size() < j) {
                        pq.add(each.start + each.time);
                    }
                    else {
                        int min = pq.remove();
                        int wait = min - each.start;
                        if(wait > 0) {
                            sumWait[i][j] += wait;
                            pq.add(min+each.time);
                        }
                        else {
                            pq.add(each.start + each.time);
                        }
                    }
                }
            }
        }
        int answer = dfs(n-k+1, sumWait, 1, k);
        return answer;
    }
    
    static int dfs(int left, int[][] wait, int depth, int k) {
        if(depth > k) return 0;
        int sum = Integer.MAX_VALUE;
        for(int i = 1; i <= left; i++) {
            sum = Math.min(dfs(left-i+1, wait, depth+1, k)+wait[depth][i], sum);
        }
        return sum;
    }
}