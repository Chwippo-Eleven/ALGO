import java.util.*;
import java.io.*;

class Solution
{
	static int D, W, K;
	public static void main(String args[]) throws Exception
	{
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		int T = Integer.parseInt(br.readLine());
		

		for(int test_case = 1; test_case <= T; test_case++)
		{
			bw.write("#"+test_case+" ");
			
			StringTokenizer st = new StringTokenizer(br.readLine());
			D = Integer.parseInt(st.nextToken());
			W = Integer.parseInt(st.nextToken());
			K = Integer.parseInt(st.nextToken());
			
			int[][] arr =new int[D][W];
			for(int i = 0; i<D; i++) {
				st= new StringTokenizer(br.readLine());
				for(int j = 0; j<W; j++) {
					arr[i][j] = Integer.parseInt(st.nextToken());
				}
			}
			
			//col 방향으로 돌린 마스크 생성
			//그림의 방향과는 다른데 나열 순서만 맞으면 될 것 같아서 그냥 함
			int[] cols = new int[W];
			for(int c = 0; c<W; c++) {
				int mask = 0;
				for(int r = 0; r<D; r++) {
					if(arr[r][c] == 1) mask = turnOn(mask, r);
				}
				cols[c] = mask;
			}
			
			if(canPass(cols) || canPass(cols)) {
				bw.write("0");
			}
			else {
				//조합 찾기
				bw.write(makeComb(cols, 0, 0)+"");
			}
			bw.newLine();
		}
		bw.flush();
		br.close();
		bw.close();
	}
	
	//각 col 체크하면서 통과했는지 체크
	public static boolean canPass(int[] cols) {
		for(int col : cols) {
			if(!isOnInRange(col)) {
				return false;
			}
		}
		return true;
	}
	
	//약품 부을 row 결정하는 조합
	public static int makeComb(int[] cols, int start, int depth) {
		
		//통과되는지?
		if(canPass(cols)) return depth;
		
		if(depth == K) {
			return K;
		}
		
		int minVal = 987654321;
		//comb
		for(int i = start; i<D; i++) {
			//row 0또는1로 만들어서 넘기기
			minVal = Math.min(minVal, makeComb(turnRowOn(cols, i, 0), i+1, depth+1));
			minVal = Math.min(minVal, makeComb(turnRowOn(cols, i, 1), i+1, depth+1));
		}
		
		return minVal;
	}
	public static int[] copyArr(int[] arr) {
		int[] tmp = new int[arr.length];
		for(int i = 0; i<tmp.length; i++) {
			tmp[i] = arr[i];
		}
		return tmp;
	}
	
	//행 다 켜기
	public static int[] turnRowOn(int[] masks, int targetRow, int val) {
		int[] tmp = copyArr(masks);
		//A:0 또는 B:1
		if(val == 0) {
			for(int c = 0; c<tmp.length; c++) {
				tmp[c] = turnOff(tmp[c], targetRow);
			}
		}
		else {
			for(int c = 0; c<tmp.length; c++) {
				tmp[c] = turnOn(tmp[c], targetRow);
			}
		}
		return tmp;
	}
	
	//방문체크용
	public static boolean isOn(int mask, int idx) {
		return (mask & (1<<idx)) != 0; 
	}
	
	public static int turnOn(int mask, int idx) {
		return mask | (1<<idx);
	}
	public static int turnOff(int mask, int idx) {
		return mask & ~(1<<idx);
	}
	
	//각 col의 특정 구간을 찾음
	public static boolean isOnInRange(int col) {
		int checkMask = (1<<K)-1;
		
		for(int i = 0; i<=D-K; i++) {
			int window = (col>>i) & checkMask;
			if(window == 0 || window == checkMask) {
				return true;
			}
		}
		//아니면 실패
		return false;
	}
}
/*
 * 그냥 세기
 * 
 */