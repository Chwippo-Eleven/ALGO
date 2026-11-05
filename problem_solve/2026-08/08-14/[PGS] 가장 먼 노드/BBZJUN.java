import java.util.*;

class Solution {
    public int solution(int n, int[][] edge) {
        int answer = 0; // 정답 누적
        int maxdep = 0; // 최대 깊이 저장
        List<Integer>[] list = new ArrayList[n + 1]; // list배열 생성
        int[] dep = new int[n+1]; // 각각의 노드의 깊이
        boolean[] vi = new boolean[n+1]; // 방문 여부
        for (int i=1; i<=n; i++){
            list[i] = new ArrayList<>();//초기화
        }
        
        // 그래프 연결
        for (int i=0; i<edge.length; i++){
            int a = edge[i][0];
            int b = edge[i][1];
            list[a].add(b);
            list[b].add(a);
        }
        
        Deque<int[]> dq = new ArrayDeque<>();//큐로 써줄거임
        dq.addLast(new int[]{1,0}); // 1시작, 깊이0
        vi[1] = true;
        while (!dq.isEmpty()){
            int[] poll = dq.pollFirst();
            int g = poll[0]; // 현재 노드가 뭔지
            int depth = poll[1]; // 현재 노드의 깊이
            
            for (int next=0; next<list[g].size(); next++){ // 현재 노드에서 이어진 노드들 탐색
                if (!vi[list[g].get(next)]){ // 방문안했으면
                    vi[list[g].get(next)] = true; // 방문처리
                    dep[list[g].get(next)] = depth+1; //깊이 갱신
                    dq.addLast(new int[]{list[g].get(next), depth+1});//다음에 넣어줌 
                    maxdep = Math.max(maxdep, depth+1); // 최대 깊이 갱신
                }
            }
        }
        
        for (int d : dep){ // 최대 깊이랑 같은거 체크
            if (d==maxdep){
                answer++;
            }
        }
        
        return answer;
    }
}
