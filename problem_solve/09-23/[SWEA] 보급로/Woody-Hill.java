import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.PriorityQueue;

public class Solution {
	
	private static final int INF = Integer.MAX_VALUE;
	
	private static int[] dr = {-1, 1, 0, 0};
	private static int[] dc = {0, 0, -1, 1};
	
	private static class State implements Comparable<State> {
		int row;
		int col;
		int accCost;
		
		State(int row, int col, int accCost) {
			this.row = row;
			this.col = col;
			this.accCost = accCost;
		}
		
		@Override
		public int compareTo(State o) {
			return this.accCost - o.accCost;
		}
	}
	
	public static void main(String[] args) throws Exception {
		
		// SWEA 1249. 보급로
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		int T = Integer.parseInt(br.readLine());
		
		for (int testCase = 1; testCase <= T; testCase++) {
			
			int N = Integer.parseInt(br.readLine());
			int[][] cost = new int[N][N];
			
			for (int r = 0; r < N; r++) {
				String str = br.readLine();
				for (int c = 0; c < N; c++) {
					cost[r][c] = str.charAt(c) - '0';
				}
			}
			
			// DijkStra-Like
			int[][] minCost = new int[N][N];
			for (int i = 0; i < N; i++) {
				Arrays.fill(minCost[i], INF);
			}
			
			PriorityQueue<State> pq = new PriorityQueue<>();
			
			minCost[0][0] = 0;
			pq.offer(new State(0, 0, 0));
			
			Dijkstra:
			while (!pq.isEmpty()) {
				State curr = pq.poll();
				int curRow  = curr.row;
				int curCol  = curr.col;
				int curCost = curr.accCost;
				
				for (int dir = 0; dir < 4; dir++) {
					int r = curRow + dr[dir];
					int c = curCol + dc[dir];
					
					if (isIn(r, c, N)) {
						int nextCost = curCost + cost[r][c];
						
						if (nextCost < minCost[r][c]) {
							minCost[r][c] = nextCost;
							
							if (r == N - 1 && c == N - 1) {
								break Dijkstra;
							}
							
							pq.offer(new State(r, c, nextCost));
						}
					}
				}
			}
			// End DijkStra
			
			bw.write("#" + testCase + " " + minCost[N - 1][N - 1] + "\n");
		}
		bw.flush();
		bw.close();
		br.close();
	}
	
	private static boolean isIn(int row, int col, int N) {
		return 0 <= row && row < N && 0 <= col && col < N;
	}
}
