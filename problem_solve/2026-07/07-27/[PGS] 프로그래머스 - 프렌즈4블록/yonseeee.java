import java.util.*;

class Solution {
    public int solution(int m, int n, String[] board) {
        int answer = 0;
        
        char[][]map=new char[m][n];
        int[] dr={-1,-1,0};
        int[] dc={0,1,1};
        
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                map[i][j]=board[i].charAt(j);
            }
        }
        
        while(true){
            List<int[]>list = new ArrayList<>();
            
            //지워질 2*2 블럭의 왼쪽 하단 좌표 저장
            for(int i=m-1;i>=0;i--){
                for(int j=0;j<n;j++){
                    char current=map[i][j];
                    if(current=='0')continue;
                    
                    int cnt=0;
                    for(int k=0;k<3;k++){
                        int nr=i+dr[k];
                        int nc=j+dc[k];

                        if(nr>=0&&nr<m&&nc>=0&&nc<n){
                            if(current!=map[nr][nc]){
                                break;
                            }
                            cnt++;
                        }
                    }
                    if(cnt==3){
                        list.add(new int[]{i,j});
                    }
                }
            }
            
            //while문 탈출조건
            if(list.size()==0)break;
            
            
            //지워질 블록 자리 표시
            for(int[] arr:list){
                if(map[arr[0]][arr[1]]!='0'){
                    map[arr[0]][arr[1]]='0';
                    answer++;
                }
                   
                
                for(int k=0;k<3;k++){
                    int nr=arr[0]+dr[k];
                    int nc=arr[1]+dc[k];
                    
                    if(map[nr][nc]!='0'){
                        map[nr][nc]='0';
                        answer++;
                    };
                }
            }

            //블록 내리기
            for(int j=0;j<n;j++){
                for(int i=m-1;i>=0;i--){
                    if(map[i][j]=='0'){
                        
                        int k=i-1;
                        while(k>=0 &&map[k][j]=='0'){
                            k--;
                        }
                        if(k>=0){
                            map[i][j]=map[k][j];
                            map[k][j]='0';
                        }
                        
                    }
                    
                }
            }
            
        }

        
        return answer;
    }
}
