import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

class Solution {
    
    static class Edge {
        int dest;
        int type;
        
        Edge(int dest, int type) {
            this.dest = dest;
            this.type = type;
        }
    }
    
    public int solution(int n, int infection, int[][] edges, int k) {
        
        // 모든 열고 닫는 경우를 생성해서 sequences에 저장
        List<int[]> sequences = createSequences(k);
        
        // 그래프 생성
        List<Edge>[] graph = new List[n + 1];
        
        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }
        
        for (int[] edge : edges) {
            graph[edge[0]].add(new Edge(edge[1], edge[2]));
            graph[edge[1]].add(new Edge(edge[0], edge[2]));
        }
        
        // 감염 시작점부터 어떤 타입의 파이프를 거쳐야 도달 가능한지 저장
        List<Integer>[] routes = findRoute(n, infection, graph);
        
        int maxInfectCount = 0; // 문제 목표 : 감염 개수의 최댓값
        
        // 각 sequence로 해당 배양체를 감염시킬 수 있는지 확인하는 반복문
        for (int[] seq : sequences) {
            int infectCount = 0;
            
            for (int i = 1; i <= n; i++) {
                // i번 배양체의 타입 경로
                List<Integer> route = routes[i];
                
                int idx = 0;    // seq 조회 인덱스
                
                for (int type : route) {
                    // seq[idx] : 현재 열려 있는 파이프 타입
                    // type == seq[idx]이면 통과 (여러 개 통과 가능!)
                    // 다르면 seq의 다음 타입을 확인한다
                    while (idx < k && type != seq[idx]) {
                        idx += 1;
                    }
                }
                
                // k번 열고 닫는 시퀀스로 도달하지 못했으면 감염시킬 수 없음
                if (idx >= k) { continue; }
                
                // k번을 다 하기 전에 빠져나오면 해당 배양체가 감염되었다는 것
                infectCount += 1;
            }
            // 최댓값 갱신
            maxInfectCount = Math.max(maxInfectCount, infectCount);
        }
        
        return maxInfectCount;
    }
    
    // BFS를 통해 시작점에서 출발해 각 노드에 도착하기까지 거치는 파이프 타입을 기록해 반환
    private List<Integer>[] findRoute(int n, int start, List<Edge>[] graph) {
        
        List<Integer>[] routes = new List[n + 1];
        
        Queue<Integer> queue = new ArrayDeque<>();
        
        routes[start] = new ArrayList<>();
        queue.add(start);
        
        while (!queue.isEmpty()) {
            int current = queue.poll();
            
            for (Edge e : graph[current]) {
                int next = e.dest;
                if (routes[next] != null) { continue; }
                
                routes[next] = new ArrayList<>(routes[current]);
                routes[next].add(e.type);
                
                queue.add(next);
            }
        }
        
        return routes;
    }
    
    // 파이프를 열고 닫는 모든 시퀀스를 생성 (내부 함수 사용)
    private List<int[]> createSequences(int k) {
        
        List<int[]> result = new ArrayList<>();
        
        createSeq(k, 0, new int[k], result);
        
        return result;
    }
    
    // 내부 함수는 DFS 형태로 작동하며 같은 파이프를 연속으로 여닫는 경우는 제외
    private void createSeq(int k, int depth, int[] seq, List<int[]> result) {
        if (depth == k) {
            result.add(seq.clone());
            return;
        }
        
        if (depth == 0) {
            seq[depth] = 1;
            createSeq(k, depth + 1, seq, result);
            seq[depth] = 2;
            createSeq(k, depth + 1, seq, result);
            seq[depth] = 3;
            createSeq(k, depth + 1, seq, result);
        } else {
            int prev = seq[depth - 1];
            if (prev != 1) {
                seq[depth] = 1;
                createSeq(k, depth + 1, seq, result);
            }
            if (prev != 2) {
                seq[depth] = 2;
                createSeq(k, depth + 1, seq, result);
            }
            if (prev != 3) {
                seq[depth] = 3;
                createSeq(k, depth + 1, seq, result);
            }
        }
    }
}
