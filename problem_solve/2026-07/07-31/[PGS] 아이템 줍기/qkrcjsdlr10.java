import java.util.*;

class Solution {
    
    static public class Pair{
        int first;
        int second;
        
        public Pair(int first, int second){
            this.first = first;
            this.second = second;
        }
    }
    
    public int solution(int[][] rectangle, int characterX, int characterY, int itemX, int itemY) {
        int answer = 0;
        
        int maxX = 110;
        int maxY = 110;
        
        int[] dx = {0, 1, 0, -1};
        int[] dy = {-1, 0, 1, 0};
        
        characterY = maxY - 1 - (characterY * 2);
        itemY = maxY - 1 - (itemY * 2);
        
        characterX *= 2;
        itemX *= 2;
        
        int[][] map = new int[maxY][maxX];
        int[][] dist = new int[maxY][maxX];
        
        for(int[] rec : rectangle){
            int sx = rec[0] * 2;
            int sy = rec[1] * 2;
            int ex = rec[2] * 2;
            int ey = rec[3] * 2;
            
            for(int x = sx; x <= ex; x++){
                for(int y = sy; y <= ey; y++){
                    map[maxY - 1 - y][x] = 1;
                }
            }
        }
        
        for(int[] rec : rectangle){
            int sx = rec[0] * 2;
            int sy = rec[1] * 2;
            int ex = rec[2] * 2;
            int ey = rec[3] * 2;
            for(int y = sy + 1; y < ey; y++){
                for(int x = sx + 1; x < ex; x++){
                    map[maxY - 1 - y][x] = 0;
                }
            }
        }
        
        // for(int[] r : map){
        //     for(int c : r){
        //         System.out.print(c);
        //     }
        //     System.out.println();
        // }
        
        for(int[] cur : dist){
            Arrays.fill(cur, -1);
        }
        
        Queue<Pair> q = new ArrayDeque<>();
        
        q.offer(new Pair(characterY, characterX));
        
        dist[characterY][characterX] = 0;
        // dist[itemY][itemX] = Integer.MAX_VALUE;
        while(q.isEmpty() == false){
            Pair cur = q.poll();
            int cury = cur.first;
            int curx = cur.second;
            // System.out.println(dist[cury][curx]);
            for(int d = 0; d < 4; d++){
                int nx = curx + dx[d];
                int ny = cury + dy[d];
                
                
                if(map[ny][nx] != 1 || dist[ny][nx] != -1) continue;
                // System.out.println(dist[ny][nx]);
                
                if(ny == itemY && nx == itemX){
                    dist[ny][nx] = dist[cury][curx] + 1;
                    while(q.isEmpty() == false){
                        q.poll();
                    }
                    break;
                }
                dist[ny][nx] = dist[cury][curx] + 1;
                q.offer(new Pair(ny, nx));
            }
        }
        
        answer = dist[itemY][itemX] / 2;
        
        return answer;
    }
}