import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

class Solution {
    
    static class GyulBox implements Comparable<GyulBox> {
        int size;
        int count;
        
        GyulBox(int size, int count) {
            this.size = size;
            this.count = count;
        }
        
        @Override
        public int compareTo(GyulBox other) {
            return Integer.compare(other.count, this.count);
        }
    }
    
    public int solution(int k, int[] tangerine) {
        
        Map<Integer, Integer> gyul = new HashMap<>();
        
        for (int size : tangerine) {
            gyul.put(size, gyul.getOrDefault(size, 0) + 1);
        }
        
        PriorityQueue<GyulBox> gyulBoxes = new PriorityQueue<>();
        
        for (Map.Entry<Integer, Integer> entry : gyul.entrySet()) {
            gyulBoxes.add(new GyulBox(entry.getKey(), entry.getValue()));
        }
        
        int diffSizeCount = 0;
        int gyulCount = 0;
        
        while (gyulCount < k) {
            GyulBox gyulBox = gyulBoxes.poll();
            
            diffSizeCount += 1;
            gyulCount += gyulBox.count;
        }
        
        return diffSizeCount;
    }
}
