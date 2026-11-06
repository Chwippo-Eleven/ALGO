import java.util.*;

class Solution {
    public int solution(int[][] routes) {
        // 진출지점 기준으로 오름차순 정렬 후, 해당구간의 진입지점보다 카메라가 앞에있으면 새로 설치해야함.
        Arrays.sort(routes, (a, b) -> {
            return Integer.compare(a[1], b[1]);
        });

        int pos = routes[0][0] - 1; // 시작전 카메라 위치 초기화
        int cnt = 0;

        for (int[] cur : routes){
            if (pos < cur[0]){
                pos = cur[1];
                cnt++;
            }
        }
        return cnt;


    }
}