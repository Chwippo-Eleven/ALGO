class Solution {
    
    int[] parent;
    int[] rank;
    
    public int[] findRedundantConnection(int[][] edges) {
        int n = edges.length;

        parent = new int[1 + n];
        rank   = new int[1 + n];

        for (int i = 1; i <= n; i++) {
            parent[i] = i;
        }

        for (int[] edge : edges) {
            if (!union(edge[0], edge[1])) {
                return edge;
            }
        }

        return new int[] {0, 0};
    }

    private int find(int x) {
        if (parent[x] == x) return x;

        parent[x] = find(parent[x]);
        return parent[x];
    }

    private boolean union(int a, int b) {
        a = find(a);
        b = find(b);

        if (a == b) return false;

        if (rank[a] < rank[b]) {
            int tmp = a;
            a = b;
            b = tmp;
        }

        parent[b] = a;

        if (rank[a] == rank[b]) {
            rank[a] += 1;
        }

        return true;
    }
}
