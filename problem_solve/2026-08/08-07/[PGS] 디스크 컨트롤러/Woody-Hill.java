import java.util.Comparator;
import java.util.PriorityQueue;

class Solution {
    
    final int INF = Integer.MAX_VALUE;
    
    // 각 작업을 큐에 넣기 위한 클래스
    static class Task implements Comparable<Task> {
        private int pid;
        private int requestTime;
        private int processingTime;
        
        public Task(int pid, int requestTime, int processingTime) {
            this.pid = pid;
            this.requestTime = requestTime;
            this.processingTime = processingTime;
        }
        
        public int getPID() {
            return this.pid;
        }
        
        public int getRequestTime() {
            return this.requestTime;
        }
        
        public int getProcessingTime() {
            return this.processingTime;
        }
        
        @Override
        public int compareTo(Task other) {
            // 실행시간 - 요청시간 - 작업번호 순으로 정렬
            return Comparator.comparing(Task::getProcessingTime)
                             .thenComparing(Task::getRequestTime)
                             .thenComparing(Task::getPID)
                             .compare(this, other);
        }
    }
    
    public int solution(int[][] jobs) {
        
        int n = jobs.length;
        
        // 요청이 들어온 작업들이 대기하는 큐
        PriorityQueue<Task> waitingQueue = new PriorityQueue<>();
        
        // 요청이 들어오기 전의 작업들이 저장되어 있는 큐 - 별도의 정렬 기준 적용
        PriorityQueue<Task> requestQueue = new PriorityQueue<>(
            Comparator.comparing(Task::getRequestTime)
                      .thenComparing(Task::getProcessingTime)
                      .thenComparing(Task::getPID)
        );  // 요청 시간을 기준으로 정렬해 요청 시간 순으로 뽑아서 대기 큐에 넣을 수 있다
        
        // 일단 모두 요청 큐에 넣기
        for (int id = 0; id < n; id++) {
            requestQueue.offer(new Task(id, jobs[id][0], jobs[id][1]));
        }
        
        // 각 작업의 TAT 저장용
        int[] turnaroundTime = new int[n];
        
        int currentTime = 0;        // 현재 시간
        int completedTasks = 0;     // 완료한 작업 개수 (n이면 반복문 탈출)
        
        while (completedTasks < n) {
            // 대기 큐로 옮길 수 있는 작업 요청이 있다면
            if (!requestQueue.isEmpty()) {
                // 가장 빠른 작업 요청 시간 확인
                int nextRequestTime = requestQueue.peek().getRequestTime();
                
                // 만약 "대기 큐가 비었는데" 들어온 요청도 없다면 현재 시간을 옮긴다
                if (waitingQueue.isEmpty() && currentTime < nextRequestTime) {
                    currentTime = nextRequestTime;
                    continue;
                }
                
                // 현재 시간보다 먼저 들어온 요청들을 모두 대기 큐로 옮긴다
                while (nextRequestTime <= currentTime) {
                    Task task = requestQueue.poll();
                    waitingQueue.offer(task);
                    
                    if (!requestQueue.isEmpty()) {
                        nextRequestTime = requestQueue.peek().getRequestTime();
                    } else {
                        nextRequestTime = INF;
                    }
                }
            }
            
            // 다음으로 수행할 작업을 대기 큐에서 선택
            Task nextTask = waitingQueue.poll();
            
            int pid = nextTask.getPID();
            int requestTime = nextTask.getRequestTime();
            int processingTime = nextTask.getProcessingTime();
            
            // TAT 계산 = 대기 시간 + 수행 시간 = (현재 시간 - 요청 시간) + 수행 시간
            turnaroundTime[pid] = currentTime - requestTime + processingTime;
            currentTime += processingTime;  // 작업을 했으니 그만큼 시간이 지났다
            completedTasks += 1;            // 완료한 작업 개수 + 1
        }
        
        // TAT 총합 계산
        int totalTAT = 0;
        for (int id = 0; id < n; id++) {
            totalTAT += turnaroundTime[id];
        }
        
        // 평균 계산 후 반환
        int averageTAT = totalTAT / n;
        
        return averageTAT;
    }
}
