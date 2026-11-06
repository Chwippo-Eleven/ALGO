class Solution {
    
    int maxDiff = 1;        // 라이언이 어피치보다 점수가 커야 함 -> 차이가 1 이상
    int[] ryanBest = {-1};  // 점수 차가 최대일 때의 기록 저장
    
    public int[] solution(int n, int[] info) {
        int[] ryan = new int[11];   // 라이언의 화살 기록지
        shoot(n, 0, ryan, info);    // 모든 경우의 수를 탐색하고 멤버 변수 갱신
        return ryanBest;            // 결과 배열 반환
    }
    
    // DFS-Like Recursive Method : 모든 경우의 수 탐색
    // params - arrowsLeft: 남은 화살 개수, shot: 마지막에 쏜 과녁, ryan/apeach: 명중 기록지
    private void shoot(int arrowsLeft, int shot, int[] ryan, int[] apeach) {
        // 화살 다 썼으면 점수 계산
        if (arrowsLeft == 0) {
            int diff = getScoreDiff(ryan, apeach);
            
            // 갱신 로직 (중요!)
            if (diff > maxDiff) {
                // 점수 차가 기존 값을 초과하면 바로 갱신
                maxDiff = diff;
                ryanBest = ryan.clone();
            } else if (diff == maxDiff && hasMoreLowScores(ryan)) {
                // 같으면 가장 낮은 점수를 더 많이 가졌는지 확인해서 갱신!
                ryanBest = ryan.clone();
            }
            return;
        }
        
        // 중복된 경우를 지우기 위해 마지막에 쏜 과녁보다 점수가 작은 과녁만 쏜다.
        for (int i = shot; i < 11; i++) {
            // 이미 라이언이 점수를 확보한 구역에 또 쏠 필요는 없다.
            if (ryan[i] > apeach[i]) continue;
            
            // 기본적으로 한 발씩 쏘고, 0점을 쏠 때만 남은 화살을 다 쓴다.
            int shotCount = (i < 10) ? 1 : arrowsLeft;
            
            ryan[i] += shotCount;
            shoot(arrowsLeft - shotCount, i, ryan, apeach);
            ryan[i] -= shotCount;
        }
    }
    
    // [중요] "가장 낮은 점수"를 "더 많이" 맞혔는지 판별!!!
    // 그냥 덮어쓰면 "가장 높은 점수"를 "더 적게" 맞힌 경우가 선택된다!
    private boolean hasMoreLowScores(int[] ryan) {
        
        // 예외 케이스: ryanBest가 초기 상태인 경우
        if (ryanBest.length == 1) return true;
        
        // 0점부터 시작해서 같으면 넘어가고 다르면 결과 바로 반환
        for (int i = 10; i >= 0; i--) {
            if (ryan[i] > ryanBest[i])  {
                return true;
            } else if (ryan[i] < ryanBest[i]) {
                return false;
            }                        
        }
        return false; // 일반적으로 도달하지 않음 
    }
    
    // 점수 차 계산
    private int getScoreDiff(int[] ryan, int[] apeach) {
        int ryanScore = 0;
        int apeachScore = 0;
        for (int i = 0; i < 11; i++) {
            if (ryan[i] > apeach[i]) {
                ryanScore += (10 - i);
            } else if (apeach[i] > 0) {
                apeachScore += (10 - i);
            }
        }
        // 라이언과 어피치의 점수 차를 반환
        return ryanScore - apeachScore;
    }
}
