import java.util.ArrayDeque;
import java.util.Queue;

class Solution {
    private int n;
    private int[][] board;
    
    private static class Drone {
        boolean isHorizontal;   // 수평이면 true
        int axis;   // 수평이면 행, 수직이면 열 저장
        int loc1;
        int loc2;
        
        Drone(boolean isHorizontal, int axis, int loc1, int loc2) {
            this.isHorizontal = isHorizontal;
            this.axis = axis;
            this.loc1 = loc1;
            this.loc2 = loc2;
        }
        
        Drone forward() {
            return new Drone(isHorizontal, axis + 1, loc1, loc2);
        }
        
        Drone backward() {
            return new Drone(isHorizontal, axis - 1, loc1, loc2);
        }
        
        Drone moveLeft() {
            return new Drone(isHorizontal, axis, loc1 - 1, loc2 - 1);
        }
        
        Drone moveRight() {
            return new Drone(isHorizontal, axis, loc1 + 1, loc2 + 1);
        }
        
        Drone rotateA() {
            return new Drone(!isHorizontal, loc1, axis, axis + 1);
        }
        
        Drone rotateB() {
            return new Drone(!isHorizontal, loc1, axis - 1, axis);
        }
        
        Drone rotateC() {
            return new Drone(!isHorizontal, loc2, axis, axis + 1);
        }
        
        Drone rotateD() {
            return new Drone(!isHorizontal, loc2, axis - 1, axis);
        }
        
        boolean hasReached(int r, int c) {
            return isHorizontal 
                ? (axis == r && (loc1 == c || loc2 == c)) 
                : (axis == c && (loc1 == r || loc2 == r));
        }
    }
    
    private boolean isIn(int x) {
        return 0 <= x && x < n;
    }

    private boolean isClear(int... cells) {
        for (int cell : cells) {
            if (cell > 0) return false;
        }
        return true;
    }
    
    public int solution(int[][] board) {
        this.n = board.length;
        this.board = board;
    
        // 드론을 BFS로 움직이며 목적지까지 가기
        Queue<Drone> queue = new ArrayDeque<>();
        
        // (loc1 < loc2) 보장. 둘 중 하나만으로도 방문 여부 판별 가능 
        boolean[][][] visited = new boolean[2][n][n];
        
        queue.offer(new Drone(true, 0, 0, 1));
        visited[1][0][0] = true;
        
        int time = 0;
        
        // 가능한 다음 이동들을 임시로 담는 큐. BFS 로직 내부에서 사용!
        Queue<Drone> nextMoves = new ArrayDeque<>();
        
        // BFS
        while (!queue.isEmpty()) {
            time++;
            int size = queue.size();
            
            while (size-- > 0) {
                Drone drone = queue.poll();
                
                int ax = drone.axis;
                int l1 = drone.loc1;
                int l2 = drone.loc2;
                
                // 현재 이동 가능한 모든 위치 찾기 
                if (drone.isHorizontal) {
                    // 1. 앞으로 && A형 회전 && C형 회전
                    if (isIn(ax + 1) && isClear(board[ax + 1][l1], board[ax + 1][l2])) {
                        nextMoves.offer(drone.forward());
                        nextMoves.offer(drone.rotateA());
                        nextMoves.offer(drone.rotateC());
                    }
                    // 2. 뒤로 && B형 회전 && D형 회전
                    if (isIn(ax - 1) && isClear(board[ax - 1][l1], board[ax - 1][l2])) {
                        nextMoves.offer(drone.backward());
                        nextMoves.offer(drone.rotateB());
                        nextMoves.offer(drone.rotateD());
                    }
                    // 3. 좌로
                    if (isIn(l1 - 1) && isClear(board[ax][l1 - 1])) {
                        nextMoves.offer(drone.moveLeft());
                    }
                    // 4. 우로
                    if (isIn(l2 + 1) && isClear(board[ax][l2 + 1])) {
                        nextMoves.offer(drone.moveRight());
                    }
                    
                } else {
                    // 1. 앞으로 && A형 회전 && C형 회전
                    if (isIn(ax + 1) && isClear(board[l1][ax + 1], board[l2][ax + 1])) {
                        nextMoves.offer(drone.forward());
                        nextMoves.offer(drone.rotateA());
                        nextMoves.offer(drone.rotateC());
                    }
                    // 2. 뒤로 && B형 회전 && D형 회전
                    if (isIn(ax - 1) && isClear(board[l1][ax - 1], board[l2][ax - 1])) {
                        nextMoves.offer(drone.backward());
                        nextMoves.offer(drone.rotateB());
                        nextMoves.offer(drone.rotateD());
                    }
                    // 3. 좌로
                    if (isIn(l1 - 1) && isClear(board[l1 - 1][ax])) {
                        nextMoves.offer(drone.moveLeft());
                    }
                    // 4. 우로
                    if (isIn(l2 + 1) && isClear(board[l2 + 1][ax])) {
                        nextMoves.offer(drone.moveRight());
                    }
                }
                
                while (!nextMoves.isEmpty()) {
                    Drone d = nextMoves.poll();
                    
                    // 도착 지점에 도착했는지 검사
                    if (d.hasReached(n - 1, n - 1)) return time;
                    
                    int isHzt = d.isHorizontal ? 1 : 0;
                    // 이미 가본 위치 건너뛰기
                    if (visited[isHzt][d.axis][d.loc1]) continue;
                    
                    queue.offer(d);
                    visited[isHzt][d.axis][d.loc1] = true;
                }
            }
        }
        
        return -1;
    }
}
