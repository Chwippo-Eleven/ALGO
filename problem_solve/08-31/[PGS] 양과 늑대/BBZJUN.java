import java.util.*;

class Solution {

    int[] info;
    List<List<Integer>> tree = new ArrayList<>();
    int answer = 0;

    public int solution(int[] info, int[][] edges) {

        this.info = info;

        // 트리 생성
        for (int i = 0; i < info.length; i++) {
            tree.add(new ArrayList<>());
        }

        for (int[] edge : edges) {
            int parent = edge[0];
            int child = edge[1];

            tree.get(parent).add(child);
        }

        // 처음에는 0번만 방문 가능
        List<Integer> candidates = new ArrayList<>();
        candidates.add(0);

        dfs(0, 0, candidates);

        return answer;
    }

    void dfs(int sheep, int wolf, List<Integer> candidates) {

        // 현재 양의 최댓값 갱신
        answer = Math.max(answer, sheep);

        // 현재 갈 수 있는 모든 노드를 하나씩 선택
        for (int i = 0; i < candidates.size(); i++) {

            int next = candidates.get(i);

            int nextSheep = sheep;
            int nextWolf = wolf;

            // 다음 노드 방문
            if (info[next] == 0) {
                nextSheep++;
            } else {
                nextWolf++;
            }

            // 늑대가 양 이상이면 갈 수 없음
            if (nextSheep <= nextWolf) {
                continue;
            }

            // 새로운 후보 목록 생성
            List<Integer> nextCandidates =
                    new ArrayList<>(candidates);

            // 현재 방문한 노드는 후보에서 제거
            nextCandidates.remove(i);

            // 현재 노드의 자식들을 후보에 추가
            nextCandidates.addAll(tree.get(next));

            // 다음 상태로 이동
            dfs(nextSheep, nextWolf, nextCandidates);
        }
    }
}
