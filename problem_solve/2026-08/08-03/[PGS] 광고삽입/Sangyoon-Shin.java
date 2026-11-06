class Solution {
    public String solution(String play_time, String adv_time, String[] logs) {

        // 알고리즘: 누적합
        // 시간 데이터를 초 단위로 바꾼 뒤, 타임라인을 배열로 처리
        // 첫 번째 누적합: 시작 - 종료 시점을 기반으로 매초 시청중인 사람 수 구해두기
        // 두 번째 누적합: 첫 번째 누적합을 통해 누적 시청 시간 구하기

        // 어려웠던 점: 누적합으로 접근을 하지 않고, 현재 로그에서 시청한 구간을 전부 탐색해버림(N^2) = 시간초과
        // 예전에 풀었던 누적합 문제 공유: https://swexpertacademy.com/main/code/problem/problemSubmitDetail.do


        int playTime = timeToSec(play_time);
        int advTime = timeToSec(adv_time);

        long[] timeline = new long[playTime + 1];

        for (String log : logs){
            String[] info = log.split("-");
            int start = timeToSec(info[0]);
            int end = timeToSec(info[1]);

            timeline[start]++; // 시작, 종료 시점만 기록해두기
            timeline[end]--;
        }

        // 1차: 매초 시청자 수
        for (int i = 1; i < timeline.length; i++){
            timeline[i] += timeline[i - 1];
        }

        // 2차: 0초부터 i까지 누적 시청 시간
        for (int i = 1; i < timeline.length; i++){
            timeline[i] += timeline[i - 1];
        }

        long max = timeline[advTime - 1];
        int bestTime = 0;

        // 광고 길이만큼씩 비교해나가면서, 누적 시청 시간이 가장 큰 애 찾기
        for (int start = 1; start + advTime <= playTime; start++){
            long cur = timeline[start + advTime - 1] - timeline[start - 1];
            if (cur > max){
                max = cur;
                bestTime = start;
            }
        }

        return secToTime(bestTime);
    }
    public int timeToSec(String time){
        String[] t = time.split(":");
        return  (Integer.parseInt(t[0]) * 3600) + (Integer.parseInt(t[1]) * 60)
                + Integer.parseInt(t[2]);
    }
    public String secToTime(int time){
        int h = time / 3600;
        int m = (time % 3600) / 60;
        int s = time % 60;
        return String.format("%02d:%02d:%02d", h, m, s); // 문자열 형식 맞추는거도 까먹었었다
    }
}