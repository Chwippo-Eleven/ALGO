import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Solution {
	
	static int[] dx = {-1, 1, 0, 0};	// x: row
	static int[] dy = {0, 0, -1, 1};	// y: column
	
	static int N;
	static int[][] cheese;
	static boolean[][] visited;
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		int T = Integer.parseInt(br.readLine());
		 
		for (int testCase = 1; testCase <= T; testCase++) {
		    StringTokenizer st = new StringTokenizer(br.readLine());
			
			N = Integer.parseInt(st.nextToken());
		    cheese = new int[N][N];
		    
		    // (치즈의 최댓값 == 가장 마지막 날) 기록
		    int finalDay = 0;
		    
		    for (int x = 0; x < N; x++) {
		    	st = new StringTokenizer(br.readLine());
		    	for (int y = 0; y < N; y++) {
		    		cheese[x][y] = Integer.parseInt(st.nextToken());
		    		finalDay = Math.max(finalDay, cheese[x][y]);
		    	}
		    }
		    
		    // 1일부터 마지막 날까지 Backtracking으로 치즈의 최대 분할 찾기
		    int maxSplit = dayByDay(finalDay);
		    bw.write(String.format("#%d %d\n", testCase, maxSplit));
		}
		bw.flush();
		bw.close();
		br.close();
    }
	
	// 모든 날짜 중 치즈 덩어리 개수가 제일 많은 날을 반환하는 함수
	private static int dayByDay(int finalDay) {
		int maxSplit = 1;
		
		for (int day = 1; day < finalDay; day++) {
			// 매번 방문배열 초기화하기!
			visited = new boolean[N][N];
			int curSplit = 0;
			
			for (int x = 0; x < N; x++) {
				for (int y = 0; y < N; y++) {
					// 아직 먹히지 않은 곳(cheese > day)만 시작점으로 해서 탐색
					if (!visited[x][y] && cheese[x][y] > day) {
						backtrack(day, x, y);
						curSplit += 1;
					}
				}
			}
			maxSplit = Math.max(maxSplit, curSplit);
		}
		
		return maxSplit;
	}
	
	private static void backtrack(int day, int x, int y) {
		// 이미 먹힌 곳(cheese <= day)이면 멈추기
		if (cheese[x][y] <= day) {
			visited[x][y] = true;
			return;
		}
		
		visited[x][y] = true;
		
		// 4방향 이동
		for (int d = 0; d < 4; d++) {
			int nx = x + dx[d];
			int ny = y + dy[d];
			
			if (!isIn(nx, ny) || visited[nx][ny]) continue;
			
			backtrack(day, nx, ny);
		}
	}
	
	private static boolean isIn(int x, int y) {
		return 0 <= x && x < N && 0 <= y && y < N;
	}
}
