import java.util.*;

// 자바로 트리 구현하는 법
// 1. Node 클래스 만들기 -> 필요한 값(여기서는 2차원 좌표랑 원래 idx 값이 필요해서 3개), 왼쪽 자식, 오른쪽 자식
// 2. Node 배열을 만들어서, 각각의 노드 생성
// 3. Node 배열을 문제 조건에 맞게 정렬
// 4. nodes[0] 을 root 노드로 잡기
// 5. 각 노드들을 트리에 삽입하는 insert() 함수 구현
// 6. 문제 요구 조건에 맞는 순회 함수 구현

class Solution {
    static class Node{
        int x;
        int y;
        int idx;
        Node left;
        Node right;
        Node(int x, int y, int idx){ // 생성된 노드는 양쪽이 어차피 null이니까 생성자에 포함 x
            this.x = x;
            this.y = y;
            this.idx = idx;
        }
    }
    public int[][] solution(int[][] nodeinfo) {

        // 노드 생성
        Node[] nodes = new Node[nodeinfo.length];
        for (int i = 0; i < nodeinfo.length; i++){
            nodes[i] = new Node(nodeinfo[i][0], nodeinfo[i][1], i + 1);
        }

        // 부모 노드를 자식 노드보다 먼저 트리에 넣기 위한 정렬 과정
        // 1. y값 내림차순
        // 2. y값 같으면 x값 기준으로 오름차순 정렬해서 왼쪽부터 처리
        Arrays.sort(nodes, (n1, n2) -> {
            if (n1.y != n2.y){
                return Integer.compare(n2.y, n1.y);
            }
            return Integer.compare(n1.x, n2.x);
        });

        // root 노드 지정
        Node root = nodes[0];

        // 나머지 노드들 삽입
        for (int i = 1; i < nodes.length; i++){
            insert(root, nodes[i]);
        }

        // 전위, 후위 순회 각각 진행
        List<Integer> preorderResult = new ArrayList<>();
        preorder(root, preorderResult);

        List<Integer> postorderResult = new ArrayList<>();
        postorder(root, postorderResult);

        int[][] res = new int[2][nodes.length];

        for (int i = 0; i < nodes.length; i++){
            res[0][i] = preorderResult.get(i);
        }

        for (int i = 0; i < nodes.length; i++){
            res[1][i] = postorderResult.get(i);
        }
        return res;
    }

    // 트리에 노드 삽입하는 함수
    static void insert(Node parent, Node child){
        if (child.x < parent.x){ // 현재 root 노드의 x값보다 넣으려는 노드의 x값이 작으면 -> 왼쪽으로
            if (parent.left == null){ // 1. root의 왼쪽이 비어있으면 바로 넣으면 됨.
                parent.left = child;
            } else { // 2. root의 왼쪽이 비어있지 않으면 -> 그 노드를 root로 두고(root를 진짜로 바꾸는 건 아님), child와 다시 비교.
                // 재귀로 구현한거고, 언젠가는 왼쪽이 null인 부분을 만나게 되기에 탈출 가능함.
                insert(parent.left, child);
            }
        } else {
            // 현재 root 노드의 x값보다 넣으려는 노드의 x값이 큰 경우
            if (parent.right == null){
                parent.right = child;
            } else {
                insert(parent.right, child);
            }
        }
    }

    // 전위 순회 함수 = 자신 -> 왼쪽 -> 오른쪽
    static void preorder(Node node, List<Integer> result){
        if (node == null){
            return;
        }
        result.add(node.idx); // 현재 노드 저장하고
        preorder(node.left, result); // 왼쪽
        preorder(node.right, result); // 오른쪽 순서로 순회
    }

    // 후위 순회 함수 = 왼쪽 -> 오른쪽 -> 자신
    static void postorder(Node node, List<Integer> result){
        if (node == null){
            return;
        }
        postorder(node.left, result);
        postorder(node.right, result);
        result.add(node.idx);
    }
}