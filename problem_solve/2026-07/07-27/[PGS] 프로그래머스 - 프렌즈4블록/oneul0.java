import java.util.*;
class Solution {
    class Pair {
        int x, y;
        Pair(int x, int y){
            this.x = x;
            this.y = y;
        }
        
        @Override
        public boolean equals(Object o){
            if(this == o) return true;
            if(o == null || !(o instanceof Pair tmp)) return false;
            Pair p = (Pair) o;
            return Objects.equals(this.x, p.x) && Objects.equals(this.y, p.y);
        }
        @Override
        public int hashCode(){
            return Objects.hash(x, y);
        }
    }
    int n, m, answer = 0;
    final char BLANK = '0';
    char[][] map;
    public int solution(int m, int n, String[] board) {
        this.n = n;
        this.m = m;
        this.map = new char[m][n];
        for(int i = 0; i<m; i++){
            map[i] = board[i].toCharArray();
        }
        
        while(solve()){
            
        }
        
        return answer;
    }
    
    public boolean solve(){
        //전체 범위를 스캔하며 2*2 범위 체크
        Set<Pair> tmp = new HashSet<>();
        for(int i = 0; i<m; i++){
            for(int j = 0; j<n; j++){
                if(isSame2x2(i, j)){
                    tmp.add(new Pair(i, j));
                    tmp.add(new Pair(i+1, j));
                    tmp.add(new Pair(i, j+1));
                    tmp.add(new Pair(i+1, j+1));
                }
            }
        }
        if(tmp.size() < 4) return false;
        
        //전달된 블록 없애기
        answer += tmp.size();
        for(Pair point: tmp){
            map[point.x][point.y] = BLANK;
        }
        //각 col 빈칸없이 밑으로 내리는 함수    
        for(int j = 0; j<n; j++){
            gravityPerCol(j);
        }
        return true;
    }
    
    public void gravityPerCol(int cy){
        int cur = m-1;
        for(int i = m-1; i>=0; i--){
            //빈칸이 아니라면 칸 내리기
            if(map[i][cy] != BLANK){
                map[cur][cy] = map[i][cy];
                
                if(cur != i){
                    map[i][cy] = BLANK;
                }
                
                cur--;
            }
        }
        while(cur >= 0){
            map[cur][cy] = BLANK;
            cur--;
        }
    }
    
    public boolean isSame2x2(int x, int y) {
        if (!isInRange(x+1, y+1)) return false;

        char value = map[x][y];
        
        if(value == BLANK) return false;

        return value == map[x+1][y] 
            && value == map[x][y+1]
            && value == map[x+1][y+1];
    }
    
    public boolean isInRange(int x, int y){
        return x>=0 && y>=0 && x<m && y<n;
    }
}
