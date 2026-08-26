import java.util.*;

class Solution {
    
    int[] parent;
    
    public int solution(int n, int[][] costs) {
        int answer = 0;
        int count = 0;
        
        // 비용 기준 정렬
        Arrays.sort(costs, (a, b) -> a[2] - b[2]);
        
        // union-find 초기화
        parent = new int[n];
        
        for(int i = 0; i < n; i++) {
            // 자기자신으로 초기화
            parent[i] = i;
        }
        
        for(int[] e: costs) {
            int a = e[0];
            int b = e[1];
            int cost = e[2];
            
            // 다른 그룹일 때만
            if (find(a) != find(b)){
                union(a, b);
                
                answer += cost;
                count++;
                
                // n개의 섬을 연결하려면 n-1개의 다리가 필요
                if(count == n-1) break;
            }
        }
        
        return answer;
    }
    
    // 대표 노드 찾기
    public int find(int x) {
        if(parent[x] == x)
            return x;
        // 경로 압축
        return parent[x] = find(parent[x]);
    }
    
    // 두 그룹 합치기
    public void union(int a, int b) {
        a = find(a);
        b = find(b);
        // 대표가 다르면 합치기
        if (a != b)
            parent[b] = a;
    }
}
