import java.util.*;

class Solution {
    public int[] solution(String[][] places) {
        
        int N = 5; // 대기실 수
        
        int[] answer = new int[5]; // 답
        Arrays.fill(answer,1);
        
        for (int test=0; test<N; test++){
            char[][] pl = new char[5][5];
            
            for (int i=0; i<5; i++){
                pl[i] = places[test][i].toCharArray();
            }
            
            boolean[][] check = new boolean[5][5]; // 자기 자신이 거리두기 잘하는지 체크
            
            // 체크할 범위
            int[] dx = {1, 2, -1, -2, 0,0,0,0,1,-1,1,-1};
            int[] dy = {0, 0, 0, 0, 1,2,-1,-2,1,-1,-1,1};
                    // O O X O O 
                    // O X X X O
                    // X X S X X
                    // O X X X O
                    // O O X O O

            for (int y=0; y<5; y++){
                for (int x=0; x<5; x++){
                    if (pl[y][x] == 'P'){ // 사람이면 체크
                        for (int tmp = 0; tmp<12; tmp++){
                            int nx = x + dx[tmp];
                            int ny = y + dy[tmp];
                            if (nx < 0 || nx > 4 || ny < 0 || ny > 4){ // 범위쳌
                                continue;
                            }
                            //체크 하고
                            if (pl[ny][nx] == 'P'){ // 범위가 사람이면
                                // 검증해야함 파티션까지 
                                int c = move(pl, x,y,nx,ny);
                                if (c <= 2){
                                    answer[test] = 0;
                                    break;
                                }
                            }
                        }
                    } 
                }
            }        
        }
        return answer;
    }
    
    
    // 최단 경로 계산
    public static int move(char[][] pl, int startX, int startY, int endX, int endY){
        
        Deque<int[]> dq = new ArrayDeque<>();        
        
        dq.add(new int[]{startX, startY, 0});
        
        int[] dx = {0,0,1,-1};
        int[] dy = {1,-1,0,0};
        
        int[][] count = new int[5][5];
        for (int i=0; i<5; i++){
             Arrays.fill(count[i], Integer.MAX_VALUE);
        }
        count[startY][startX] = 0;
        
        while(!dq.isEmpty()){
            int[] p = dq.poll();
            int x = p[0];
            int y = p[1];
            int cost = p[2];

            for (int i=0; i<4; i++){
                int nx = x + dx[i];
                int ny = y + dy[i];
                if (nx > 4 || ny >4 || nx<0 || ny<0){
                    continue;
                }
                if (pl[ny][nx]=='X'){
                    continue;
                }
                if (count[ny][nx] > cost+1){
                    count[ny][nx] = cost + 1;
                    dq.add(new int[]{nx, ny, cost+1});
                }
                else{
                    continue;
                }
            }
        }
        return count[endY][endX];
    }
}
