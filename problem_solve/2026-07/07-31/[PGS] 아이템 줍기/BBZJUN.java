import java.util.*;

class Solution {
    static int[][] arr = new int[102][102];
    public int solution(int[][] rectangle, int characterX, int characterY, int itemX, int itemY) {
        // 모서리를 표현하기 위해 2배늘린다
        characterX *= 2;
        characterY *= 2;
        itemX *= 2;
        itemY *= 2;
        
        // 모서리를 표현하기 위해 2배늘린다
        for (int[] rect : rectangle){

            int x1 = rect[0] * 2;
            int y1 = rect[1] * 2;
            int x2 = rect[2] * 2;
            int y2 = rect[3] * 2;

            
            // 테두리는 1 , 안쪽은 2, 아무상관없는점은 0 default
            for(int y=y1;y<=y2;y++){
                for(int x=x1;x<=x2;x++){
                    //테두리면
                    if(x==x1 || x==x2 || y==y1 || y==y2){
                        
                        //내부로 체크된 적이 없는 점이면 테두리로 체크
                        if(arr[y][x]!=2)
                            arr[y][x]=1;

                    }else{
                        //내부점 체크
                        arr[y][x]=2;

                    }
                }
            }
        }       
        
        
        int[][] answer = ttt(characterX, characterY);
        
        return answer[itemY][itemX]/2;
    }
    
    public int[][] ttt(int characterX, int characterY){
        Deque<int[]> dq = new ArrayDeque<>();
        int[][] count = new int[102][102];
        for (int i=0; i<102; i++){
            Arrays.fill(count[i], Integer.MAX_VALUE);
        }
        
        dq.add(new int[]{characterX, characterY, 0}); // 시작점, 시작점까지 도달은 0
        
        while(!dq.isEmpty()){
            int[] poll = dq.poll();
            
            int x = poll[0];
            int y = poll[1];
            int c = poll[2]; // 해당 점까지의 거리
            
            int[] dx = {0,0,1,-1};
            int[] dy = {1,-1,0,0};
            
            int nx;
            int ny;
            
            for (int dd=0; dd<4; dd++){
                nx = x + dx[dd];                
                ny = y + dy[dd];
                
                if (nx<0 || ny<0 || nx>101 || ny>101){
                    continue;
                }
                
                if (arr[ny][nx] != 1){
                    continue;
                }

                if (c + 1 > count[ny][nx]){
                    continue;
                }
                
                count[ny][nx] = c + 1;
                dq.add(new int[]{nx, ny, c+1});
                

            }
        }
        return count;
    }
}
