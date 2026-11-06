import java.util.ArrayDeque;
import java.util.Queue;

class Solution {
    public int solution(int bridge_length, int weight, int[] truck_weights) {
        
        int n = truck_weights.length;   // 트럭 개수
        
        int[] entryTime = new int[n];   // 트럭 별 다리 진입 시간
        
        // 큐의 원소는 트럭의 index
        Queue<Integer> bridge = new ArrayDeque<>();
        
        int onBridge = 0;   // 현재 다리에 걸린 하중
        
        int index = 0;      // 다음 진입할 트럭의 index
        int time  = 0;      // 현재 시간
        
        while (index < n) {
            time += 1;      // 현재 시간 + 1
            
            // 과정 1. 다리 위 가장 앞 트럭을 빼낼 수 있으면 빼내기
            
            Integer truckIdx = bridge.peek();   // 다리 위 가장 앞 트럭의 인덱스 
            
            if (truckIdx != null && time - entryTime[truckIdx] >= bridge_length) {
                bridge.poll();
                onBridge -= truck_weights[truckIdx];
            }
            
            // 과정 2. 대기열의 가장 앞 트럭을 넣을 수 있으면 넣기
            
            if (onBridge + truck_weights[index] <= weight) {
                bridge.offer(index);
                onBridge += truck_weights[index];
                
                entryTime[index++] = time;
            }
        }
        
        // 맨 마지막 트럭이 다리를 다 건너는 데 걸리는 시간
        return entryTime[n - 1] + bridge_length;
    }
}
