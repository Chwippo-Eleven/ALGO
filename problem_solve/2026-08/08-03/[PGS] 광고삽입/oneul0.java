import java.util.*;
class Solution {
    public String solution(String play_time, String adv_time, String[] logs) {
        if(play_time.equals(adv_time)) return "00:00:00";
        int playTotalTime = time2Sec(play_time);
        int adTime = time2Sec(adv_time);
        int[] viewerCnt = new int[playTotalTime+1];

        //누적합으로 해당 부분의 누적 시청자 수 구하기
        for(String log : logs){
            int[] chunk = log2Sec(log);
            viewerCnt[chunk[0]]++;
            viewerCnt[chunk[1]]--;
        }

        // 시청자 수 변화량 → 초별 동시 시청자 수
        for(int sec = 1; sec<playTotalTime; sec++){
            viewerCnt[sec] += viewerCnt[sec-1];
        }

        //슬라이딩 윈도우
        long window = 0; //전체 누적 청자 수
        for(int i = 0; i<adTime; i++){
            window += viewerCnt[i];
        }

        long maxWatchTime = window;
        int bestStartTime = 0;

        for(int start = 1; start + adTime <= playTotalTime; start++){
            int removedSec = start-1;
            int addedSec = start + adTime -1;

            window -= viewerCnt[removedSec];
            window += viewerCnt[addedSec];

            if(window > maxWatchTime){
                maxWatchTime = window;
                bestStartTime = start;
            }
        }

        return sec2Log(bestStartTime);
    }


    //utils
    public String sec2Log(int startTime){
        int start = startTime;
        int startHH = start / 3600;

        start %= 3600;
        int startMM = start / 60;
        int startSS = start % 60;

        return makeTwo(startHH) + ":" + makeTwo(startMM) + ":" + makeTwo(startSS);
    }

    //"01:20:15" 형태
    public int time2Sec(String time){
        String[] hhmmss = time.split(":");
        int sec = Integer.parseInt(hhmmss[0])*3600
            + Integer.parseInt(hhmmss[1])*60
            + Integer.parseInt(hhmmss[2]);

        return sec;
    }
    public int[] log2Sec(String log){
        String[] startTimeEndTime = log.split("-");
        int startTime = time2Sec(startTimeEndTime[0]);
        int endTime = time2Sec(startTimeEndTime[1]);
        return new int[]{startTime, endTime};
    }

    public String makeTwo(int time){
        String str = String.valueOf(time);
        return str.length() < 2 ? "0"+str : str;
    }
}