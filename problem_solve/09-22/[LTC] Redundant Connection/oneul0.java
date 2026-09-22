class Solution {
    int[] parent;
    public int[] findRedundantConnection(int[][] edges) {
        int n = edges.length;
        parent = new int[n+1];
        for(int i = 0; i<=n; i++){
            parent[i] = i;
        }

        for(int[] edge : edges){
            if(find(edge[0]) == find(edge[1])) return edge;
            union(edge[0], edge[1]);
        }
        return new int[2];
    }
    public int find(int a){
        if(a == parent[a]) return a;
        return parent[a] = find(parent[a]);
    }
    public void union(int a, int b){
        a = find(a);
        b = find(b);
        if(a<b) parent[b] = a;
        else parent[a] = b;
    }
}