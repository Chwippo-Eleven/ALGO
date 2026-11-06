import java.util.*;

class Solution {
    
    class Pair implements Comparable<Pair>{
        int first;
        int second;
        
        public Pair(int first, int second){
            this.first = first;
            this.second = second;
        }
        
        @Override
        public int compareTo(Pair p) {
            if(this.second != p.second) return this.second - p.second;
            return this.first - p.first;
        }
    }
    
    public int solution(int[][] jobs) {
        int answer = 0;
        Arrays.sort(jobs, (a,b) -> a[0]-b[0]);
        PriorityQueue<Pair> pq = new PriorityQueue<>();
        
        int curTime = 0;
        int idx = 0;
        
        while(idx < jobs.length || pq.isEmpty() == false){
            // System.out.println("curTime : " + curTime);
            while(idx < jobs.length && curTime >= jobs[idx][0]){
                pq.add(new Pair(jobs[idx][0], jobs[idx][1]));
                idx++;
            }
            
            // System.out.println(pq.size());
            if(pq.isEmpty() == false){
                Pair cur = pq.poll();
                curTime += cur.second;
                
                answer += curTime - cur.first;
            }else{
                curTime = jobs[idx][0];
            }
            
        }
        // System.out.println("curTime : " + curTime);
        // System.out.println("answer : " + answer);
        
        return answer / jobs.length;
    }
}