import java.util.*;
// 어피치가 다 쏘고 라이언이 쏨!!!

class Solution {
    int maxDiff;
    int[] bestScores;
    
    public int[] solution(int n, int[] info) {
        maxDiff = 0;
        // -1로 초기화
        bestScores = new int[]{-1};
        
        // 백트래킹
        int[] result = new int[11];
        dfs(0, n, result, info);
        
        return bestScores;
    }
    
    public void dfs(int index, int arrows, int[] result, int[] info){
        // 모든 점수 판단을 마쳤을 때
        if(index == 10){
            // 남은 화살을 전부 0 점으로
            result[10] = arrows;
            
            // 점수 계산
            int rScore = 0;
            int aScore = 0;
            
            for (int i = 0; i <= 10; i++) { 
                int score = 10 - i;
                // 둘 다 0발일 경우
                if(result[i] == 0 && info[i] == 0)
                    continue;
                
                if(result[i] > info[i]){
                    rScore += score;
                } else {
                    aScore += score;
                }
            }
            
            int diff = rScore - aScore;
            
            if (diff > 0) {
                // max 갱신
                if(diff > maxDiff) {
                    maxDiff = diff;
                    bestScores = result.clone();
                } 
                // 점수 차가 같을 대 가장 낮은 점수를 더 많이 맞힌 경우 선택
                else if (diff == maxDiff) {
                    if (isBetter(result, bestScores)) {
                        bestScores = result.clone();
                    }
                }
            }
            // 백트래킹 원복 (다음 탐색을 위해 0점 화살 수 초기화)
            result[10] = 0;
            return;
        }
        
        // 이기기 위해 필요한 화살 개수
        int need = info[index] + 1;
        
        // 현재 점수를 가져오기
        if(arrows >= need) {
            result[index] = need;
            // 화살 차감
            dfs(index+1, arrows-need, result, info);
            result[index] = 0; // 원복
        }
        
        // 현재 점수를 포기
        result[index] = 0;
        dfs(index+1, arrows, result, info);
    }
    
    public boolean isBetter(int[] newRyan, int[] currentBest) {
        // 배열의 맨 뒤부터 역순으로 비교
        for (int i = 10; i >= 0; i--) {
            if (newRyan[i] > currentBest[i]) {
                return true; // 새로 만든 배치가 0점 쪽 화살이 더 많음 -> 갱신 O
            } else if (newRyan[i] < currentBest[i]) {
                return false; // 기존 배치가 0점 쪽 화살이 더 많음 -> 갱신 X
            }
        }
        return false;
    }
}
