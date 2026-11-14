import java.util.*;

class Solution {
    public long solution(int cap, int n, int[] deliveries, int[] pickups) {

        int d = 0; // 배달량
        int p = 0; // 수거량
        long res = 0;

        for (int i = deliveries.length - 1; i >= 0; i--){

            d += deliveries[i];
            p += pickups[i];

            // 현재 위치에서 배달하거나 수거해야할게 하나라도 있으면 왕복 거리 추가
            // 이후 cap으로 빼주는데
            // 예를 들어 cap = 4인데, 현재 위치에서 d = 2라면 d = -2가 됨.
            // 그 다음 방문하는 집들에서 거리 추가 없이 2개까지 더 배달할 수 있다는 것.

            while (d > 0 || p > 0){
                d -= cap;
                p -= cap;
                res += (i + 1) * 2;

            }
        }
        return res;
    }
}