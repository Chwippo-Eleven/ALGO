import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {
	private static int[] dr = {-1, 0, 1, 0};
	private static int[] dc = {0, 1, 0, -1};
	
	private static int N, M;
	private static int[][] grid;
	private static boolean[][] isCleaned;
	
	public static void main(String[] args) throws Exception {
		
		// BOJ 14503. 로봇 청소기
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		st = new StringTokenizer(br.readLine());
		
		// 로봇의 현재 위치와 방향
		int r = Integer.parseInt(st.nextToken());
		int c = Integer.parseInt(st.nextToken());
		int d = Integer.parseInt(st.nextToken());
		
		grid = new int[N][M];
		isCleaned = new boolean[N][M];
		
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < M; j++) {
				grid[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		int cleanedCount = 0;
		
		while (true) {
			// 1. 현재 칸이 청소되지 않은 경우 청소하기
			if (!isCleaned[r][c]) {
				isCleaned[r][c] = true;
				cleanedCount++;
			}
			
			// 2. 주변 4칸 중 청소되지 않은 빈칸이 없는 경우
			if (isNearbyCleaned(r, c)) {
				int back = (d + 2) % 4;
				
				// 2-1. 후진 가능하면 후진하기
				if (isMovable(r, c, back)) {
					r += dr[back];
					c += dc[back];
					continue;
					
				// 2-2. 불가능하면 작동 종료
				} else {
					break;
				}
			}
			
			// 3. 청소되지 않은 빈칸이 있는 경우
			
			d = (d + 3) % 4;	// 3-1. 반시계 방향 회전
			
			// 3-2. 앞쪽 칸이 청소되지 않은 빈칸이면 전진
			if (!isMovable(r, c, d)) continue;
			
			int nr = r + dr[d];
			int nc = c + dc[d];
			
			if (!isCleaned[nr][nc]) {
				r = nr;
				c = nc;
			}
		}
		
		bw.write(cleanedCount + "\n");
		
		bw.flush();
		bw.close();
		br.close();
	}
	
	// 주변 4방향이 모두 깨끗한지 확인하는 함수
	private static boolean isNearbyCleaned(int r, int c) {
		for (int d = 0; d < 4; d++) {
			if (!isMovable(r, c, d)) continue;
			
			int nr = r + dr[d];
			int nc = c + dc[d];
			
			if (!isCleaned[nr][nc]) {
				return false;
			}
		}
		return true;
	}
	
	// (r, c)에서 d 방향으로 이동 가능한지 판별하는 함수
	private static boolean isMovable(int r, int c, int d) {
		int nr = r + dr[d];
		int nc = c + dc[d];
		
		boolean isIn = 0 <= nr && nr < N && 0 <= nc && nc < M;
		return isIn && grid[nr][nc] == 0;
	}
}
