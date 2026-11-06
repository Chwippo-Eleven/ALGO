import java.util.*;

class Solution {
    public String solution(String play_time, String adv_time, String[] logs) {
        // 조기 종료
        if (play_time.equals(adv_time)) return "00:00:00";
        
        int total_time = convert(play_time);

        int block = convert(adv_time);
        
        int[] diff = new int[total_time + 2];
        
        for (int i=0; i<logs.length; i++) {
            // 시청자 재생 - 시작, 종료 시간
            int start = convert(logs[i].substring(0,8));
            int end = convert(logs[i].substring(9,17));
            diff[start]++;
            diff[end]--;
        }
        // 초단위 배열 (시간을 크기로 가시화해줌)
        int[] total_second = new int[total_time+1];
        int acc = 0;
        for (int i = 0; i <= total_time; i++) {
            acc += diff[i];
            total_second[i] = acc;
        }
        
        long answer = 0;
        int time = 0;
        // 전체 범위 X block했더니 시간 초과떠서, 슬라이딩 윈도우로 수정함
        long block_sum = 0;
        for (int i=0; i<block; i++) { // 초기값 지정 (윈도우 크기 = block개: 0 ~ block-1)
            block_sum += total_second[i];
        }
        answer = block_sum;
        time = 0;
        // 핵심로직: 슬라이딩 윈도우
        for (int i=1; i+block-1 < total_time; i++) {
            block_sum += total_second[i+block-1] - total_second[i-1]; // 핵심 코드 (새로운 값 1개 추가, 이전 값 1개 삭제)
            if (answer < block_sum) {
                answer = block_sum;
                time = i;
            }
        }
        
        // 숫자로 표현한 시간 → 문자열로 표현한 시간 
        int hour = time/3600;
        int minute = (time%3600)/60;
        int second = time % 60;
        String result = String.format("%02d", hour) + ":" +  String.format("%02d", minute) 
                        + ":" + String.format("%02d", second);
        
        return result;
    }
    
    // 시간 변환 함수 [단위: 초]
    private int convert(String time) {
        int sum = 0;
        sum += 3600 * (Integer.parseInt(time.substring(0,2)));
        sum += 60 * (Integer.parseInt(time.substring(3,5)));
        sum += Integer.parseInt(time.substring(6,8));
        
        return sum;
    }
}