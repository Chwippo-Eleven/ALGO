import java.util.*;

class Solution {
    public int solution(int[][] jobs) {
        int answer = 0;
        int n = jobs.length;
        
        int time  = 0;
        int waitingTime = 0;
        // 요청 시각 순 정렬 
        Arrays.sort(jobs, (a,b) -> a[0]-b[0]);
        
        // 대기큐
        PriorityQueue<Job> pq = new PriorityQueue<>();
        // 인덱스 역할 
        int i =0; 
        while(i<n || !pq.isEmpty()) {
            // 작업 요청이 이루어지지 않아도, 시간은 계속 증가하는 로직이 필요함
            if (i<n && pq.isEmpty() && time < jobs[i][0]) {
                time = jobs[i][0];
            }
            while (i<n && time >= jobs[i][0]) {
                //              작업번호, 요청시각, 소요 시간
                pq.offer(new Job(i,jobs[i][0], jobs[i][1]));
                waitingTime += jobs[i][1]; // [미리 더하기]: 자기자신 소요 시간 어차피 더해야됨
                i++;
            }
            // 작업 처리
            if (!pq.isEmpty()) {
                Job task = pq.poll();
                waitingTime += (time - task.request);
                time += task.consume;
            }
        }
        
        return waitingTime/n;
    }
    
    class Job implements Comparable<Job> {
        // 작업번호, 요청시각, 소요 시간
        int idx, request, consume;
        Job (int idx, int request, int consume) {
            this.idx = idx;
            this.request = request;
            this.consume = consume;
        }
        
        @Override
        public int compareTo(Job o) {
            if (this.consume == o.consume) {
                if (this.request == o.request) {
                    // 3순위: 작업 번호 
                    return this.idx - o.idx;
                }
                else {
                    // 2순위: 요청시각
                    return this.request - o.request;
                }
            } 
            // 1순위: 소요 시간
            return this.consume - o.consume;
        }
    }
}