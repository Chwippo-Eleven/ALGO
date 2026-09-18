import java.util.*;

class Solution {

    class Pair {
        int first;
        int second;

        Pair(int first, int second) {
            this.first = first;
            this.second = second;
        }
    }

    public int solution(int bridge_length, int weight, int[] truck_weights) {
        int answer = 0;

        ArrayDeque<Pair> q = new ArrayDeque<>();

        int w = 0;
        int idx = 0;

        while (idx < truck_weights.length || !q.isEmpty()) {
            for (Pair p : q) {
                p.second++;
            }
            
            while (!q.isEmpty() && q.peekFirst().second >= bridge_length) {
                w -= q.pollFirst().first;
            }
            
            if (idx < truck_weights.length && w + truck_weights[idx] <= weight) {
                q.addLast(new Pair(truck_weights[idx], 0));
                w += truck_weights[idx];
                idx++;
            }

            answer++;
        }

        return answer;
    }
}