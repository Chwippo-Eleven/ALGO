import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

class Solution {
    private static int[] dr = {-1, 1, 0, 0};
    private static int[] dc = {0, 0, -1, 1};
    
    private static class Puzzle {
        int row;
        int col;
        int size;
        boolean[][] shape;
        
        private Puzzle(int row, int col, int size, boolean[][] shape) {
            this.row = row;
            this.col = col;
            this.size = size;
            this.shape = new boolean[row][];
            
            for (int i = 0; i < row; i++) {
                this.shape[i] = shape[i].clone();
            }
        }
        
        private Puzzle rotate90CCW() {
            boolean[][] rotated = new boolean[col][row];
            
            for (int r = 0; r < row; r++) {
                for (int c = 0; c < col; c++) {
                    rotated[col - 1 - c][r] = shape[r][c];
                }
            }
            
            return new Puzzle(col, row, size, rotated);
        }
        
        private boolean equals(Puzzle o) {
            if (this.row != o.row || this.col != o.col || this.size != o.size) {
                return false;
            }
            return Arrays.deepEquals(this.shape, o.shape);
        }
        
        @Override
        protected Puzzle clone() {
            return new Puzzle(row, col, size, shape);
        }
    }
    
    private static class PuzzleSet {
        Puzzle[] puzzles;
        
        private PuzzleSet(Puzzle puzzle) {
            Puzzle rot90 = puzzle.rotate90CCW();
            
            if (rot90.equals(puzzle)) {
                this.puzzles = new Puzzle[1];
                
                puzzles[0] = puzzle.clone();
                return;
            }
            
            Puzzle rot180 = rot90.rotate90CCW();
            
            if (rot180.equals(puzzle)) {
                this.puzzles = new Puzzle[2];
                
                puzzles[0] = puzzle.clone();
                puzzles[1] = rot90;
                return;
            }
            
            Puzzle rot270 = rot180.rotate90CCW();
            
            this.puzzles = new Puzzle[4];
            
            puzzles[0] = puzzle.clone();
            puzzles[1] = rot90;
            puzzles[2] = rot180;
            puzzles[3] = rot270;
        }
        
        private int size() {
            return puzzles.length;
        }
        
        private int puzzleSize() {
            return puzzles[0].size;
        }
        
        private boolean contains(Puzzle puzzle) {
            for (Puzzle p : puzzles) {
                if (puzzle.equals(p)) {
                    return true;
                }
            }
            return false;
        }
    }
    
    public int solution(int[][] game_board, int[][] table) {
        
        // 퍼즐을 종류별로 기록하는 리스트
        List<PuzzleSet> puzzleSets = new ArrayList<>();
        
        // 퍼즐 종류별로 빈칸 수와 조각 수를 기록
        List<Integer> spaceCount = new ArrayList<>();
        List<Integer> pieceCount = new ArrayList<>();
        // HashMap을 List로 쪼개서 사용하는 느낌의 구현...
        
        // BFS로 일단 다 가져오기
        List<Puzzle> spaces = bfs(game_board, 0);
        List<Puzzle> pieces = bfs(table, 1);
        
        // 빈칸들 먼저 정리
        for (Puzzle puzzle : spaces) {
            int pIndex = -1;
            
            for (int i = 0; i < puzzleSets.size(); i++) {
                if (puzzleSets.get(i).contains(puzzle)) {
                    pIndex = i;
                    break;
                }
            }
            
            // 퍼즐이 속하는 집합을 찾은 경우
            if (pIndex >= 0) {
                spaceCount.set(pIndex, spaceCount.get(pIndex) + 1);
                
            // 처음 보는 유형의 퍼즐인 경우
            } else {
                puzzleSets.add(new PuzzleSet(puzzle));
                spaceCount.add(1);
                pieceCount.add(0);
            }
        }
        
        // 퍼즐 조각들 정리
        for (Puzzle puzzle : pieces) {
            int pIndex = -1;
            
            for (int i = 0; i < puzzleSets.size(); i++) {
                if (puzzleSets.get(i).contains(puzzle)) {
                    pIndex = i;
                    break;
                }
            }
            
            // 퍼즐이 속하는 집합을 찾은 경우
            if (pIndex >= 0) {
                pieceCount.set(pIndex, pieceCount.get(pIndex) + 1);
                
            // 처음 보는 유형의 퍼즐인 경우
            } else {
                puzzleSets.add(new PuzzleSet(puzzle));
                spaceCount.add(0);
                pieceCount.add(1);
            }
        }
        
        int filledSlots = 0;
        
        for (int i = 0; i < puzzleSets.size(); i++) {
            filledSlots += Math.min(pieceCount.get(i), spaceCount.get(i)) * puzzleSets.get(i).puzzleSize();
        }
        
        return filledSlots;
    }
    
    private List<Puzzle> bfs(int[][] arr, int target) {
        
        List<Puzzle> puzzles = new ArrayList<>();
        
        int n = arr.length;
        int m = arr[0].length;
        
        boolean[][] visited = new boolean[n][m];
        boolean[][] forCopy = new boolean[n][m];
        Queue<int[]> queue = new ArrayDeque<>();
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (arr[i][j] != target || visited[i][j]) continue;
                
                visited[i][j] = true;
                forCopy[i][j] = true;
                queue.offer(new int[] {i, j});
                
                int top = i, down = i;
                int left = j, right = j;
                int pSize = 1;
                
                // BFS로 해당 영역 탐색
                while (!queue.isEmpty()) {
                    int[] coord = queue.poll();
                    int r = coord[0];
                    int c = coord[1];
                    
                    for (int d = 0; d < 4; d++) {
                        int nr = r + dr[d];
                        int nc = c + dc[d];
                        
                        // 배열 밖이거나, 이미 봤거나, 찾는 값이 아니면 건너뛰기
                        if (!isIn(nr, nc, n, m) || visited[nr][nc] || arr[nr][nc] != target) continue;
                        
                        visited[nr][nc] = true;
                        forCopy[nr][nc] = true;
                        pSize += 1;
                        
                        top   = Math.min(top, nr);
                        down  = Math.max(down, nr);
                        left  = Math.min(left, nc);
                        right = Math.max(right, nc);
                        
                        queue.offer(new int[] {nr, nc});
                    }
                }
                
                // forCopy 이용해 퍼즐 생성
                int pRow = down - top + 1;
                int pCol = right - left + 1;
                boolean[][] shape = new boolean[pRow][pCol];
                
                for (int r = top; r <= down; r++) {
                    for (int c = left; c <= right; c++) {
                        shape[r - top][c - left] = forCopy[r][c];
                        forCopy[r][c] = false;
                    }
                }
                
                puzzles.add(new Puzzle(pRow, pCol, pSize, shape));
            }
        }
        
        return puzzles;
    }
    
    private boolean isIn(int r, int c, int n, int m) {
        return 0 <= r && r < n && 0 <= c && c < m;
    }
}
