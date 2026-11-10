class Solution {
    public long solution(int cap, int n, int[] deliveries, int[] pickups) {
        
        long totalDist = 0L;
        
        int dIdx = n - 1;   // 배달 위치에 대한 인덱스
        int pIdx = n - 1;   // 픽업 위치에 대한 인덱스
        
        while (dIdx >= 0 || pIdx >= 0) {
            
            // 1. 가장 먼 배달 지점 찾기
            while (dIdx >= 0 && deliveries[dIdx] == 0) {
                dIdx--;
            }
            
            // 2. 가장 먼 픽업 지점 찾기
            while (pIdx >= 0 && pickups[pIdx] == 0) {
                pIdx--;
            }
            
            // 3. 이동 거리 산출
            int dist = Math.max(dIdx, pIdx) + 1;
            
            // 4. 배달 진행
            int capLeft = cap;
            
            while (capLeft > 0 && dIdx >= 0) {
                
                if (capLeft >= deliveries[dIdx]) {
                    capLeft -= deliveries[dIdx];
                    deliveries[dIdx--] = 0;
                    
                } else {
                    deliveries[dIdx] -= capLeft;
                    capLeft = 0;
                }
            }
            
            // 5. 픽업 진행
            capLeft = cap;
            
            while (capLeft > 0 && pIdx >= 0) {
                
                if (capLeft >= pickups[pIdx]) {
                    capLeft -= pickups[pIdx];
                    pickups[pIdx--] = 0;
                    
                } else {
                    pickups[pIdx] -= capLeft;
                    capLeft = 0;
                }
            }
            
            // 6. 총 이동 거리 계산
            totalDist += dist * 2;
        }
        
        return totalDist;
    }
}
