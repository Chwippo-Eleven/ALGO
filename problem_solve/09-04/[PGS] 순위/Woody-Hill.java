import java.util.HashSet;
import java.util.Set;

class Solution {
    public int solution(int n, int[][] results) {
        
        // winner[x] : x를 이긴 사람들
        // loser[x]  : x에게 진 사람들
        Set<Integer>[] winner = new Set[n + 1];
        Set<Integer>[] loser  = new Set[n + 1];
        
        for (int i = 1; i <= n; i++) {
            winner[i] = new HashSet<>();
            loser[i]  = new HashSet<>();
        }
        
        for (int[] result : results) {
            int a = result[0];
            int b = result[1];
            
            winner[b].add(a);               // b를 이긴 a
            winner[b].addAll(winner[a]);    // a를 이긴 사람들도 추가
            
            // b에게 진 사람들은 a에게 지니까 b랑 똑같이 처리
            for (int x : loser[b]) {        
                winner[x].add(a);
                winner[x].addAll(winner[a]);
            }
            
            loser[a].add(b);                // a에게 진 b
            loser[a].addAll(loser[b]);      // b에게 진 사람들도 추가
            
            // a를 이긴 사람들은 b를 이기니까 a랑 똑같이 처리
            for (int x : winner[a]) {
                loser[x].add(b);
                loser[x].addAll(loser[b]);
            }
        }
        
        int fixRank = 0;
        
        // (자신보다 센 사람) + (자신보다 약한 사람) + (자기 자신) == n 이면 순위 확정
        for (int i = 1; i <= n; i++) {
            if (winner[i].size() + loser[i].size() + 1 == n) {
                fixRank += 1;
            }
        }
        
        return fixRank;
    }
}
