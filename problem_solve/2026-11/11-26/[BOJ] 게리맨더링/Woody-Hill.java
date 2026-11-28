import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	private static class District {
		int index;
		int population;
		List<District> adjacent;
		
		District(int index, int population) {
			this.index = index;
			this.population = population;
			this.adjacent = new ArrayList<>();
		}
	}
	
	private static int N;
	private static District[] districts;
	private static boolean[] visited;
	
	private static List<Integer> masks;
	
	public static void main(String[] args) throws Exception {
		
		// BOJ 17471. 게리맨더링
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		N = Integer.parseInt(br.readLine());
		districts = new District[1 + N];
		visited = new boolean[1 + N];
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		for (int i = 1; i <= N; i++) {
			int population = Integer.parseInt(st.nextToken());
			
			districts[i] = new District(i, population);
		}
		
		for (int a = 1; a <= N; a++) {
			st = new StringTokenizer(br.readLine());
			int count = Integer.parseInt(st.nextToken());
			
			for (int i = 0; i < count; i++) {
				int b = Integer.parseInt(st.nextToken());
				
				districts[a].adjacent.add(districts[b]);
				districts[b].adjacent.add(districts[a]);
			}
		}
		
		// DFS로 기본적으로 나눠진 선거구 수 파악 - 선거구 인구 수 기록
		List<Integer> popList = new ArrayList<>();
		
		// 방문 배열은 초기화된 상태로 시작
		for (int i = 1; i <= N; i++) {
			if (visited[i]) continue;
			
			popList.add(dfs(i, null));
		}
		
		// 선거구가 이미 3개 이상이면 나눌 수 없다
		if (popList.size() > 2) {
			bw.write("-1\n");
		
		// 선거구가 이미 2개면 방법은 1가지 뿐이다
		} else if (popList.size() == 2) {
			int diff = Math.abs(popList.get(0) - popList.get(1));
			bw.write(diff + "\n");
			
		// 이외에는 선거구가 1개인 경우. 게리맨더링 가능
		} else {
			int minDiff = Integer.MAX_VALUE;
			
			// 1번 구역을 고정한 채로 선거구 분할 방법 생성
			masks = new ArrayList<>();
			createMasks(setBit(0, 1), 2);
			
			for (Integer mask : masks) {
				popList.clear();
				Arrays.fill(visited, false);
				
				popList.add(dfs(1, mask));
				
				for (int i = 2; i <= N; i++) {
					if (visited[i]) continue;
					
					popList.add(dfs(i, null));
				}
				
				// 선거구가 2개로 나눠질 때만 인구 차이 최솟값 갱신
				if (popList.size() == 2) {
					int diff = Math.abs(popList.get(0) - popList.get(1));
					minDiff = Math.min(minDiff, diff);
				}
			}
			
			bw.write(minDiff + "\n");
		}

		// TODO
		
		bw.flush();
		bw.close();
		br.close();
	}
	
	// DFS로 해당 구역의 총 인구수 반환
	private static int dfs(int start, Integer mask) {
		
		int popSum = 0;
		
		Deque<Integer> stack = new ArrayDeque<>();
		
		visited[start] = true;
		stack.push(start);
		popSum += districts[start].population;
		
		while (!stack.isEmpty()) {
			int currIdx = stack.pop();
			List<District> adjacent = districts[currIdx].adjacent;
		
			for (District adj : adjacent) {
				int adjIdx = adj.index;
				
				if (visited[adjIdx]) continue;
				if (mask != null && getBit(mask, adjIdx) == 0) continue;
				
				visited[adjIdx] = true;
				stack.push(adjIdx);
				popSum += adj.population;
			}
		}
		
		return popSum;
	}
	
	// 가능한 모든 비트마스킹 생성
	private static void createMasks(int mask, int index) {
		
		if (index > N) {
			masks.add(mask);
			return;
		}
		
		createMasks(setBit(mask, index), index + 1);
		createMasks(mask, index + 1);
	}
	
	private static int getBit(int mask, int index) {
		return (mask & (1 << index)) > 0 ? 1 : 0;
	}
	
	private static int setBit(int mask, int index) {
		return mask | (1 << index);
	}
}
