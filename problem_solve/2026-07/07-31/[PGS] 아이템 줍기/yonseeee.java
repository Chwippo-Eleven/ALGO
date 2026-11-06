import java.util.*;

class Solution {
    public int solution(int[][] rectangle, int characterX, int characterY, int itemX, int itemY) {
        int answer = 0;
        
        characterX*=2;
        characterY*=2;
        
        itemX*=2;
        itemY*=2;
        
        int[] dr={1,-1,0,0};
        int[] dc={0,0,1,-1};
        
        int[][]map=new int[101][101];
        
        for(int[] r:rectangle){
            //경계 좌표 2배
            for(int i=0;i<4;i++){
                r[i]*=2;
            }
            for(int x=r[0];x<=r[2];x++){
                for(int y=r[1];y<=r[3];y++){
                    if(map[x][y]==-1)continue;//이미 어떤 직사각형의 내부이면 진행 x
                    if(x==r[0]||x==r[2]||y==r[1]||y==r[3])//직사각형 테두리
                        map[x][y]=1;
                    else{
                        map[x][y]=-1;//직사각형 내부
                    }
                }
            }
        }

        //여기서부터는 그냥 BFS
        Queue<int[]>queue=new ArrayDeque<>();
        boolean[][]visited=new boolean[101][101];
        
        queue.offer(new int[]{characterX, characterY, 1});
        visited[characterX][characterY]=true;
        
        while(!queue.isEmpty()){
            int[] cur=queue.poll();
            
            if(cur[0]==itemX&&cur[1]==itemY){
                return cur[2]/2;
            }
            for(int i=0;i<4;i++){
                int nr=cur[0]+dr[i];
                int nc=cur[1]+dc[i];
                
                if(nr>=0&&nr<101&&nc>=0&&nc<101&&map[nr][nc]==1){
                    if(!visited[nr][nc]){
                        visited[nr][nc]=true;
                        queue.offer(new int[]{nr,nc,cur[2]+1});
                    }
                }
            }
        }
        return answer;
    }
}
