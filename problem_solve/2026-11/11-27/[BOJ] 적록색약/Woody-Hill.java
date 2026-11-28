import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {
	private static int[] dr = {-1, 1, 0, 0};
	private static int[] dc = {0, 0, -1, 1};
	
	private static int N;
	private static char[][] grid;
	private static boolean[][] visited;
	
	public static void main(String[] args) throws Exception {
		
		// BOJ 10026. 적록색약
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		N = Integer.parseInt(br.readLine());
		grid = new char[N][];
		
		for (int r = 0; r < N; r++) {
			grid[r] = br.readLine().toCharArray();
		}
		
		int normal = getVisibleSectors(false);			// 색약 X
		int colorWeakness = getVisibleSectors(true);	// 색약 O
			
		bw.write(normal + " " + colorWeakness + "\n");
		
		bw.flush();
		bw.close();
		br.close();
	}
	
	// 적록 색약 보유 여부를 매개변수로 전달
	private static int getVisibleSectors(boolean hasCW) {
		int sectorCount = 0;
		
		visited = new boolean[N][N];
		
		for (int r = 0; r < N; r++) {
			for (int c = 0; c < N; c++) {
				if (visited[r][c]) continue;
				
				sectorCount++;
				dfs(r, c, grid[r][c], hasCW);
			}
		}
		
		return sectorCount;
	}
	
	private static void dfs(int r, int c, char color, boolean hasCW) {
		
		for (int d = 0; d < 4; d++) {
			int nr = r + dr[d];
			int nc = c + dc[d];
			
			if (!isIn(nr, nc) || visited[nr][nc]) continue;
			
			char nextColor = grid[nr][nc];
			
			if (nextColor == color || (hasCW && color != 'B' && nextColor != 'B')) {
				visited[nr][nc] = true;
				dfs(nr, nc, color, hasCW);
			}
		}
	}
	
	private static boolean isIn(int r, int c) {
		return 0 <= r && r < N && 0 <= c && c < N;
	}
}
