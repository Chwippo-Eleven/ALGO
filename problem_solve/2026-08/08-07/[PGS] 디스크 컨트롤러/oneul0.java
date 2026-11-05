import java.util.*;
class Solution {
    public class Job implements Comparable<Job> {
        int idx;
        int arrivalTime;
        int processTime;

        Job(int idx, int arrivalTime, int processTime) {
            this.idx = idx;
            this.arrivalTime = arrivalTime;
            this.processTime = processTime;
        }

        @Override
        public int compareTo(Job o) {
            if (this.processTime != o.processTime) return this.processTime - o.processTime;
            if (this.arrivalTime != o.arrivalTime) return this.arrivalTime - o.arrivalTime;

            return this.idx - o.idx;
        }
    }

    public int solution(int[][] jobs) {
        Arrays.sort(jobs, (a,b) -> a[0] - b[0]);
        PriorityQueue<Job> pq = new PriorityQueue<>();
        int currentTime = 0;
        int responseTimeSum = 0;
        int idx = 0;
        int completed = 0;

        while (completed < jobs.length) {
            while (idx<jobs.length && jobs[idx][0]<=currentTime) {
                pq.offer(new Job(idx, jobs[idx][0], jobs[idx][1]));
                idx++;
            }

            if (pq.isEmpty()) {
                currentTime = jobs[idx][0];
                continue;
            }

            Job cur = pq.poll();
            currentTime += cur.processTime;
            responseTimeSum += currentTime - cur.arrivalTime;
            completed++;
        }

        return responseTimeSum/jobs.length;
    }
}