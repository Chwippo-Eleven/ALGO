package samsung01;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Solution
{
	
	
	public static void main(String args[]) throws Exception
	{
		BufferedReader br = new BufferedReader(new FileReader("src/samsung01/input.txt"));

		
		
		int T;
		T=Integer.parseInt(br.readLine());

		for(int test_case = 1; test_case <= T; test_case++)
		{
			int N=Integer.parseInt(br.readLine());
			
			int[][]cheese=new int[N][N];

			//Integer.MIN_VALUE로 하면 업데이트 안 될 수도 있다........
			int max=1;
			
			for(int r=0;r<N;r++) {
				
				StringTokenizer st= new StringTokenizer(br.readLine());
				for(int c=0;c<N;c++) {
					cheese[r][c]=Integer.parseInt(st.nextToken());
				}
			}
			
			for(int x=1;x<=100;x++) {
				for(int r=0;r<N;r++) {
					for(int c=0;c<N;c++) {
						//x일차에 먹은 치즈 칸 표시
						if(cheese[r][c]==x) {
							cheese[r][c]=-1;
						}
					}
				}
				
				
				boolean[][]visited=new boolean[N][N];
				int cnt=0;
				
				for(int r=0;r<N;r++) {
					for(int c=0;c<N;c++) {
						if(!visited[r][c]&&cheese[r][c]!=-1) {//bfs한 횟수=덩어리 갯수
							bfs(cheese, visited, N, r, c);
							cnt++;
						}
					}
				}
				max=Math.max(cnt, max);
				
			}
			
			
			System.out.println("#"+test_case+" "+max);
			
		}
	}
	
	public static void bfs(int[][]cheese, boolean[][]visited, int N, int sr, int sc) {
		
		int[]dr= {1,-1,0,0};
		int[]dc= {0,0,1,-1};
		
		Queue<int[]> queue=new ArrayDeque<>();
		queue.offer(new int[] {sr, sc});
		
		visited[sr][sc]=true;
		
		while(!queue.isEmpty()) {
			int[]cur=queue.poll();
			
			
			for(int i=0;i<4;i++) {
				int nr=cur[0]+dr[i];
				int nc=cur[1]+dc[i];
				
				if(nr>=0&&nr<N&&nc>=0&&nc<N&&cheese[nr][nc]!=-1) {
					if(!visited[nr][nc]) {
						visited[nr][nc]=true;
						queue.offer(new int[] {nr, nc});
					}
				}
			}
		}
	}
}
