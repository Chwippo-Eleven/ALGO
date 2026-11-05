import java.util.*;

class Solution {
    static int[] aaaaa; // 어피치
    static int[] bbbbb = new int[11]; //라이언
    static int[] best; // 베스트 중복되는 점수 있을때, 작은수 더 맞춘경우 따지기
    static int max = 0;

    public int[] solution(int n, int[] info) {
        aaaaa = Arrays.copyOf(info, info.length);// 복사
        dfs(0, n);
        return max == 0 ? new int[]{-1} : best; // 없
    }

    // 몇 번째 쏨, 남은 화살
    void dfs(int idx, int n) {
        // 다 쏘면
        if (idx == 10) {
            bbbbb[10] = n;
            check();
            bbbbb[10] = 0; // 백트래킹
            return;
        }

        for (int cnt = 0; cnt <= n; cnt++) {
            bbbbb[idx] = cnt;//idx번째 화살은 = cnt를 맞췄다
            dfs(idx + 1, n - cnt); // 0부터 하나씩 늘려가며 idx 자리에 배정, 나머지는 뒤로 넘김
            bbbbb[idx] = 0; // 백트래킹
        }
    }

    void check() {
        int aSum = 0, bSum = 0;
        for (int i = 0; i <= 10; i++) {
            int score = 10 - i;
            if (aaaaa[i] == 0 && bbbbb[i] == 0){
              continue; // 무승부  
            }  
            if (bbbbb[i] > aaaaa[i]){
              bSum += score;  
            } 
            else{
              aSum += score;  
            } 
        }

        int diff = bSum - aSum; // 라이언 - 어피치로 라이언이 이겨야함
        if (diff <= 0) // 라이언이 지면 계산 ㄴ
            return;
        
        if (diff > max) {
            max = diff; 
            best = Arrays.copyOf(bbbbb, bbbbb.length);
        } else if (diff == max) {
            // 만약 라이언이 가장 큰 점수 차이로 우승할 수 있는 방법이 여러 가지 일 경우, 가장 낮은 점수를 더 많이 맞힌 경우
            int tmp = 0;
            for (int k= 10; k>=0; k--){
                if (bbbbb[k] == best[k]){
                    continue;
                }
                else if(bbbbb[k] > best[k]){
                    tmp = 1;
                    break;
                }
                else{
                    break;
                }
            }
            if (tmp == 1) {
                best = Arrays.copyOf(bbbbb, bbbbb.length);
            }
        }
    }
}
