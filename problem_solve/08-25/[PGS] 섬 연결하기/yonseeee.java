import java.util.*;

class Solution {
    int[] parent;
    public int solution(int n, int[][] costs) {
        int answer = 0;
        parent = new int[n];
        
        for(int i=0;i<n;i++){
            parent[i]=i;
        }
        
        Arrays.sort(costs, (a,b)->a[2]-b[2]);
        
        for(int []cost:costs){
            int a=cost[0];
            int b=cost[1];
            int c=cost[2];
            
            if(find(a)!=find(b)){
                union(a,b);
                answer+=c;
            }
        }
        return answer;
    }
    
    private int find(int x){
        if(parent[x]==x){
            return x;
        }
        
        return find(parent[x]);

    }
    
    private void union(int a, int b){
        a=find(a);
        b=find(b);
        
        if(a!=b){
            parent[b]=a;
        }
}
}
