import java.util.*;

class Solution {

    class Node {
        int idx;
        int x,y;
        Node left;
        Node right;
        Node(int idx, int x, int y) {
            this.idx = idx;
            this.x = x;
            this.y = y;
        }
    }
    int n;
    int[] preCount = {0};
    int[] postCount = {0};
    public int[][] solution(int[][] nodeinfo) {
        n = nodeinfo.length;
        Node root = null;

        // 원활한 생성을 위해 y값 기준 내림차순 정렬
        Integer [] order = new Integer[n]; // 내림차순 정렬된, 인덱스 저장용
        for (int i=0; i<n; i++) order[i] = i;

        Arrays.sort(order, (a,b) -> nodeinfo[b][1] - nodeinfo[a][1]);

        for (int i=0; i<n; i++) {
            int idx = order[i]+1;
            int x = nodeinfo[order[i]][0];
            int y = nodeinfo[order[i]][1];
            root = insert(root,  idx, x, y);
        }

        int[][] answer = new int[2][n];

        preOrder(root, answer);
        postOrder(root, answer);

        return answer;
    }

    // 이진 트리 생성 함수 [O]
    Node insert(Node root, int idx, int x, int y) {
        // 객체 생성
        Node newNode = new Node(idx, x, y);

        if (root == null) return newNode; // root가 없으면 니가 root가 되어라.

        Node cur = root;
        while(true) {
            if (x < cur.x) {
                if (cur.left == null) {
                    cur.left = newNode;
                    break;
                }
                cur = cur.left; // 한 층 내려옴
            } else {
                if (cur.right == null) {
                    cur.right = newNode;
                    break;
                }
                cur = cur.right;
            }
        }
        return root;
    }

    // 전위순회: Root → Left → Right
    private void preOrder(Node node, int[][] answer) {
        if (node == null) return;
        answer[0][preCount[0]++] = node.idx; // 이걸 answer에 입력하는 걸로 바꾸기 [O]
        preOrder(node.left,  answer);
        preOrder(node.right, answer);
    }

    // 후위순회: Left → Right → Root
    private void postOrder(Node node, int[][] answer) {
        if (node == null) return;
        postOrder(node.left,  answer);
        postOrder(node.right, answer);
        answer[1][postCount[0]++] = node.idx; // 이걸 answer에 입력하는 걸로 바꾸기 [O]
    }


}