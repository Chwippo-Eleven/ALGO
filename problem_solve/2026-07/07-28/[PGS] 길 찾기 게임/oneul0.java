import java.util.*;
class Solution {
    class Node implements Comparable<Node>{
        int idx, x, y;
        Node(int idx, int x, int y){
            this.idx = idx;
            this.x = x;
            this.y=  y;
        }
        @Override
        public int compareTo(Node o){
            if(this.y != o.y) return Integer.compare(o.y, this.y);
            return Integer.compare(this.x, o.x);
        }
    }
    int n;
    public int[][] solution(int[][] nodeinfo) {
        this.n = nodeinfo.length;
        List<Node> nodes = new ArrayList<>();
        for(int i = 0; i<n; i++){
            nodes.add(new Node(i, nodeinfo[i][0], nodeinfo[i][1]));
        }
        Collections.sort(nodes);
        List<Integer>[] children = makeTree(nodes);

        int[][] answer = new int[2][n];
        int root = nodes.get(0).idx;
        preorder(answer, root, children);
        postorder(answer, root, children);

        return answer;
    }


    //preorder
    //노드 1만개라서 스택 터질 수도 있음
    void preorder(int[][] answer, int rootId, List<Integer>[] children){
        Deque<Integer> stack = new ArrayDeque<>();
        int idx = 0;

        stack.push(rootId);
        while(!stack.isEmpty()){
            int curId = stack.pop();
            answer[0][idx++] = curId+1;

            for(int i = children[curId].size()-1; i>=0; i--){
                stack.push(children[curId].get(i));
            }
        }
    }

    //postorder
    void postorder(int[][] answer, int rootId, List<Integer>[] children){
        Deque<Integer> first = new ArrayDeque<>();
        Deque<Integer> second = new ArrayDeque<>();
        int idx = 0;
        first.push(rootId);

        while(!first.isEmpty()){
            int curId = first.pop();
            second.push(curId);

            for(int child : children[curId]){
                first.push(child);
            }
        }
        while(!second.isEmpty()){
            answer[1][idx++] = second.pop()+1;
        }
    }

    //트리 만들기
    List<Integer>[] makeTree(List<Node> nodes){
        //i번 노드의 모든 자식
        List<Integer>[] children = new ArrayList[n];
        for(int i = 0; i<n; i++){
            children[i] = new ArrayList<>();
        }

        Node[] nodeArr = new Node[n];
        for(Node node : nodes){
            nodeArr[node.idx] = node;
        }

        int[] left = new int[n];
        int[] right = new int[n];
        Arrays.fill(left, -1);
        Arrays.fill(right, -1);

        int rootIdx = nodes.get(0).idx;
        //todo: n-1로 진행하고 나중에 붙이기
        for(int i = 1; i<n; i++){
            Node childNode = nodes.get(i);
            int curIdx = rootIdx;

            while(true){
                Node curNode = nodeArr[curIdx];

                //왼쪽이 비었으면 왼쪽으로
                if(childNode.x < curNode.x){
                    if(left[curIdx] == -1){
                        left[curIdx] = childNode.idx;
                        break;
                    }
                    curIdx = left[curIdx];
                }
                //아니라면 오른쪽 체크
                else{
                    if(right[curIdx] == -1){
                        right[curIdx] = childNode.idx;
                        break;
                    }
                    curIdx = right[curIdx];
                }
            }
        }

        //전위순회, 후위순회 모두 왼쪽 먼저 순회하기 때문에 순회 순서를 보장하기 위해서 왼쪽 먼저 넣음
        for(int i = 0; i<n; i++){
            //왼쪽 먼저 저장
            if(left[i] != -1){
                children[i].add(left[i]);
            }
            //오른쪽 나중에 저장
            if(right[i] != -1){
                children[i].add(right[i]);
            }
        }

        return children;
    }
}

//y값이 가장 큰 노드가 루트
//루트의 값보다 y값이 작은 노드에는 연결 가능

