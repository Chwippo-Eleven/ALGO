import java.util.*;
import java.io.*;

class Solution
{
	static int[] gears;
	static StringTokenizer st;
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

	static int[] dx=  {-1,1,0,0}, dy = {0,0,-1,1};
	public static void main(String args[]) throws Exception
	{
		int T = Integer.parseInt(br.readLine());

		for(int test_case = 1; test_case <= T; test_case++)
		{
			bw.write("#" + test_case + " ");
			int N = Integer.parseInt(br.readLine());
			int[][] arr = new int[N][N];
			for(int i =0; i<N; i++) {
				String line = br.readLine();
				for(int j = 0; j<N; j++) {
					arr[i][j] = line.charAt(j)-'0';
				}
			}
			
			PriorityQueue<int[]> pq = new PriorityQueue<>(
					(a, b) -> a[2] - b[2]
			);
			pq.offer(new int[] {0,0,0});
			int[][] costs = new int[N][N];
			for(int i = 0; i<N; i++) {
				Arrays.fill(costs[i], 987654321);
			}
			while(!pq.isEmpty()) {
				int[] cur = pq.poll();
				
				for(int i= 0; i<4; i++) {
					int nx = cur[0] + dx[i];
					int ny = cur[1] + dy[i];
					if(nx<0 || ny<0 ||nx>=N||ny>=N) continue;
					int newCost = cur[2] + arr[nx][ny];
					if(newCost < costs[nx][ny]) {
						costs[nx][ny] = newCost;
						pq.offer(new int[] {nx,ny,newCost});
					}
				}
			}
			bw.write(costs[N-1][N-1]+"");
			bw.newLine();
		}

		bw.flush();
		br.close();
		bw.close();
	}

}