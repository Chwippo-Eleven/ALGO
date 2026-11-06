import java.util.*;

class Solution {
    public int solution(int[][] routes) {
        
        // 진출로 정렬
        Arrays.sort(routes, (a, b) -> a[1] - b[1]);
        
        int camera = Integer.MIN_VALUE; // 일단 맨 왼쪽 부터 탐색(시간대 기준 맨 왼쪽)
        int answer = 0;
        
        for (int[] route : routes) {
            // 현재 카메라로 불가면
            if (camera < route[0]) { // (X축 좌표라 생각하고) 카메라 < 진입시점면 카메라에 못 잡는거니까 설치해주는 조건
                // 현재 차의 오른쪽 끝에 설치(차의 진출 시점에 설치하는게 가장 카메라를 적게 설치할 수 있음 - 겹칠확률이 높으니까)
                camera = route[1];
                answer++;
            }
        }
        
        return answer;
    }
}
