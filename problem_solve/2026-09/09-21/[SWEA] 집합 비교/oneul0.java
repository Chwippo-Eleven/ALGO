import java.util.*;
import java.io.*;

class Solution
{
	static int[] gears;
	static StringTokenizer st;
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

	public static void main(String args[]) throws Exception
	{
		int T = Integer.parseInt(br.readLine());

		for(int test_case = 1; test_case <= T; test_case++)
		{
			st = new StringTokenizer(br.readLine());

			int aLen = Integer.parseInt(st.nextToken());
			int bLen = Integer.parseInt(st.nextToken());
			Set<Integer> A = new HashSet<>();
			Set<Integer> B = new HashSet<>();
			
			st = new StringTokenizer(br.readLine());
			for(int i = 0; i<aLen; i++) {
				A.add(Integer.parseInt(st.nextToken()));
			}
			st = new StringTokenizer(br.readLine());			
			for(int i = 0; i<bLen; i++) {
				B.add(Integer.parseInt(st.nextToken()));
			}

			if(A.equals(B)) bw.write("=");
			else if(A.containsAll(B)) bw.write(">");
			else if(B.containsAll(A)) bw.write("<");
			else bw.write("?");
			bw.newLine();
		}

		bw.flush();
		br.close();
		bw.close();
	}

}