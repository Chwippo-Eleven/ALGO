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
		
		int[]dr= {-1,1,0,0};
		int[]dc= {0,0,-1,1};
		//위, 아래, 왼쪽, 오른쪽
		int d=0;
		char[]dir= {'^','v','<','>'};
		
		for(int test_case = 1; test_case <= T; test_case++)
		{
			StringTokenizer st= new StringTokenizer(br.readLine());
			int H=Integer.parseInt(st.nextToken());
			int W=Integer.parseInt(st.nextToken());
			
			char[][]map=new char[H][W];
			
			int cr=0, cc=0;
			
			for(int i=0;i<H;i++) {
				String line=br.readLine();
				for(int j=0;j<line.length();j++) {
					map[i][j]=line.charAt(j);
					
					
					for(int k=0;k<4;k++) {
						if(dir[k]==map[i][j]) {
							cr=i;cc=j;
							d=k;
						}
					}
				}
			}
			
			
			int N=Integer.parseInt(br.readLine());
			String input=br.readLine();
			for(int i=0;i<N;i++) {
				char ch=input.charAt(i);
				
				switch(ch) {
				case 'U':
					d=0;
					if(cr-1>=0&&map[cr-1][cc]=='.') {
						map[cr--][cc]='.';
					}
					break;
				case 'D':
					d=1;
					if(cr+1<H&&map[cr+1][cc]=='.') {
						map[cr++][cc]='.';
					}
					break;
				case 'L':
					d=2;
					if(cc-1>=0&&map[cr][cc-1]=='.') {
						map[cr][cc--]='.';
					}
					break;
				case 'R':
					if(cc+1<W&&map[cr][cc+1]=='.') {
						map[cr][cc++]='.';
					}
					d=3;
					break;
				case 'S':
					int cnt=1;
					while(true) {
						int nr=cr+dr[d]*cnt;
						int nc=cc+dc[d]*cnt;
						cnt++;
						
					
						if(!(nr>=0&&nr<H&&nc>=0&&nc<W)) {
							break;
						}
						if(map[nr][nc]=='*') {
							map[nr][nc]='.';
							break;
						}
						if(map[nr][nc]=='#') {
							break;
						}
						
					}
					break;
				}
			}
			
			map[cr][cc]=dir[d];
			
			
			System.out.print("#"+test_case+" ");
			
			for(int i=0;i<H;i++) {
				for(int j=0;j<W;j++) {
					System.out.print(map[i][j]);
				}
				System.out.println();
			}
			
		}
	}
	

}
