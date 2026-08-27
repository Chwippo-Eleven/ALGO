import java.util.*;

class Solution {
    int node, edge;
    int[][] costss;
    int[] parent;
    int answer = 0;
    public int solution(int n, int[][] costs) {
        costss = costs;
        node = n;
        edge = costss.length;
        parent = new int[n];
        // 1. 비용 기준 오름차순 정렬
        Arrays.sort(costss, (a,b) -> a[2] - b[2]);
        // 2. 부모노드 초기화
        for (int i=0; i<n; i++) {
            parent[i] = i;
        }
        // 3. 크루스칼 알고리즘 호출
        kruskal();
        
        return answer;
    }
    // 크루스칼
    private void kruskal() {
        for (int i=0; i<edge; i++) {
            if ( find(costss[i][0]) != find(costss[i][1]) ) { // 사이클 판별
                answer += costss[i][2];
                union(costss[i][0], costss[i][1]);
            }
        }
    }
    // 유니온 (부모 설정)
    public void union(int x, int y) {
        int a = find(x);
        int b = find(y);
        
        if (a<b) {
            parent[b] = a;
        } else {
            parent[a] = b;
        }
    }
    // 파인드 (부모 찾기)
    public int find(int x) {
        if (x == parent[x]) return x;
        else return find(parent[x]); // 부모 찾아 거슬러 올라가기 (가장 인덱스 작은)
    }
    
}