import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {
	private static final int MAX_TOTAL = 100000;
	
	public static void main(String[] args) throws Exception {
		
		// BOJ 1943. 동전 분배
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		int T = 3;
		
		for (int testCase = 1; testCase <= T; testCase++) {
			int N = Integer.parseInt(br.readLine());
			
			// 금액별 동전 개수 & 단위별 합계 저장
			int[][] wallet = new int[N][2];
			
			// 총 금액 기록
			int sum = 0;
			
			for (int i = 0; i < N; i++) {
				StringTokenizer st = new StringTokenizer(br.readLine());
				int unit  = Integer.parseInt(st.nextToken());
				int count = Integer.parseInt(st.nextToken());
				
				wallet[i][0] = unit;
				wallet[i][1] = count;
				
				sum += unit * count;
			}
			
			// 금액 합계가 홀수면 똑같이 나눌 수 없다!
			if (sum % 2 != 0) {
				bw.write("0\n");
				continue;
			}
			
			int target = sum / 2;	// 목표 금액: 합계 / 2
			
			boolean[] reachable = new boolean[MAX_TOTAL + 1];
			reachable[0] = true;
			
			int maxValue = 0;
			
			for (int i = 0; i < N; i++) {
				int unit  = wallet[i][0];
				int count = wallet[i][1];
				
				for (int value = maxValue; value >= 0; value--) {
					if (!reachable[value]) continue;
					
					for (int cnt = 1; cnt <= count; cnt++) {
						reachable[value + unit * cnt] = true;
					}
					
					maxValue = Math.max(maxValue, value + unit * count);
				}
				
				if (reachable[target]) break;
			}
			
			bw.write((reachable[target] ? 1 : 0) + "\n");
		}
		
		bw.flush();
		bw.close();
		br.close();
	}
}
