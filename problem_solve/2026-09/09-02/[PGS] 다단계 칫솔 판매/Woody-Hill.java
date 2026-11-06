import java.util.HashMap;
import java.util.Map;

class Solution {
    
    static class Node {
        String name;
        int profit;
        Node parent;
        
        Node(String name, Node parent) {
            this.name = name;
            this.profit = 0;
            this.parent = parent;
        }
        
        void settle(int revenue) {
            int fee = revenue / 10;
            revenue -= fee;
            this.profit += revenue;
            
            if (fee > 0 && this.parent != null) {
                this.parent.settle(fee);
            }
        }
    }
    
    public int[] solution(String[] enroll, String[] referral, String[] seller, int[] amount) {
        
        final int PRICE = 100;
        
        Map<String, Node> nodeMap = new HashMap<>();
        
        Node center = new Node("center", null);
        nodeMap.put("-", center);
        
        int n = enroll.length;
        
        for (int i = 0; i < n; i++) {
            Node parent = nodeMap.get(referral[i]);
            Node newNode = new Node(enroll[i], parent);
            nodeMap.put(enroll[i], newNode);
        }
        
        int m = seller.length;
        
        for (int i = 0; i < m; i++) {
            Node node = nodeMap.get(seller[i]);
            node.settle(amount[i] * PRICE);
        }
        
        int[] result = new int[n];
        
        for (int i = 0; i < n; i++) {
            result[i] = nodeMap.get(enroll[i]).profit;
        }
        
        return result;
    }
}
