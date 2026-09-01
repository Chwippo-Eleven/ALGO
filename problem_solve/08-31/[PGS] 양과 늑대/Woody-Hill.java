import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

class Solution {
    
    static final int SHEEP = 0;
    static final int WOLF  = 1;
    
    static class State {
        int mask;
        int sCount;
        int wCount;
        List<Integer> candid;
        
        State(int mask, int sCount, int wCount, List<Integer> candid) {
            this.mask   = mask;
            this.sCount = sCount;
            this.wCount = wCount;
            this.candid = candid;
        }
    }
    
    static class Node {
        int  id;
        int  type;   // 양은 0, 늑대는 1
        Node left;
        Node right;
        
        Node(int id, int type) {
            this.id    = id;
            this.type  = type;
            this.left  = null;
            this.right = null;
        }
        
        // 이진 트리임이 보장되므로 예외 로직 없음
        void addChild(Node child) {
            if (left == null) {
                left = child;
            } else {
                right = child;
            }
        }
    }
    
    public int solution(int[] info, int[][] edges) {
        
        int n = info.length;
        Node[] nodes = new Node[n];
        
        for (int i = 0; i < n; i++) {
            int type = (info[i] == 0) ? SHEEP : WOLF;
            nodes[i] = new Node(i, type);
        }
        
        for (int[] edge : edges) {
            Node parent = nodes[edge[0]];
            Node child  = nodes[edge[1]];
            
            parent.addChild(child);
        }
        
        // 결과 저장 + 일종의 방문 배열 역할
        Map<Integer, Integer> resMap = new HashMap<>();
        
        // root = 0번 노드
        resMap.put(0, 0);
        
        // 초기 방문 가능 노드는 루트인 0
        List<Integer> rootList = new ArrayList<>();
        rootList.add(0);
        
        // (마스크 값, 양 수, 늑대 수, 방문 가능 노드)
        State init = new State(0, 0, 0, rootList);
        
        Queue<State> queue = new ArrayDeque<>();
        queue.add(init);
        
        while (!queue.isEmpty()) {
            State curr = queue.poll();
            
            int mask = curr.mask;
            int sc = curr.sCount;
            int wc = curr.wCount;
            List<Integer> candid = curr.candid;
            
            // 후보 리스트에서 selectIndex 위치의 노드에 방문해 보기
            for (int selectIndex = 0; selectIndex < candid.size(); selectIndex++) {
                int id = candid.get(selectIndex);
                int newMask = setBit(mask, id);
                
                // 이미 방문했으면 종료
                if (resMap.containsKey(newMask)) { continue; }
                
                Node selectNode = nodes[id];
                int newSC = sc;
                int newWC = wc;
                
                if (selectNode.type == SHEEP) { newSC += 1; }
                else if (selectNode.type == WOLF) { newWC += 1; }
                
                // 늑대의 수가 양의 수 이상이 될 수 없음
                if (newSC <= newWC) { continue; }
                
                // 가능한 경우에 대해 기록하기
                resMap.put(newMask, newSC);
                
                // 새로운 방문 후보지 배열
                List<Integer> newCandid = new ArrayList<>();
                
                // 좌우 자식들 추가
                if (selectNode.left != null) {
                    newCandid.add(selectNode.left.id);
                }
                if (selectNode.right != null) {
                    newCandid.add(selectNode.right.id);
                }
                
                // 아직 방문하지 못한 노드들도 다 넣기
                for (int i = 0; i < candid.size(); i++) {
                    if (i != selectIndex) {
                        newCandid.add(candid.get(i));
                    }
                }
                
                // 큐에 추가해 두기
                queue.add(new State(newMask, newSC, newWC, newCandid));
            }
        }
        
        // 최대 양의 수를 map에서 찾은 뒤 반환
        int maxSheepCount = 0;
        
        for (int sheepCount : resMap.values()) {
            maxSheepCount = Math.max(maxSheepCount, sheepCount);
        }
        
        return maxSheepCount;
    }
    
    // 비트마스킹
    private int setBit(int mask, int i) {
        return mask | (1 << i);
    }
}
