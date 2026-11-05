import java.util.Arrays;

class Solution {
    static class Node implements Comparable<Node> {
        int value;
        int x;
        int y;
        Node parent;
        Node leftChild;
        Node rightChild;
        int rightBound;     // 오른쪽 자식이 가질 수 있는 최대 x값
        
        Node(int value, int x, int y) {
            this.value = value;
            this.x = x;
            this.y = y;
            this.rightBound = Integer.MAX_VALUE;
        }
        
        void setLeftChild(Node child) {
            this.leftChild = child;
        }
        
        void setRightChild(Node child) {
            this.rightChild = child;
        }
        
        void setParent(Node parent) {
            this.parent = parent;
            
            // rightBound: 왼쪽 자식이면 부모의 x값, 오른쪽 자식이면 부모의 rightBound
            this.rightBound = (this.x < parent.x) ? parent.x : parent.rightBound;
        }
        
        // Node 정렬을 위한 비교 함수
        @Override
        public int compareTo(Node v) {
            
            // y에 대해 내림차순으로 정렬, y가 같으면 x에 대해 오름차순 정렬
            return (this.y != v.y) ? v.y - this.y : this.x - v.x;
        }
    }
    
    
    // 전위 순회 함수: 자신 -> 왼쪽 -> 오른쪽
    void preorderTraversal(Node v) {
        if (v == null) {
            return;
        }
        
        preorder[orderIndex++] = v.value;
        preorderTraversal(v.leftChild);
        preorderTraversal(v.rightChild);
    }
    
    // 후위 순회 함수: 왼쪽 -> 오른쪽 -> 자신
    void postorderTraversal(Node v) {
        if (v == null) {
            return;
        }
        
        postorderTraversal(v.leftChild);
        postorderTraversal(v.rightChild);
        postorder[orderIndex++] = v.value;
    }
    
    // 결과 저장용 배열
    int[] preorder;
    int[] postorder;
    
    int orderIndex;
    
    public int[][] solution(int[][] nodeinfo) {
        int n = nodeinfo.length;
        Node[] nodes = new Node[n];
        
        for (int i = 0; i < n; i++) {
            nodes[i] = new Node(i + 1, nodeinfo[i][0], nodeinfo[i][1]);
        }
        Arrays.sort(nodes);     // compareTo로 설정한 기준에 따라 정렬
        
        
        Node root = null;
        int level = 0;          // level: 현재 보고 있는 노드의 y값
        int parentIndex = 0;    // 부모 후보 탐색을 위한 인덱스
        
        for (Node v: nodes) {
            // 루트 노드 설정 (최초 1회)
            if (root == null) {
                root = v;
                level = v.y;
                continue;
            }   
            
            // 탐색 level이 한 단계 내려가는 경우
            if (v.y < level) {
                // 기존의 level은 이제 부모의 레벨. 
                // level보다 y값이 큰 노드는 부모 후보가 될 수 없다.
                while (nodes[parentIndex].y > level) {
                    parentIndex += 1;
                };
                // level 갱신
                level = v.y;
            }
            
            // 적절한 부모 탐색하기
            while (parentIndex < n) {
                // parent 후보 선정
                Node parentCandid = nodes[parentIndex];
                
                if (v.x < parentCandid.x) {
                    // 조건 1. 왼쪽 자식으로 들어갈 수 있음
                    parentCandid.setLeftChild(v);
                    v.setParent(parentCandid);
                    break;
                } else if (v.x < parentCandid.rightBound) {
                    // 조건 2. 오른쪽 자식으로 들어갈 수 있음
                    parentCandid.setRightChild(v);
                    v.setParent(parentCandid);
                    break;
                } else {
                    // 좌우 모두 불가능하면 다음 부모 후보자 탐색
                    parentIndex += 1;
                }
            }
        }
        
        // 결과 반환용 배열 초기화
        preorder = new int[n];
        postorder = new int[n];
        
        // 전위 순회
        orderIndex = 0;
        preorderTraversal(root);
        
        // 후위 순회
        orderIndex = 0;
        postorderTraversal(root);
        
        // 끝
        int[][] answer = {preorder, postorder};
        return answer;
    }
}
