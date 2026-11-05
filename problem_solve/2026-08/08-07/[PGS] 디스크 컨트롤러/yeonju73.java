import java.util.*;
/*
    소요시간 짧 -> 요청시간 빠 -> 번호 작
    작업 마치기 -> 대기큐 저장 -> 우선순위 1번 꺼내기
    모든 요청 작업의 반환 시간의 평균의 정수부분
    반환시간: 작업 요청부터 종료까지 걸린 시간
*/
class Solution {
    public int solution(int[][] jobs) {
        int answer = 0;
        int time = 0;
        int count = 0;
        int jobsIdx = 0;
        
        // [번호, 요청시간, 소요시간]
        PriorityQueue<int[]> pq = new PriorityQueue<>((x1, x2) -> {
            if(x1[2] == x2[2]){
                if(x1[1] == x2[1]){
                    return Integer.compare(x1[0], x2[0]);
                }
                return Integer.compare(x1[1], x2[1]);
            }
            return Integer.compare(x1[2], x2[2]);
            
        });
        // 요청 시각 기준으로 정렬
        Arrays.sort(jobs, (a, b) -> a[0] - b[0]);
        
        while (count < jobs.length) {
            // 현재 시간 이전에 도착한 것을 대기 큐에 넣음
            while (jobsIdx < jobs.length && jobs[jobsIdx][0] <= time){
                pq.offer(new int[]{jobsIdx, jobs[jobsIdx][0], jobs[jobsIdx][1]});
                jobsIdx++;
            }
            
            if(pq.isEmpty()) {
                // 지금 당장 수행할 게 없다면 다음 작업 요청 시간으로 점점프프
                time = jobs[jobsIdx][0];
            }
            else {
                int[] job = pq.poll();
                // job 수행 -> 소요시간 만큼 시간이 돌돌돌..
                time += job[2];
                answer += time - job[1];
                count++;
            }
        }
        
        return answer / jobs.length;
    }
}
