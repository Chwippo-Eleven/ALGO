import java.util.*;
class Solution {
    public int solution(int[][] cost, int[][] hint) {
        int n = cost.length;
        
        int answer = Integer.MAX_VALUE;
        
        for(int mask  = 0; mask < (1<<(n-1)); mask++){
            
            int[] cnt = new int[n];
            
            int total = 0;
            
            for(int i =0; i<n; i++){
                //쿠폰 수
                int used = Math.min(cnt[i], n - 1);

                //쿠폰 사용하면
                total += cost[i][used];

                //마지막 스테이지가 아니고
                //현재 꾸러미를 구매하기로 했다면
                if (i < n - 1 && (mask & (1 << i)) != 0) {

                    //꾸러미 가격 지불
                    total += hint[i][0];

                    //구매한 쿠폰 추가
                    for (int j = 1; j < hint[i].length; j++) {
                        int number = hint[i][j];
                        cnt[number - 1]++;
                    }
                }
            }

            answer = Math.min(answer, total);
        }
        
        return answer;
    }
}