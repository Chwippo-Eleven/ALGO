
import java.util.*;
import java.io.*;

class Solution
{
	static int N;
	static int[][] grid;

	public static void main(String args[]) throws Exception{
	
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        int T = Integer.parseInt(br.readLine());

		for(int test_case = 1; test_case <= T; test_case++){
			StringTokenizer st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			String direction = st.nextToken();
			
			grid = new int[N][N];
			
			for(int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine());
				for(int j = 0; j < N; j++) {
					grid[i][j] = Integer.parseInt(st.nextToken());
				}
			}
			
			System.out.println("#"+test_case);
			
			switch(direction) {
				case "up":
					up();
					break;
				case "down":
					down();
					break;
				case "left":
					left();
					break;
				case "right":
					right();
					break;
			}
			
		}
		
		

	}
	

	public static void up() {
		for(int j = 0; j < N; j++) {
			for(int i = 0; i < N; i++) {
				int cur = grid[i][j];
				if(cur == 0) continue;
				for(int k = i + 1 ; k < N ; k++) {
					if(grid[k][j] == 0)continue;
					if(grid[k][j] != cur)break;
					if(grid[k][j] == cur) {
						grid[i][j] *= 2;
						grid[k][j] = 0;
						i=k;break;
						
					}
				}
			}
			
		}
		
		int[][] result = new int[N][N];
		
		for(int j = 0; j < N; j++) {
			int idx = 0;
			for(int i = 0; i < N; i++) {
				if(grid[i][j] !=0 )result[idx++][j]=grid[i][j];
			}
		}
		
		print(result);
		
	}
	public static void down() {
		for(int j = 0; j < N; j++) {
			for(int i = N-1; i >= 0; i--) {
				int cur = grid[i][j];
				if(cur == 0) continue;
				for(int k = i - 1 ; k >= 0 ; k--) {
					if(grid[k][j] == 0)continue;
					if(grid[k][j] != cur)break;
					if(grid[k][j] == cur) {
						grid[i][j] *= 2;
						grid[k][j] = 0;
						i=k;break;
						
					}
				}
			}
			
		}
		
		int[][] result = new int[N][N];
		
		for(int j = 0; j < N; j++) {
			int idx = N-1;
			for(int i = N-1; i >= 0; i--) {
				if(grid[i][j] !=0 )result[idx--][j]=grid[i][j];
			}
		}
		print(result);
		
	}

	
	public static void left() {
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				int cur = grid[i][j];
				if(cur == 0) continue;
				for(int k = j +1 ; k < N ; k++) {
					if(grid[i][k] == 0)continue;
					if(grid[i][k] != cur)break;
					if(grid[i][k] == cur) {
						grid[i][j] *= 2;
						grid[i][k] = 0;
						j=k;break;
						
					}
				}
			}
			
		}
		
		int[][] result = new int[N][N];
		
		for(int i = 0; i < N; i++) {
			int idx = 0;
			for(int j = 0; j < N; j++) {
				if(grid[i][j] !=0 )result[i][idx++]=grid[i][j];
			}
		}
		
		
		print(result);
		
		
	}
	
	public static void right() {
		for(int i = 0; i < N; i++) {
			for(int j = N - 1; j >= 0; j--) {
				int cur = grid[i][j];
				if(cur == 0) continue;
				for(int k = j - 1; k >= 0 ; k--) {
					if(grid[i][k] == 0)continue;
					if(grid[i][k] != cur)break;
					if(grid[i][k] == cur) {
						grid[i][j] *= 2;
						grid[i][k] = 0;
						j=k;break;
						
					}
				}
			}
			
		}
		
		int[][] result = new int[N][N];
		
		for(int i = 0; i < N; i++) {
			int idx = N-1;
			for(int j = N-1; j >= 0; j--) {
				if(grid[i][j] !=0 )result[i][idx--]=grid[i][j];
			}
		}
		
		
		print(result);
		
		
	}
    public static void print(int[][] result) {
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				System.out.print(result[i][j]+" ");
			}
			System.out.println();
		}
		
	}
}


