import java.util.*;

class Solution {
    
    private int idx;
    class Node{
        int x;//x좌표
        int y;//y좌표
        int num;//노드 번호
        Node left;//왼쪽 자식
        Node right;//오른쪽 자식
        public Node(int x, int y, int num){
            this.x=x;
            this.y=y;
            this.num=num;
        }
    }
    public int[][] solution(int[][] nodeinfo) {

        int[][] answer = new int[2][nodeinfo.length];
        
        Node[] nodes=new Node[nodeinfo.length];
        
        for(int i=0;i<nodeinfo.length;i++){
            nodes[i]=new Node(nodeinfo[i][0], nodeinfo[i][1], i+1);
        }
        
        //y좌표 내림차순, x좌표 오름차순 정렬
        Arrays.sort(nodes, (a,b)->{
            if(a.y!=b.y) return Integer.compare(b.y, a.y);
            return Integer.compare(a.x, b.x);
        });
        
        Node root=nodes[0];
        
        //트리 생성
        for(int i=1;i<nodes.length;i++){
            insert(root, nodes[i]);
        }
        
        idx=0;
        preorder(root, answer[0]);

        idx=0;
        postorder(root, answer[1]);

        return answer;
    }
    
   
    private void insert(Node parent, Node child){
        if(child.x<parent.x){
            if(parent.left==null) parent.left=child;
            else insert(parent.left, child);
        }else{
            if(parent.right==null) parent.right=child;
            else insert(parent.right, child);
        }
    }
    
    //전위 순회
    private void preorder(Node root, int[] result){
        if(root==null) return;

        result[idx++]=root.num;
        preorder(root.left, result);
        preorder(root.right, result);
    }
    
    //후위 순회
    private void postorder(Node root, int[] result){
        if(root==null) return;
        
        postorder(root.left, result);
        postorder(root.right, result);
        result[idx++]=root.num;
    }
}
