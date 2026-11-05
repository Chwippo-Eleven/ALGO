import java.util.*;
class Solution {
    class ttt {
        int index;
        int x;
        int y;
        ttt left, right; // 연결용, 초기에 생성해주면 null
        ttt(int index, int x, int y) {
            this.index = index;
            this.x = x;
            this.y = y;
        }
    }

    public int[][] solution(int[][] nodeinfo) {
        int[][] answer = {};
        ttt root = null; // 트리 시작점                         
        List<Integer> pre = new ArrayList<>(); // 전위 부-왼-오
        List<Integer> post = new ArrayList<>(); // 후위 왼-오-부

        PriorityQueue<ttt> pq = new PriorityQueue<>((a, b) -> {
            if (a.y != b.y){
                return b.y - a.y; // y는 내림차순
            }
            else{
                return a.x - b.x; // x는 오름차순
            }
            });
        int index = 1;
        for (int[] x : nodeinfo) {
            pq.add(new ttt(index, x[0], x[1])); // 인덱스, x, y 넣기
            index++;
        }
        while (!pq.isEmpty()) {
            ttt poll = pq.poll(); // 이쁘게 정렬된것을 하나씩 뽑아서 이진트리 만들기
            root = insert(root, poll);
        }
        
        preorder(root, pre); //전
        postorder(root, post); //후
        
        answer = new int[2][pre.size()];
        for (int i = 0; i < pre.size(); i++){
            answer[0][i] = pre.get(i);
        }
        
        for (int i = 0; i < post.size(); i++){
            answer[1][i] = post.get(i);
        }
        return answer;
    }

    public ttt insert(ttt root, ttt poll) {
        if (root == null) { // 값이 없으면 넣어줌
            return poll;         
        }
        // 루트가 있는 상태에서
        if (poll.x < root.x) { // 왼쪽
            root.left = insert(root.left, poll); // root.left가 없으면 이제 poll이 연결, return으로 받은 poll이 여기에 들어감 + 계속 루트부터 보면서 내려가는거임 널을 찾을때까지
        } else {//위의 오른쪽 버전
            root.right = insert(root.right, poll);
        }
        return root;// 밑으로 내려갔다가 연결하면서 다시 올라옴
    }

    public void preorder(ttt node, List<Integer> pre){
        if (node == null)
            return;
        pre.add(node.index); // 부
        preorder(node.left, pre); // 왼
        preorder(node.right, pre); // 오
    }

    public void postorder(ttt node, List<Integer> post){
        if (node == null)
            return;
        postorder(node.left, post); // 왼
        postorder(node.right, post);// 오
        post.add(node.index);// 부
    }
}
