class Solution {
    public long solution(int cap, int n, int[] deliveries, int[] pickups) {
        int del = 0; // 아직 처리하지 못한 배달량
        int pak = 0; // 아직 처리하지 못한 수거량
        long answer = 0;

        // 먼 집으로 가는 왕복은 가까운 집의 경로를 포함하므로
        // 먼 집부터 필요한 왕복을 확보하고
        // 남은 적재 용량을 가까운 집의 배달과 수거에 활용
        for (int i = n - 1; i >= 0; i--) {
            del += deliveries[i];
            pak += pickups[i];

            // 0 이상은 기존 왕복의 용량만으로 처리할 수 없는 물량
            // 0 이하는 기존 왕복의 용량으로 처리 가능
            while (del > 0 || pak > 0) {
                del -= cap;
                pak -= cap;

                // 현재 집까지 왕복하는 거리
                answer += (long) (i + 1) * 2;
            }
        }

        return answer;
    }
}