import java.util.*;
class Solution {
    class Truck{
        int weight;
        int enteredAt;
        Truck(int w, int e){
            this.weight = w;
            this.enteredAt = e;
        }
    }
    public int solution(int bridge_length, int weight, int[] truck_weights) {
        int time = 0;
        int curWeight = 0;
        int nextTruckIdx = 0;
        Queue<Truck> bridge = new ArrayDeque<>();

        //반복문 별로 1초씩 흐름
        while(nextTruckIdx < truck_weights.length || !bridge.isEmpty()){
            time++;

            //다리 다 건넌 트럭 제거
            if(!bridge.isEmpty()){
                Truck first = bridge.peek();

                //올라오고 다리 길이만큼 시간 지나면 다 지난 것
                if(time  - first.enteredAt >= bridge_length){
                    curWeight-=first.weight;
                    bridge.poll();
                }
            }

            //올라갈 다른 트럭이 있는지
            if(nextTruckIdx < truck_weights.length){
                int nextTruckWeight = truck_weights[nextTruckIdx];
                if(curWeight +nextTruckWeight <= weight){
                    bridge.offer(new Truck(nextTruckWeight, time));

                    curWeight += nextTruckWeight;
                    nextTruckIdx++;
                }
            }

        }

        return time;
    }
}