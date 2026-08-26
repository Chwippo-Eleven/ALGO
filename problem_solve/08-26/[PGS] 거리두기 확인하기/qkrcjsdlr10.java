import java.util.*;

class Solution {
    
    static int dx[] = {0, 1, 1, 1, 0, -1, -1, -1};
    static int dy[] = {-1, -1, 0, 1, 1, 1, 0, -1};
    
    static boolean areaCheck(int y, int x, String[] places){
        for(int d = 0; d < 8; d++){
            int nx = x + dx[d];
            int ny = y + dy[d];
            
            if(nx < 0 || nx >= 5 || ny < 0 || ny >= 5) continue;
            if(places[ny].charAt(nx) == 'P'){
                if(d == 0 || d == 2 || d == 4 || d == 6) return false;
                if(tableCheck(y, x, ny, nx, places) != 2){
                    return false;
                }
            }else if(d % 2 == 0 && places[ny].charAt(nx) == 'O'){
                int tempX = nx + dx[d];
                int tempY = ny + dy[d];
                if(tempX < 0 || tempX >= 5 || tempY < 0 || tempY >= 5) continue;
                if(places[tempY].charAt(tempX) == 'P'){
                    return false;
                }
            }
            
        }
        return true;
    }
    
    static int tableCheck(int curY, int curX, int checkY, int checkX, String[] places){
        int cnt = 0;
        for(int d = 0; d < 8; d+=2){
            int nx = curX + dx[d];
            int ny = curY + dy[d];
            
            if(nx < 0 || nx >= 5 || ny < 0 || ny >= 5) continue;
            if(places[ny].charAt(nx) != 'X') continue;
            for(int dir = 0; dir < 8; dir+=2){
                int nnx = checkX + dx[dir];
                int nny = checkY + dy[dir];
                if(nnx < 0 || nnx >= 5 || nny < 0 || nny >= 5) continue;
                if(nnx == nx && nny == ny){
                    cnt++;
                }
            }
        }
        
        return cnt;
    }
    
    public int[] solution(String[][] places) {
        int[] answer = new int[places.length];
        
        int n = places.length;
        int m = places[0].length;
        
        for (int i = 0; i < places.length; i++) {
            boolean isValid = true;
            for (int j = 0; j < places[i].length; j++) {
                if(!isValid) break;
                for (int k = 0; k < places[i][j].length(); k++) {
                    if (places[i][j].charAt(k) == 'P') {
                        isValid = areaCheck(j, k, places[i]);
                        if(isValid == false){
                            answer[i] = 0;
                            break;
                        }
                    }
                }
            }
            if(isValid) answer[i] = 1;
        }
        
        return answer;
    }
}