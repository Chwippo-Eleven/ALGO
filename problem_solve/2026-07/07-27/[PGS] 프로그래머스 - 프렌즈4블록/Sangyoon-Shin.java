import java.util.*;

class Solution {
    public int solution(int m, int n, String[] board) {

        int res = 0;
        char[][] map = new char[m][n];
        for (int i = 0; i < m; i++){
            String cur = board[i];
            for (int j = 0; j < n; j++){
                map[i][j] = cur.charAt(j);
            }
        }

        // 1. 2 * 2로 없앨 수 있는 후보 찾기. -> 바로 없애면 같은 타일 중복으로 셀 수 있으니까, 후보 저장만
        // 2. 저장했던 애들 지우면서 cnt++
        // 3. 위에 남아있던 애들 아래로 떨어뜨리기
        // 4. 더 이상 지울 애들 없을때까지 반복
        while (true){
            boolean[][] cand = new boolean[m][n];

            for (int r = 0; r < m - 1; r++){
                for (int c = 0; c < n - 1; c++){
                    char cur = map[r][c];
                    if (cur == 'x'){
                        continue;
                    }
                    if (cur == map[r][c + 1] && cur == map[r + 1][c] && cur == map[r + 1][c + 1]){
                        cand[r][c] = true;
                        cand[r][c + 1] = true;
                        cand[r + 1][c] = true;
                        cand[r + 1][c + 1] = true;
                    }
                }
            }

            int cnt = 0;

            for (int r = 0; r < m; r++){
                for (int c = 0; c < n; c++){
                    if (cand[r][c]){
                        cnt++;
                        map[r][c] = 'x';
                    }
                }
            }
            if (cnt == 0){ // 더 이상 깰 게 없는 경우 종료
                break;
            }
            res += cnt;

            // 밑에서부터 채우는 로직
            for (int c = 0; c < n; c++){
                int bottom = m - 1;

                for (int r = m - 1; r >= 0; r--){
                    if (map[r][c] != 'x'){
                        map[bottom][c] = map[r][c];
                        bottom--;
                    }
                }

                for (int r = bottom; r >= 0; r--){
                    map[r][c] = 'x';
                }
            }

        }
        return res;
    }
}