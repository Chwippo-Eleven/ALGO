import java.math.BigInteger;
import java.util.Scanner;

public class Solution {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		long T = sc.nextInt();
		
		for (int t=0 ; t<T ; t++) {
			String a = sc.next();
			BigInteger a_int = new BigInteger(a);
			BigInteger b = new BigInteger(sc.next());
			
			System.out.printf("#%d ",t+1);
			System.out.println(a_int.add(b));
		}
	}
}
