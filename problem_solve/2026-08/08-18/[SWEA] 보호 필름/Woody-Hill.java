import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.Scanner;
 
public class Solution {
     
    static final int A = 0;
    static final int B = 1;
    static final int NONE = -1;
     
    // For Queue
    static class State {
        int[] apply;    // 적용할 변경 정보 배열
        int index;      // 중복 변경 방지를 위한 인덱스
        int count;      // 적용할 변경 개수
         
        State(int[] apply, int index, int count) {
            this.apply = apply;
            this.index = index;
            this.count = count;
        }
    }
     
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();
          
        for (int testCase = 1; testCase <= T; testCase++) {
            int D = sc.nextInt();
            int W = sc.nextInt();
            int K = sc.nextInt();
             
            // 셀 입력받기
            int[][] cell = new int[D][W];
            for (int r = 0; r < D; r++) {
                for (int c = 0; c < W; c++) {
                    cell[r][c] = sc.nextInt();
                }
            }
             
            // BFS로 탐색하고 결과 출력
            int result = searchByBFS(cell, D, W, K);
            System.out.printf("#%d %d\n", testCase, result);
        }
        sc.close();
    }
     
    // BFS로 가능한 모든 경우 탐색
    private static int searchByBFS(int[][] cell, int D, int W, int K) {
         
        Queue<State> queue = new ArrayDeque<>();
         
        // NONE(-1)이면 그 행의 값을 변경하지 않는다는 의미
        int[] change = new int[D];
        Arrays.fill(change, NONE);
         
        queue.add(new State(change, 0, 0));
         
        while(!queue.isEmpty()) {
            State s = queue.poll();
            int[] apply = s.apply;
            int index = s.index;
            int count = s.count;
             
            // 모든 열이 보호 성능을 충족하면 현재 변경 횟수를 반환
            if (canProtect(cell, apply, D, W, K)) {
                return count;
            }
             
            // K번 변경하면 무조건 통과하므로 굳이 더 할 필요가 없다.
            if (count >= K - 1) {
                continue;
            }
             
            // 인덱스 위치 이후의 행을 A또는 B로 변경해 본다.
            for (int r = index; r < D; r++) {
                int[] changeA = apply.clone();
                changeA[r] = A;
                queue.add(new State(changeA, r + 1, count + 1));
                 
                int[] changeB = apply.clone();
                changeB[r] = B;
                queue.add(new State(changeB, r + 1, count + 1));
            }
        }
        // count == K - 1까지 못 찾았다면 더 볼 필요 없이 K
        return K;
    }
     
    // 모든 열에 대해 통과하면 true 반환
    private static boolean canProtect(int[][] cell, int[] apply, int D, int W, int K) {
        for (int c = 0; c < W; c++) {
            if (!canColumnProtect(cell, apply, c, D, K)) {
                return false;
            }
        }
        return true;
    }
     
    // 각 열을 검사하는 함수. apply값이 A 또는 B인 행은 해당 값으로 변경해 처리한다.
    private static boolean canColumnProtect(int[][] cell, int[] apply, int c, int D, int K) {
        int cellSum = 0;
 
        for (int r = 0; r < K; r++) {
            cellSum += getCellValue(cell, apply, r, c);
        }
         
        // 전체가 0이거나 전체가 1이거나
        if (cellSum == 0 || cellSum == K) return true;
 
        for (int r = K; r < D; r++) {
            cellSum -= getCellValue(cell, apply, r - K, c);
            cellSum += getCellValue(cell, apply, r, c);
 
            if (cellSum == 0 || cellSum == K) return true;
        }
 
        return false;
    }
     
    // apply를 실제로 적용하는 함수
    private static int getCellValue(int[][] cell, int[] apply, int r, int c) {
        return (apply[r] == NONE) ? cell[r][c] : apply[r];
    }
}
