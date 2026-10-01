class Solution {
    public int[] solution(int[] progresses, int[] speeds) {
        
        int n = progresses.length;
        int[] days = new int[n];    // 각 기능의 배포 가능 날짜
        
        int deployDay = 0;  // 배포하는 기준 날짜
        int deployCnt = 0;  // 결과 배열 크기
        
        // 배포 가능한 날짜 계산해서 이전 기능과 같이 배포 가능한지 확인
        for (int i = 0; i < n; i++) {
            days[i] = (int) Math.ceil((100.0 - progresses[i]) / speeds[i]);
            
            // 따로 배포해야 한다면 관련 값 갱신
            if (days[i] > deployDay) {
                deployDay = days[i];
                deployCnt += 1;
            }
        }
        
        // 결과 배열
        int[] deployPlan = new int[deployCnt];
        
        int day = days[0];  // 시작 날짜는 첫 기능의 배포일
        int planIndex = 0;  // 결과 배열 조회 인덱스
        
        // days 순회하며 결과 배열 생성
        for (int i = 0; i < n; i++) {
            if (days[i] > day) {
                day = days[i];
                deployPlan[++planIndex] += 1;
            } else {
                deployPlan[planIndex] += 1;
            }
        }
        
        return deployPlan;
    }
}
