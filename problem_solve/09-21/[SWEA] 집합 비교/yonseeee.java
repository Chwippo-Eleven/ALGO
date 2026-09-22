
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class Solution {

	public static void main(String[] args) throws Exception{
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int T = Integer.parseInt(br.readLine());
		
		for(int tc=1;tc<=T;tc++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			
			int A_size=Integer.parseInt(st.nextToken());
			int B_size=Integer.parseInt(st.nextToken());
			
			Set<Integer> A=new HashSet<>();
			Set<Integer> B=new HashSet<>();
			
			st = new StringTokenizer(br.readLine());
			for(int i=0;i<A_size;i++) {
				A.add(Integer.parseInt(st.nextToken()));
			}
			st = new StringTokenizer(br.readLine());
			for(int i=0;i<B_size;i++) {
				B.add(Integer.parseInt(st.nextToken()));
			}
						
			if(A.equals(B)) {
				System.out.println("=");
			}else if(A.containsAll(B)) {
				System.out.println(">");
			}else if(B.containsAll(A)) {
				System.out.println("<");
			}else {
				System.out.println("?");
			}

			

		}

	}
	



}
