import java.util.*;

class Solution {
    class Node {
        int x;
        int y;
        int number;
        Node left;
        Node right;
        
        Node (int x, int y, int number) {
            this.x = x;
            this.y = y;
            this.number = number;
        }
    }
    
    public int[][] solution(int[][] nodeinfo) {
        int[][] answer = new int [2][nodeinfo.length];
        
        // y 가 제일 큰게 루트가 되어야됨 
        // Node 만들기
        List<Node> nodeList = new ArrayList<>();
        
        for(int i = 0; i < nodeinfo.length; i++){
            nodeList.add(new Node(nodeinfo[i][0], nodeinfo[i][1], i+1));
        }
                
        // y 내림차순, x 오름차순 정렬
        Collections.sort(nodeList, (o1, o2) -> {
            if(o1.y == o2.y) {
                return Integer.compare(o1.x, o2.x);
            }
            return Integer.compare(o2.y, o1.y);
        });
        
        // Node left, rignt 연결
        Node root = nodeList.get(0);
        
        // root에서 내려와서 자리찾기
        for(int i = 1; i < nodeList.size(); i++) {
            insert(root, nodeList.get(i));
        }
        List<Integer> preorderList = new ArrayList<>();
        List<Integer> postorderList = new ArrayList<>();
        preorder(root, preorderList);
        postorder(root, postorderList);
        
        for (int i = 0; i < preorderList.size(); i++){
            answer[0][i] = preorderList.get(i);
            answer[1][i] = postorderList.get(i);
        }
        
        return answer;
    }
    
    // node 연결
    public void insert(Node parent, Node node){
        // x 가 더 작으면 left, 크면 rirgt
        if(node.x < parent.x) {
            if (parent.left == null) parent.left = node;
            else insert(parent.left, node);
        } else {
            if (parent.right == null) parent.right = node;
            else insert(parent.right, node);
        }
    }
    
    // 부모 -> 왼 -> 오
    public void preorder(Node node, List<Integer> list){
        if (node == null)
            return;
        list.add(node.number);
        preorder(node.left, list);
        preorder(node.right, list);
    }
    
    // 왼 -> 오 -> 부모
    public void postorder(Node node, List<Integer> list){
        if (node == null)
            return;
        postorder(node.left, list);
        postorder(node.right, list);
        list.add(node.number);
    }
}
