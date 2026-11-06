class Solution {

    int[] p;

    boolean union(int a, int b) {
        a = find(a);
        b = find(b);

        if (a == b) {
            return false;
        }

        p[b] = a;
        return true;
    }

    int find(int x) {
        if (p[x] == x) {
            return x;
        }

        return p[x] = find(p[x]);
    }

    public int[] findRedundantConnection(int[][] edges) {
        int mx = 0;

        for (int[] e : edges) {
            int a = e[0];
            int b = e[1];

            mx = Math.max(mx, Math.max(a, b));
        }

        p = new int[mx + 1];

        for (int i = 1; i <= mx; i++) {
            p[i] = i;
        }

        for (int[] e : edges) {
            if (!union(e[0], e[1])) {
                return e;
            }
        }

        return new int[0];
    }
}