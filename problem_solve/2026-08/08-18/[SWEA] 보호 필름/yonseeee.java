package swea;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Solution {
	
	static int ans;
	static int[][]arr;
	static int D,W,K;
	public static void main(String[] args) throws Exception{
		
		BufferedReader br = new BufferedReader(new FileReader("src/swea/input.txt"));
//		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T=Integer.parseInt(br.readLine());
		
		for(int tc=1;tc<=T;tc++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			
			D=Integer.parseInt(st.nextToken());
			W=Integer.parseInt(st.nextToken());
			K=Integer.parseInt(st.nextToken());
			
			
			arr=new int[D][W];
			
			for(int i=0;i<D;i++) {
				st = new StringTokenizer(br.readLine());
				for(int j=0;j<W;j++) {
					arr[i][j]=Integer.parseInt(st.nextToken());
				}
			}
			
			
			ans=K;
			
			
			dfs(0,0);
			
			System.out.println("#"+tc+" "+ans);
				
		}

	}
	
	public static void dfs(int row, int count) {
		
		if(count>=ans) {
			return;
		}
		
		if(check()) {
			ans=count;
			return;
			
		}
		if(row==D) {
			return;
		}
		
		int[]tmp=arr[row].clone();
		
		//현재 행 그대로
		dfs(row+1, count);
		
		
		//A투입 
		for(int j=0;j<W;j++) {
			arr[row][j]=0;
		}
		dfs(row+1, count+1);
		arr[row]=tmp.clone();
		
		//B 투입
		
		for(int j=0;j<W;j++) {
			arr[row][j]=1;
		}
		dfs(row+1, count+1);
		arr[row]=tmp;
		
		
	}
	
	public static boolean check() {
		for(int j=0;j<W;j++) {
			
			int cnt=1;
			boolean pass=false;
			for(int i=1;i<D;i++) {
				if(arr[i][j]==arr[i-1][j]) {
					cnt++;
				}else {
					cnt=1;
				}
				
				if(cnt>=K) {
					pass=true;
					break;
				}
			}
			
			if(!pass)return false;
		}
		
		return true;
	}

}
