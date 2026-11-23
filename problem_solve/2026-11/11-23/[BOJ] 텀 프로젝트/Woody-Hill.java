import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public class Main {
	private static final int MAX_N = 100000;
	
	private static int[] teammate = new int[MAX_N + 1];
	private static boolean[] visited = new boolean[MAX_N + 1];
	
	private static Deque<Integer> stack = new ArrayDeque<>();
	
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		int T = Integer.parseInt(br.readLine());
		
		for (int testCase = 1; testCase <= T; testCase++) {
			int N = Integer.parseInt(br.readLine());
			
			StringTokenizer st = new StringTokenizer(br.readLine());
			
			for (int i = 1; i <= N; i++) {
				teammate[i] = Integer.parseInt(st.nextToken());
				visited[i] = false;
			}
			
			int noTeamCount = 0;
			
			stack = new ArrayDeque<>();
			
			for (int i = 1; i <= N; i++) {
				if (visited[i]) continue;
				
				stack.push(i);
				visited[i] = true;
				
				int endPoint = 0;
				
				// 사이클이 만들어질 때까지 stack에 push
				while (true) {
					int next = teammate[stack.peek()];
					
					if (visited[next]) {
						endPoint = next;
						break;
					}
					
					stack.push(next);
					visited[next] = true;
				}
				
				int size = stack.size();
				boolean hasCycle = false;
				
				// 실제 사이클 발생 여부 확인
				while (!stack.isEmpty()) {
					int id = stack.pop();
					
					if (id == endPoint) {
						hasCycle = true;
						break;
					}
				}
				
				// 남은 학생들 처리
				if (hasCycle) {
					noTeamCount += stack.size();
				} else {
					noTeamCount += size;
				}
				stack.clear();
			}
			
			bw.write(noTeamCount + "\n");
		}
		
		bw.flush();
		bw.close();
		br.close();
	}
}
