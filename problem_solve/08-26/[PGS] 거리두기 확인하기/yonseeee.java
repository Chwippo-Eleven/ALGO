import java.util.*;

class Solution {
    private static int[]dr={1,-1,0,0};
    private static int[]dc={0,0,1,-1};
    
    public int[] solution(String[][] places) {
        int[] answer = new int[5];
        Arrays.fill(answer,1);
        
        for(int p=0;p<places.length;p++){
            boolean wrong=false;
            for(int i=0;i<5;i++){
                for(int j=0;j<5;j++){
                    if(places[p][i].charAt(j)=='P'){//P인 곳에서 bfs
                        if(!bfs(i, j, places[p])){//하나라도 거리두기 위반 했으면 바로 0 넣기
                            wrong=true;
                            answer[p]=0;
                            break;
                        }
                    }
                }
                if(wrong)break;
            }
        }
        return answer;
    }
    private boolean bfs(int sr, int sc, String[] place){
        Queue<int[]> queue=new ArrayDeque<>();
        boolean[][]visited=new boolean[5][5];
        
        queue.offer(new int[]{sr,sc,0});
        visited[sr][sc]=true;
        
        while(!queue.isEmpty()){
            int[] cur=queue.poll();
            
            int cr=cur[0];
            int cc=cur[1];
            int cd=cur[2];
            
            if(cd>=2) continue;//거리 1까지만 보겠다
            
            for(int i=0;i<4;i++){
                int nr=cr+dr[i];
                int nc=cc+dc[i];
                
                if(nr>=0&&nr<5&&nc>=0&&nc<5&&place[nr].charAt(nc)!='X'){//파티션은 못 지나감
                    if(!visited[nr][nc]){
                        if(place[nr].charAt(nc)=='P')return false;//P를 만나면 거리두기 실패
                        visited[nr][nc]=true;//O는 통괴
                        queue.offer(new int[]{nr,nc,cd+1});
                    }
                    
                }
            }
        }
        
        return true;
    }
}
