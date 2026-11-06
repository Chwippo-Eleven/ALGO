import java.util.*;

class Solution {
    private int toSeconds(String time) {
        int h = Integer.parseInt(time.substring(0, 2));
        int m = Integer.parseInt(time.substring(3, 5));
        int s = Integer.parseInt(time.substring(6, 8));

        return h * 3600 + m * 60 + s;
    }
    
    private String toStr(int time) {
        int hh = time / 3600;
        int mm = (time % 3600) / 60;
        int ss = time % 60;

        return String.format("%02d:%02d:%02d", hh, mm, ss);
    }
    
    public String solution(String play_time, String adv_time, String[] logs) {
        String answer = "00:00:00";
        
        int curTime = 0;
        int totalTime = toSeconds(play_time.substring(0, 8));
        int avdTime = toSeconds(adv_time.substring(0, 8));
        Long mx = -1L;
        
        long[] timeline = new long[totalTime + 1];
        
        for(String log : logs){
            int st = toSeconds(log.substring(0, 8));
            int et = toSeconds(log.substring(9, log.length()));
            
            timeline[st]++;
            timeline[et]--;
        }
        
        // 초마다 시청자 수
        for (int i = 1; i <= totalTime; i++) {
            timeline[i] += timeline[i - 1];
        }
        
        // 시간별 누적 시청 시간
        for (int i = 1; i <= totalTime; i++) {
            timeline[i] += timeline[i - 1];
        }
        
        while (curTime + avdTime <= totalTime){
            int avdEnd = curTime + avdTime;
            
            long res = 0L;

            if (curTime == 0) {
                res = timeline[avdEnd - 1];
            } else {
                res = timeline[avdEnd - 1] - timeline[curTime - 1];
            }
            
            if(res > mx){
                mx = res;
                answer = toStr(curTime);
            }
            
            curTime++;
        }
        
        return answer;
    }
}