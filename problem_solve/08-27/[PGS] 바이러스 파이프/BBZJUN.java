import java.util.*;

class Solution {

    int answer = 0;
    List<List<int[]>> list = new ArrayList<>();
    boolean[] vi;
    int n;

    public int solution(int n, int infection, int[][] edges, int k) {

        this.n = n;
        vi = new boolean[n + 1];

        for (int i = 0; i <= n; i++) {
            list.add(new ArrayList<>());
        }

        // 양방향 그래프
        // int[]{연결된 노드, 파이프 종류}
        for (int[] x : edges) {
            list.get(x[0]).add(new int[]{x[1], x[2]});
            list.get(x[1]).add(new int[]{x[0], x[2]});
        }

        // 처음
        vi[infection] = true;

        // k번 파이프를 선택
        TTT(k);

        return answer;
    }
    
    // k번 돌릴 수 있다(감소해서 내려갈거임)
    public void TTT(int countK){
        
        // k번 행동을 모두 사용
        if (countK == 0) {

            int count = 0;

            for (int i = 1; i <= n; i++) {
                if (vi[i]) {
                    count++;
                }
            }

            answer = Math.max(answer, count);
            return;
        }

        
        
         // 파이프 1, 2, 3 중 하나 선택
        for (int pipe = 1; pipe <= 3; pipe++) {

            // 이번 행동에서 새롭게 감염되는 노드
            List<Integer> newInfection = new ArrayList<>();
            
            // 이번 파이프를 통해 감염 확장
            Deque<Integer> dq = new ArrayDeque<>();

            // 현재 감염된 모든 노드에서 시작
            for (int i = 1; i <= n; i++) {
                if (vi[i]) {
                    dq.add(i);
                }
            }

            // BFS
            while (!dq.isEmpty()) {

                int now = dq.poll();

                for (int[] x : list.get(now)) {

                    int next = x[0];
                    int move = x[1];

                    // 선택한 파이프이고 아직 감염되지 않았다면
                    if (move == pipe && !vi[next]) {

                        vi[next] = true;
                        newInfection.add(next);
                        
                        // 해당 파이프로 또 연결되어있을 수 있으니 넣어줌
                        dq.add(next);
                    }
                }
            }

            // 다음 행동
            TTT(countK - 1);

            // 백트래킹, 해당 파이프꺼 다시 돌려서 다른 파이프로 방문 확인해야해서
            for (int next : newInfection) {
                vi[next] = false;
            }
        }
    }
}
