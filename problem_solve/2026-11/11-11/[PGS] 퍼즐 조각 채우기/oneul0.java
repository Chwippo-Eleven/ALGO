import java.util.*;
class Solution {
    class Point {
        int x,y;
        Point(int x, int y){
            this.x = x;
            this.y = y;
        }
        
        @Override
        public boolean equals(Object o){
            if(this == o) return true;
            if(!(o instanceof Point other)) return false;
            
            return this.x == other.x && this.y == other.y;
        }
        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
    
    int n;
    int[] dx = {-1,1,0,0}, dy = {0,0,-1,1};
    public int solution(int[][] game_board, int[][] table) {
        n = game_board.length;
        int answer = 0;
        //테이블의 모든 블록 저장
        List<List<List<Point>>> allBlocks = new ArrayList<>();
        for(int i = 0; i<n; i++){
            for(int j = 0; j<n; j++){
                if(table[i][j] == 1){
                    allBlocks.add(genRotateState(getBlock(i, j, 1, table)));
                }
            }
        }
        
        //게임보드에 있는 빈칸을 탐색할 때마다 딱 맞는 블록이 있다면 채우기
        List<Point> cells = new ArrayList<>();
        for(int i = 0; i<n; i++){
            for(int j = 0; j<n; j++){
                if(game_board[i][j] == 0){
                    cells = getBlock(i, j, 0, game_board);
                    
                    int matchedBlockIdx = -1;
                    for(int blockIdx = 0; blockIdx < allBlocks.size(); blockIdx++){
                        List<List<Point>> rotationStates = allBlocks.get(blockIdx);
                        
                        int idx = findRotationStates(cells, rotationStates);
                        
                        if(idx > -1){
                            matchedBlockIdx = blockIdx;
                            answer += cells.size();
                            break;
                        }
                        //만약 채울 수 없어도 복구할 필요 없음
                        //갖고 있는 모든 블록을 검사했기 때문에 어짜피 못 채움
                    }
                    
                    //allBlocks에서 해당 블록 지우기
                    if (matchedBlockIdx != -1) {
                        allBlocks.remove(matchedBlockIdx);
                    }
                }
            }
        }
        
        return answer;
    }
    
    //bfs
    List<Point> getBlock(int sx, int sy, int targetCell, int[][] board){
        Queue<Point> q = new ArrayDeque<>();
        
        q.offer(new Point(sx, sy));
        board[sx][sy] = targetCell ^ 1;
        
        List<Point> block = new ArrayList<>();
        block.add(new Point(sx, sy));
        while(!q.isEmpty()){
            Point cur = q.poll();
            
            for(int i = 0; i<4; i++){
                int nx = cur.x + dx[i];
                int ny = cur.y + dy[i];
                
                if(nx<0 || ny<0 || nx>=n || ny>=n || (board[nx][ny] != targetCell)) continue;
                
                q.offer(new Point(nx, ny));
                block.add(new Point(nx, ny));
                board[nx][ny] = targetCell ^ 1;
            }
        }
        return block;
    }
    
    //블록을 왼쪽 위(0,0)으로 이동하고 일정한 순서로 정렬
    //같은 블록인데 들어온 좌표만 다를수도 있기 때문
    List<Point> normalize(List<Point> cells){
        int minX = cells.stream()
            .mapToInt(p -> p.x)
            .min()
            .orElseThrow();
        int minY = cells.stream()
            .mapToInt(p -> p.y)
            .min()
            .orElseThrow();
        
        List<Point> normalized = new ArrayList<>();
        
        for(Point point : cells){
            normalized.add(new Point(
                point.x - minX,
                point.y - minY
            ));
        }
        
        normalized.sort(
            Comparator.comparingInt((Point p) -> p.x)
                .thenComparingInt(p -> p.y)
        );
        
        return normalized;
    }
    
    List<Point> rotate90ClockWise(List<Point> cells){
        List<Point> rotated = new ArrayList<>();
        
        for(Point point : cells){
            rotated.add(new Point(
                -point.y,
                point.x
            ));
        }
        return rotated;
    }
    
    //90도 회전 모든 상태 생성
    List<List<Point>> genRotateState(List<Point> cells){
        List<List<Point>> rotateStates = new ArrayList<>();
        Set<List<Point>> uniqueStates = new HashSet<>();
        
        List<Point> cur = new ArrayList<>(cells);
        
        for(int i = 0; i<4; i++){
            List<Point> normalized = normalize(cur);
            if(uniqueStates.add(normalized)){
                rotateStates.add(normalized);
            }
            
            cur = rotate90ClockWise(normalized);
        }
        return rotateStates;
    }
    
    //이 블록이 몇 번째 블록인지 인덱스 반환
    int findRotationStates(List<Point> cells, List<List<Point>> rotatedStates){
        List<Point> normalizedCells = normalize(cells);
        
        for(int i = 0; i<rotatedStates.size(); i++){
            if(rotatedStates.get(i).equals(normalizedCells)) return i;
        }
        return -1;
    }
}