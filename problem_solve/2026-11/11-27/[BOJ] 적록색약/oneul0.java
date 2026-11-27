import java.util.*;
import java.io.*;

class Main {
	static int N;
	static char[][] arr;
	static boolean[][] visited;
    public static void main(String args[]) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        N = Integer.parseInt(br.readLine());
        arr = new char[N][N];
        visited = new boolean[N][N];
        for(int i = 0; i<N; i++) {
        	arr[i] = br.readLine().toCharArray();
        }
        
        int a  = 0;
        for(int i = 0; i<N; i++) {
        	for(int j = 0; j<N; j++) {
        		if(!visited[i][j]) {
        			++a;
        			bfs(i, j);
        		}
        	}
        }
        
        visited = new boolean[N][N];
        for(int i = 0; i<N; i++) {
        	for(int j = 0; j<N; j++) {
        		if(arr[i][j] == 'G') {
        			arr[i][j] = 'R';
        		}
        	}
        }
        int b  = 0;
        for(int i = 0; i<N; i++) {
        	for(int j = 0; j<N; j++) {
        		if(!visited[i][j]) {
        			++b;
        			bfs(i, j);
        		}
        	}
        }
        bw.write(a+" "+b);
        bw.flush();
        br.close();
        bw.close();
    }
    
    static int[] dx = {-1,1,0,0}, dy = {0,0,-1,1};
    public static void bfs(int sx, int sy) {
    	Queue<int[]> q = new ArrayDeque<>();
    	q.offer(new int[] {sx,sy});
    	visited[sx][sy] = true;
    	
    	while(!q.isEmpty()) {
    		int[] cur = q.poll();
    		
    		for(int i = 0; i<4; i++) {
    			int nx = cur[0] + dx[i];
    			int ny = cur[1] + dy[i];
    			
    			if(nx<0 || ny<0 || nx>=N || ny>=N || visited[nx][ny]) continue;
    			
				if(arr[sx][sy] == arr[nx][ny]) {
					q.offer(new int[] {nx ,ny});
					visited[nx][ny] = true;
				}
    		}
    	}
    }
}