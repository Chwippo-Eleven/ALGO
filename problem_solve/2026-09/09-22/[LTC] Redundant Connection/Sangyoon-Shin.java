import java.util.*;

class Solution {
    static int[] p;
    public int[] findRedundantConnection(int[][] edges) {

        // 사이클 형성되게 만드는 간선 찾으면 되네
        // 유니온 파인드

        int len = edges.length + 1;
        p = new int[len];

        for (int i = 1; i < len; i++){
            p[i] = i;
        }

        for (int[] cur : edges){
            int x = cur[0];
            int y = cur[1];

            if (find(x) == find(y)){ // 부모 노드 같으면 싸이클 형성
                return new int[] {x, y};
            }
            union(x, y);
        }
        return new int[] {};
    }
    public void union(int x, int y){

        x = find(x);
        y = find(y);

        if (x == y) return;
        p[y] = x;
    }
    public int find(int x){

        if (p[x] == x) return x;
        return find(p[x]);
    }

}