import java.util.*;
import java.io.FileInputStream;

class Solution{
	public static void main(String args[]) throws Exception{
		Scanner sc = new Scanner(System.in);
		int T;
		T=sc.nextInt();
        
        int[][] direc = {
            {0, 1}, {0, -1}, {1, 0}, {-1, 0}
        };

		for (int test_case = 1; test_case <= T; test_case++) {
            int answer = 0;
            int n = sc.nextInt();
            int[][] arr = new int[n][n];
            
            for(int i = 0; i < n; i++){
            	for(int j = 0; j < n; j++){
                    arr[i][j] = sc.nextInt();
                }
            }
            
            for(int day = 0; day < 100; day++){
                boolean[][] visited = new boolean[n][n];
                Queue<int[]> queue = new ArrayDeque<>();
                int temp = 0;
                
                for(int i = 0; i < n; i++){
                    for(int j = 0; j < n; j++){
                        
                        if(!visited[i][j] && (arr[i][j] > day)) {
                            temp++; // 덩어리 개수 증가

                            queue.offer(new int[]{i, j});
                            visited[i][j] = true;

                            while(!queue.isEmpty()) {
                                int[] current = queue.poll();
                                int x = current[0];
                                int y = current[1];

                                for(int d = 0; d < 4; d++){
                                    int nextX = x + direc[d][0];
                                    int nextY = y + direc[d][1];

                                    // 오늘과 같거나 작으면 무시
                                    if(nextX < 0 || nextX >= n || nextY < 0 || nextY >= n || visited[nextX][nextY] || arr[nextX][nextY] <= day) {
                                        continue;
                                    }

                                    visited[nextX][nextY] = true;
                                    queue.offer(new int[]{nextX, nextY});
                                }
                            }
                        }
                    }
                }
                answer = Math.max(temp, answer);
            }
            
            System.out.println("#" + test_case + " " + answer);
		}
	}
}
