import java.util.*;

class Solution {
    static int parent[];
    public int solution(int n, int[][] costs) {

        // Kruskal 알고리즘
        // 1. 간선 비용 오름차순으로 정렬
        Arrays.sort(costs, (a, b) -> {
            return Integer.compare(a[2], b[2]);
        });

        parent = new int[n]; // Union-Find를 위해, 부모의 정점을 기록헤야한다.
        for (int i = 0; i < n; i++){
            parent[i] = i;
        }

        int dist = 0; // 선택한 간선들의 합
        int countEdge = 0; // 선택한 간선 개수

        for (int[] edge : costs){
            int v = edge[0];
            int w = edge[1];
            int cost = edge[2];

            if (union(v, w)){
                dist += cost;
                countEdge++;

                if (countEdge == n - 1){ // 간선 개수 = 정점 - 1 => MST 완성된 것.
                    break;
                }
            }
        }
        return dist;
    }
    public int find(int v){
        if (parent[v] == v){
            return v;
        }
        // 여기서 재귀적으로 부모를 따라서 올라감.
        // 그러면 v에서 부모까지의 모든 경로가 부모 노드로 갱신된다.
        return parent[v] = find(parent[v]);
    }
    public boolean union(int a, int b){
        int rootA = find(a);
        int rootB = find(b);

        if (rootA == rootB){ // 두 원소가 같은 집합에 속해있으면 불가능
            return false;
        }
        parent[rootB] = rootA; // 두 원소가 같은 집합에 속해있지 않았다면, 자식을 부모에 속하도록
        return true;
    }
}