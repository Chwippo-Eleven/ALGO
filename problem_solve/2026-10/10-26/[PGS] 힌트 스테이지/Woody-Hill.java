import java.util.ArrayDeque;
import java.util.Queue;

class Solution {
    
    private static class State {
        int totalCost;
        int currStage;
        int[] hintCount;
        
        State(int totalCost, int currStage, int[] hintCount) {
            this.totalCost = totalCost;
            this.currStage = currStage;
            this.hintCount = hintCount;
        }
    }
    
    public int solution(int[][] cost, int[][] hint) {
        
        int n = cost.length;
        int k = hint[0].length - 1;
        
        // BFS-Like
        int minCost = Integer.MAX_VALUE;
        
        Queue<State> queue = new ArrayDeque<>();
        queue.offer(new State(0, 0, new int[n]));
        
        while (!queue.isEmpty()) {
            State s = queue.poll();
            
            int totalCost = s.totalCost;
            int currStage = s.currStage;
            int[] hintCount = s.hintCount;
            
            // 힌트 사용 최대 개수를 초과할 가능성이 있음
            if (hintCount[currStage] >= n) {
                hintCount[currStage] = n - 1;
            }
            
            totalCost += cost[currStage][hintCount[currStage]];
            
            int nextStage = currStage + 1;
            
            if (nextStage == n) {
                minCost = Math.min(minCost, totalCost);
                continue;
            }
            
            // 힌트 구매 없이 다음으로
            queue.offer(new State(totalCost, nextStage, hintCount.clone()));
            
            // 힌트 구매하기
            totalCost += hint[currStage][0];
            
            for (int i = 1; i <= k; i++) {
                int hintStage = hint[currStage][i] - 1;
                hintCount[hintStage] += 1;
            }
            
            queue.offer(new State(totalCost, nextStage, hintCount.clone()));
        }
        
        return minCost;
    }
}
