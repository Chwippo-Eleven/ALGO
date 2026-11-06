import java.util.*;

class Solution {
    public String solution(String play_time, String adv_time, String[] logs) {
        String answer = "";
        
        // 전부 초로 변환
        int playTime = toSecond(play_time);
        int advTime = toSecond(adv_time);
        
        if(playTime == advTime) return "00:00:00";
        
        // 초로 변환한 동영상 길이만큼의 배열
        int[] viewer = new int[playTime + 1];
        // 누적합 배열
        int[] sumView = new int[playTime + 1];
        
        // diff 배열에 시작할때 배열의 값 +1 끝날때 -1
        for(String log: logs){
            int startTime = toSecond(log.split("-")[0]);
            int endTime = toSecond(log.split("-")[1]);
            
            viewer[startTime] += 1;
            viewer[endTime] -= 1;
        }
        
        long max = 0; 
        int maxTime = 0; // max로 업데이트 된 구간의 시작점 t는 저장해둠
        sumView[0] = viewer[0];
        long window = sumView[0];
        
        for(int i = 1; i <= playTime; i++) {
            // 동시 시청자 수 계산
            sumView[i] = sumView[i-1] + viewer[i];
            
            window += sumView[i];
            
            // 누적합을 저장하면서 adv_time 크기 이상부터는 max 구간을 계산하면서 지나감
            if(i >= advTime) {
                window -= sumView[i-advTime];
            }
            if (i >= advTime - 1) {
                if (max < window){
                    max = window;
                    maxTime = i-advTime+1;
                }
            }
            
        }
        int h = maxTime/3600;
        int m = (maxTime%3600)/60;
        int s = maxTime%60;
        
        return String.format("%02d:%02d:%02d",h ,m ,s);
    }
    
    // 시간 String -> 초로 변환
    public int toSecond(String time){
        int pow = 2;
        int s = 0;
        for (String t : time.split(":")){
            s += Integer.parseInt(t) * (int)(Math.pow(60, pow--));
        }
        return s;
    }
}
