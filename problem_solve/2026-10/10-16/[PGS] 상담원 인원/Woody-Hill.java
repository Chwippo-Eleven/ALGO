import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

class Solution {
    public int solution(int k, int n, int[][] reqs) {
        
        // waitingTime[x][y]: x 유형의 상담에 y명을 배치했을 때 대기 시간
        // 1-based index 형태로 사용하는 것에 유의!
        int[][] waitingTime = new int[k + 1][n - k + 2];
        
        for (int type = 1; type <= k; type++) {
            for (int mentor = 1; mentor <= n - k + 1; mentor++) {
                // type + mentor 수 조합의 대기 시간
                int totalWaitTime = 0;
                
                // 상담 테이블 (종료 예정 시간 기록)
                PriorityQueue<Integer> pq = new PriorityQueue<>();
                
                for (int i = 0; i < mentor; i++) {
                    pq.offer(0);    // mentor 인원 수만큼 투입
                }
                
                
                for (int[] req : reqs) {
                    if (req[2] != type) continue;
                    
                    int a = req[0]; // 상담 시작 시간
                    int b = req[1]; // 요구 상담 시간
                    
                    int closestEndTime = pq.poll();
                    int waitTimeNeeded = Math.max(closestEndTime - a, 0);
                    
                    totalWaitTime += waitTimeNeeded;
                    
                    // 해당 멘토의 다음 상담 시간: (시작 + 대기 + 상담)
                    pq.offer(a + waitTimeNeeded + b);
                }
                
                waitingTime[type][mentor] = totalWaitTime;
            }
        }
        
        int answer = Integer.MAX_VALUE;
        
        List<int[]> partitions = partition(k, n);
        
        for (int[] div : partitions) {
            int divWaitTime = 0;
            
            for (int type = 1; type <= k; type++) {
                int mentor = div[type - 1]; // 해당 타입의 멘토 수 (0-based)
                divWaitTime += waitingTime[type][mentor];
            }
            
            answer = Math.min(answer, divWaitTime);
        }
        
        return answer;
    }
    
    // n을 k개의 자연수로 나누는 모든 방법을 리스트에 담아 반환
    private List<int[]> partition(int k, int n) {
        
        List<int[]> res = new ArrayList<>();
        int[] pick = new int[k];
        
        backtrack(0, n, pick, res);
        
        return res;
    }
    
    // 백트래킹으로 n을 k개의 자연수로 나누기
    private void backtrack(int idx, int rem, int[] pick, List<int[]> res) {
        
        // 채워야 할 칸 개수
        int slotsLeft = pick.length - idx;
        
        // 남은 칸이 1개면 값 자동 확정
        if (slotsLeft == 1) {
            pick[idx] = rem;    
            res.add(pick.clone());
            return;
        }
        
        // 남은 자리에 최소 1씩 보장하기
        int upperBound = rem - (slotsLeft - 1);
        
        // 모든 경우를 재귀적으로 탐색
        for (int i = 1; i <= upperBound; i++) {
            pick[idx] = i;
            backtrack(idx + 1, rem - i, pick, res);
        }
    }
}
