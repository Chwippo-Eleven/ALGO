class Solution {
    public long solution(int n, int[] times) {
        
        // 가능한 시간의 범위 : 1 ~ (10^18)
        long lowerBound = 0L;
        long upperBound = 1_000_000_000_000_000_000L;   // 10의 18승
        
        // lowerBound + 1 == upperBound 될 때까지 탐색
        while (lowerBound + 1 < upperBound) {
            long mid = (lowerBound + upperBound) / 2;
            long peoplePassed = countPassed(mid, times);
            
            if (peoplePassed < n) {
                lowerBound = mid;
            } else {
                upperBound = mid;
            }
        }
        
        // 1) lowerBound : n명 통과가 불가능한 시간
        // 2) upperBound : n명 통과가 가능한 시간
        // 3) lowerBound + 1 == upperBound
        // 그러므로, 현재 upperBound에 저장된 값이 최소 시간이다.
        long answer = upperBound;
        return answer;
    }
    
    // time 시간 동안 심사받을 수 있는 최대 사람 수 반환
    private long countPassed(long time, int[] evalTimes) {
        long peoplePassed = 0;
        for (int evalTime : evalTimes) {
            peoplePassed += time / evalTime;
        }
        return peoplePassed;
    }
}
