import java.util.*;

class Solution {
    
    static int[] parent;
    
    public static int find(int x){
        if(parent[x] == x){
            return x;
        }
        
        return parent[x] = find(parent[x]);
    }
    
    public static void union(int a, int b){
        a = find(a);
        b = find(b);
        
        if(a != b){
            parent[b] = a;
        }
    }
    
    public int solution(int n, int[][] costs) {
        int answer = 0;
        parent = new int[n + 1];
        for(int i = 1; i <= n; i++){
            parent[i] = i;
        }
        Arrays.sort(costs, (a, b) -> Integer.compare(a[2], b[2]));
        
        for(int[] cost : costs){
            int from = cost[0];
            int to = cost[1];
            int price = cost[2];
            
            if(find(from) == find(to)) continue;
            
            union(from, to);
            answer += price;
        }
        
        return answer;
    }
}