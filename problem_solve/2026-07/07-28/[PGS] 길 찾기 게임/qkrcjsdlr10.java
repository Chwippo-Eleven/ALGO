import java.util.*;

class Solution {
    
    class Node{
        int id;
        int x;
        int y;
        Node left;
        Node right;
    }
    
    public static void insert(Node parent, Node child){
        if(child.x < parent.x){
            if(parent.left == null){
                parent.left = child;    
            }else{
                insert(parent.left, child);
            }
        }else{
            if(parent.right == null){
                parent.right = child;    
            }else{
                insert(parent.right, child);
            }
        }
    }

    public static void preOrder(Node node, List<Integer> preNodes){
        if(node == null) return;
        
        preNodes.add(node.id);
        preOrder(node.left, preNodes);
        preOrder(node.right, preNodes);
    }

    public static void postOrder(Node node, List<Integer> postNodes){
        if(node == null) return;
        
        postOrder(node.left, postNodes);
        postOrder(node.right, postNodes);
        postNodes.add(node.id);
    }
    
    public int[][] solution(int[][] nodeinfo) {
        int n = nodeinfo.length;
        int[][] answer = new int[2][n];
        
        
        List<Node> nodes = new ArrayList<>();
        List<Integer> preNodes = new ArrayList<>();
        List<Integer> postNodes = new ArrayList<>();
        
        for(int i = 1; i <= n; i++){
            Node node = new Node();
            
            node.id = i;
            node.x = nodeinfo[i-1][0];
            node.y = nodeinfo[i-1][1];
            nodes.add(node);
        }
        
        nodes.sort((a, b) -> b.y - a.y);
        
        Node root = nodes.get(0);
        
        for(int i = 1; i < n; i++){
            insert(root, nodes.get(i));
        }
        
        preOrder(root, preNodes);
        postOrder(root, postNodes);
        
        for(int i = 0; i < n; i++){
            answer[0][i] = preNodes.get(i);
            answer[1][i] = postNodes.get(i);
        }
        
        
        return answer;
    }
}