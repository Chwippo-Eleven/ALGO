import java.util.*;

/*
    P가 나오면 맨헤튼 거리 2일때 까지 bfs
    파티션을 만나면 그만 보기
    거리 안에서 다른 사람을 만날 경우 거리두기 안 지킨 것
*/
class Solution {
    int[][] direc = {
        {0, 1},
        {1, 0},
        {-1, 0},
        {0, -1}
    };
    
    public int[] solution(String[][] places) {
        int[] answer = new int[5];
        
        
        for(int tc = 0; tc < 5; tc++) {
            char[][] place = new char[5][5];
            int flag = 1; // 응시자가 아예 없다면 거리두기 완
            
            for(int i = 0; i < 5; i++) {
                place[i] = places[tc][i].toCharArray();
            }
            
            for(int i = 0; i < 5; i++) {
                if(flag == 0) break;
                for(int j = 0; j < 5; j++) {
                    if(flag == 0) break;
                    
                    if(place[i][j] == 'P'){
                        boolean[][] visited = new boolean[5][5];
                        Queue<int[]> queue = new ArrayDeque<>();
                        
                        queue.offer(new int[]{i, j, 0});
                        visited[i][j] = true;
                        
                        while(!queue.isEmpty()){
                            int[] node = queue.poll();
                            int x = node[0];
                            int y = node[1];
                            int dist = node[2];
                                                        
                            if(dist == 2) continue;
                            
                            for(int d = 0; d < 4; d++) {
                                // 파티션으로 막힌 곳은 탐색 중지
                                if(x+direc[d][0] < 0 || x+direc[d][0] >=5 || y+direc[d][1] < 0 || y+direc[d][1] >=5 || visited[x+direc[d][0]][y+direc[d][1]] || place[x+direc[d][0]][y+direc[d][1]] == 'X'){
                                    continue;
                                }
                                // 거리 안에 사람이 있음
                                if(place[x+direc[d][0]][y+direc[d][1]] == 'P'){
                                    flag = 0;
                                    queue.clear();
                                    break;
                                }
                                // 빈 테이블일 경우
                                else{
                                    queue.offer(new int[]{x+direc[d][0], y+direc[d][1], dist+1});
                                    visited[x+direc[d][0]][y+direc[d][1]] = true;
                                }
                            }
                        }
                    }
                }
            }
            answer[tc] = flag;
        }
        return answer;
    }
}
