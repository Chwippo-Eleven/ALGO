
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Solution {
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		for(int tc=1;tc<=10;tc++) {
			
			
			StringTokenizer st = new StringTokenizer(br.readLine());
			int V = Integer.parseInt(st.nextToken());
			int E = Integer.parseInt(st.nextToken());
			
			st = new StringTokenizer(br.readLine());
			
			List<Integer>[] graph = new ArrayList[V+1];
			int[]indegree=new int[V+1];
			
			for(int i=1;i<=V;i++) {
				graph[i]=new ArrayList<>();
			}
			
			for(int i = 0; i < E ; i++) {
				int a = Integer.parseInt(st.nextToken());
				int b = Integer.parseInt(st.nextToken());
				
				graph[a].add(b);
				indegree[b]++;
			}
			
			
			System.out.print("#"+tc+" ");
			
			
			topologySort(graph, indegree, V);
			
			
			System.out.println();
			
	
		
		}

	}
	private static void topologySort(List<Integer>[] graph, int[]indegree, int V) {
		Queue<Integer> queue = new ArrayDeque<>(); 
		
		//진입 차수 0인 정점 큐에 넣기
		for(int i=1;i<=V;i++) {
			if(indegree[i]==0) {
				queue.offer(i);
			}
		}
		

		while(!queue.isEmpty()) {
			int cur = queue.poll();
			System.out.print(cur+" ");

			for(int next:graph[cur]) {

				indegree[next]--;//인접 정점 진입 차수 1 감소
				
				if(indegree[next]==0)queue.offer(next);
			}
			
		}
	}

}
