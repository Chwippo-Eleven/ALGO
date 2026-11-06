import java.util.*;
class Solution {
    int answer = 0;
    int[] start, lever, dx = {-1,1,0,0}, dy = {0,0,-1,1};
    String[] maps;
    boolean isLeverFind = false, isEscaped = false;
    public int solution(String[] maps) {
        this.maps = maps;
        
        for(int i = 0; i<maps.length; i++){
            for(int j = 0; j<maps[i].length(); j++){
                if(maps[i].charAt(j) == 'S') start = new int[]{i,j};
                else if(maps[i].charAt(j) == 'L') lever = new int[]{i,j};
            }
        }
        
        //탈출 불가 조건
        answer = bfs(start);
        answer+=bfs(lever);
        
        if(isLeverFind && isEscaped) return answer;
        else return -1;
    }
    
    int bfs(int[] current){
        char goal = isLeverFind ? 'E' : 'L';
        Queue<int[]> q = new ArrayDeque<>();
        boolean[][] chk = new boolean[maps.length][maps[0].length()];
        q.add(new int[]{current[0], current[1], 0});
        chk[current[0]][current[1]] = true;
        
        while(!q.isEmpty()){
            int[] cur = q.remove();
            if(maps[cur[0]].charAt(cur[1]) == goal){
                if(!isLeverFind) isLeverFind = true;
                else if(isLeverFind) isEscaped = true;
                return cur[2];
            }
            
            for(int i = 0; i<4; i++){
                int nx = cur[0] + dx[i];
                int ny = cur[1] + dy[i];
                if(nx<0||nx>=chk.length||ny<0||ny>=chk[0].length||chk[nx][ny]||maps[nx].charAt(ny) == 'X') continue;
                
                q.add(new int[]{nx,ny, cur[2]+1});
                chk[nx][ny] = true;
            }
        }

        return 0;        
    }

    
}