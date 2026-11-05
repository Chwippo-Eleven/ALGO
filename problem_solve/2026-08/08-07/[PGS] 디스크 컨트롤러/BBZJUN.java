import java.util.*;

class Solution {

    class TTT {
        int index; // 작업 번호
        int come;  // 요청 시간
        int time;  // 소요 시간

        TTT(int index, int come, int time) {
            this.index = index;
            this.come = come;
            this.time = time;
        }
    }

    public int solution(int[][] jobs) {

        // 요청 시간 기준 정렬
        Arrays.sort(jobs, (a, b) -> a[0] - b[0]);

        PriorityQueue<TTT> pq = new PriorityQueue<>((a, b) -> {

            if (a.time == b.time) {
                if (a.come == b.come) {
                    return a.index - b.index;
                }
                return a.come - b.come;
            }

            return a.time - b.time;
        });

        int idx = 0;       // jobs에서 pq에 넣은 위치
        int cnt = 0;       // 처리 완료한 작업 수
        int curTime = 0;   // 현재 시간
        int waitT = 0;     // 총

        while (cnt < jobs.length) {

            // 현재 시간까지 도착한 작업 전부 pq에 추가
            while (idx < jobs.length && jobs[idx][0] <= curTime) {
                pq.add(new TTT(idx, jobs[idx][0], jobs[idx][1]));
                idx++;
            }

            // 실행 가능한 작업이 있으면 가장 짧은 작업 1개 실행
            if (!pq.isEmpty()) {

                TTT ttt = pq.poll();

                curTime += ttt.time;

                waitT += curTime - ttt.come;

                cnt++;

            } else {
                // 실행할 작업이 없으면 다음 작업 시간에서 시작
                curTime = jobs[idx][0];
            }
        }

        return waitT / jobs.length;
    }
}
