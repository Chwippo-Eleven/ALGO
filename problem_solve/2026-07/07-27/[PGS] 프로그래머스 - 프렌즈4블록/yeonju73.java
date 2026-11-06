import java.util.*;

class Solution {
    
    // 우, 하, 오른쪽 아래 대각선
    int[][] direc = {
        {0, 1},
        {1, 0},
        {1, 1}
    };
    
    public int solution(int m, int n, String[] board) {
        int answer = 0;
        char[][] gameMap = new char[m][n];
        
        for (int i = 0; i < m; i++) {
            int j = 0;
            for (char c : board[i].toCharArray()) {
                gameMap[i][j++] = c;
            }
        }
        
        // 탐색해도 지울 블럭이 없을 때 까지 반복쓰
        Set<String> deleteSet = checkDelete(m, n, gameMap);
        while(deleteSet.size() > 0){
            answer += delete(m, n, deleteSet, gameMap);
            rowDown(m, n,gameMap);
            deleteSet = checkDelete(m, n, gameMap);
        }
        
        return answer;
    }
    
    
    
    
    // 밑으로 내리는 함수 (열 기준 아래에서 위로 채우기)
    public void rowDown(int m, int n, char[][] gameMap) {
        for (int j = 0; j < n; j++) {
            // 현재 열에서 아래부터 빈공간이 아닌 블록 수집
            List<Character> blocks = new ArrayList<>();
            for (int i = m - 1; i >= 0; i--) {
                if (gameMap[i][j] != '.') {
                    blocks.add(gameMap[i][j]);
                }
            }

            // 아래쪽부터 다시 채워넣음
            int idx = m - 1;
            for (char block : blocks) {
                gameMap[idx--][j] = block;
            }
            // 남은 위쪽 빈 공간들은 전부 .으로 채움
            while (idx >= 0) {
                gameMap[idx--][j] = '.';
            }
        }
    }
        
    // 지우는 함수 - 지운 블럭 수 반환
    public int delete(int m, int n, Set<String> deleteSet, char[][] gameMap){
        int count = 0;
        
        for (String d : deleteSet) {
            //marking
            int x = Integer.parseInt(d.split(",")[0]);
            int y = Integer.parseInt(d.split(",")[1]);
            gameMap[x][y] = '.';
            count++;
        }
        
        return count;
    }
    
    // 뭐지울지 판단하는 함수
    public Set<String> checkDelete(int m, int n, char[][] gameMap){        
        Set<String> deleteSet = new HashSet<>();
        
        for (int i = 0; i < m-1; i++) {
            for (int j = 0; j < n-1; j++) {
                char currentValue = gameMap[i][j];
                if (currentValue == '.') continue;
                
                // 2x2 형태로 같은 value를 가지고 있는지 판단
                boolean flag = true;
                
                for(int[] di : direc){
                    int dx = i + di[0];
                    int dy = j + di[1];
                    if(dx < 0 || dx >= m || dy < 0 || dy >= n || gameMap[dx][dy] != currentValue){
                        flag = false;
                        break;
                    }
                }
                // delete 할 거 체크
                if (flag) {
                    deleteSet.add(i + "," + j);
                    for(int[] di : direc){
                        int dx = i + di[0];
                        int dy = j + di[1];
                        deleteSet.add(dx + "," + dy);
                    }
                }
            }
        }
        return deleteSet;
    }
}
