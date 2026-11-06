import java.util.*;
import java.io.*;

class Solution
{
	static StringTokenizer st;
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	
	public static void main(String args[]) throws Exception
	{
		int T = 10;
		for(int test_case = 1; test_case <= T; test_case++)
		{
			st = new StringTokenizer(br.readLine());
			int V = Integer.parseInt(st.nextToken());
			int E = Integer.parseInt(st.nextToken());
			int[] inbound = new int[V+1];
			List<List<Integer>> gr = new ArrayList<>();
			for(int i = 0; i<=V; i++) {
				gr.add(new ArrayList<>());
			}
			st = new StringTokenizer(br.readLine());
			for(int i  =0 ;i<E; i++) {
				int a = Integer.parseInt(st.nextToken());
				int b = Integer.parseInt(st.nextToken());
				inbound[b]++;
				gr.get(a).add(b);
			}
			
			bw.write("#"+test_case+" ");
			int[] arr = solve(inbound, gr, V);
			for(int i = 0; i<V; i++) {
				bw.write(arr[i]+" ");
			}
			bw.newLine();
		}
		bw.flush();
		br.close();
		bw.close();
	}
	
	public static int[] solve(int[] inbound, List<List<Integer>> gr, int V) {
		Queue<Integer> q = new ArrayDeque<>();
		boolean[] visited = new boolean[V+1];
		int idx = 0;
		int[] arr = new int[V];
		for(int i = 1; i<=V; i++) {
			if(inbound[i] == 0) {
				q.offer(i);
				visited[i] = true;
				arr[idx++] = i;
			}
		}
		
		while(!q.isEmpty()){
			int cur = q.poll();
			
			for(int next : gr.get(cur)) {
				inbound[next]--;
				if(inbound[next] == 0) {
					q.offer(next);
					visited[next] = true;
					arr[idx++] = next;
				}
			}
		}
		return arr;
	}
	
}