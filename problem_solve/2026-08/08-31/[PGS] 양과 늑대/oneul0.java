import java.util.*;
class Solution {
    int n, answer = 0;
    int[] info;
    List<List<Integer>> gr = new ArrayList<>();
    public int solution(int[] info, int[][] edges) {
        this.n = info.length;
        this.info = info;

        for(int i = 0; i<n; i++){
            gr.add(new ArrayList<>());
        }
        for(int[] edge : edges){
            gr.get(edge[0]).add(edge[1]);
        }
        Set<Integer> canGo = new HashSet<>();
        canGo.add(0);
        dfs(0, 0, canGo);
        return answer;
    }
    public void dfs(int sheep, int wolf, Set<Integer> canGo){
        answer = Math.max(answer, sheep);

        for(int next : canGo){
            int nextS = sheep;
            int nextW = wolf;

            if(info[next] == 1) nextW++;
            else nextS++;

            if(nextW>=nextS) continue;

            Set<Integer> newCanGo = new HashSet<>(canGo);
            newCanGo.remove(next);
            newCanGo.addAll(gr.get(next));

            dfs(nextS, nextW, newCanGo);
        }
    }
}
//한번 갔던 노드는 다시 갈 수 있음이 보장됨
//set을 이용해서 평균 O(1)로 노드로 이동할 수 있음
//2차원 리스트를 이용해서 트리의 노드를 하나씩 순회할 경우 양방향으로 관리해야 문제의 제약을 지킬 수 있지만
//이동할 수 있는 노드를 관리하는 배열을 사용한다면 각각의 노드에서 이동할 수 있는 모든 노드를 수집하며 경우의 수를 쌓아갈 수 있다
//또한, 최대로 모을 수 있는 양의 수를 각 상태에서 업데이트한다면 기저조건에 넣지 않아도 최대값을 구할 수 있다.