import java.util.*;

class Disk{
    int idx;
    int arrive;
    int duration;
    Disk(int idx, int arrive, int duration){
        this.idx = idx;
        this.arrive = arrive;
        this.duration = duration;
    }
}

class Solution {
    public int solution(int[][] jobs) {

        PriorityQueue<Disk> pq = new PriorityQueue<>(
                (cur, next) -> {
                    if (cur.duration != next.duration) return Integer.compare(cur.duration, next.duration);
                    else if (cur.arrive != next.arrive) return Integer.compare(cur.arrive, next.arrive);
                    return Integer.compare(cur.idx, next.idx);
                }
        );

        int[][] tasks = new int[jobs.length][3]; // 작업번호, 도착시간, 소요시간

        // 우선순위큐에 다 넣어버리면 안된다.
        // why? pq 정렬기준을 작업 소요시간이 짧은순으로 해뒀는데, 100초에 도착하는 애가 작업 소요시간이 1초라는 이유로 맨 먼저 뽑아지는 문제 발생.
        // 반면 0초에 도착해서 2초 걸리는애는 101초를 기다려야하는 상황이 생기는거지.
        // 그럼 어떻게 해야되는데? 일단 작업의 도착 순서로 정렬해서 현재 시간에 pq에 넣을 수 있는 상황일때만 추가
        for (int i = 0; i < jobs.length; i++){
            tasks[i][0] = i;
            tasks[i][1] = jobs[i][0];
            tasks[i][2] = jobs[i][1];
        }
        Arrays.sort(tasks, (a, b) -> {
            return Integer.compare(a[1], b[1]);
        });

        int idx = 0;        // 다음에 확인할 tasks의 위치
        int completed = 0;  // 완료한 작업 수
        int time = 0;
        int res = 0;

        while (completed < jobs.length) {

            // 1. 현재 time까지 도착한 작업을 전부 pq에 넣는다.
            while (idx < jobs.length && tasks[idx][1] <= time){
                pq.offer(new Disk(tasks[idx][0], tasks[idx][1], tasks[idx][2]));
                idx++;
            }

            // 2. pq가 비어 있다면?
            //    아직 다음 작업이 도착하지 않은 것
            //    → 시간을 다음 작업의 도착시각까지 이동
            if (pq.isEmpty()){
                time = tasks[idx][1];
            }

            // 3. pq에 작업이 있다면?
            //    가장 짧은 작업 poll
            //    → 실행
            //    → time, res 갱신
            //    → completed++
            else {
                Disk cur = pq.poll();
                time += cur.duration;
                res += time - cur.arrive;
                completed++;
            }
        }
        return res / jobs.length;
    }
}