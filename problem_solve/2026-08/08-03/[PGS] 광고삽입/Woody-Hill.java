class Solution {
    public String solution(String play_time, String adv_time, String[] logs) {
        
        // 정적 크기의 배열 선언. [hh + 1][60][60]으로 최적화 가능
        int[][][] watchTime = new int[100][60][60];
        
        // 영상 시간 추출
        int hh = Integer.parseInt(play_time.substring(0, 2));
        int mm = Integer.parseInt(play_time.substring(3, 5));
        int ss = Integer.parseInt(play_time.substring(6, 8));
        
        // 로그 기록
        for (String log : logs) {
            int h1 = Integer.parseInt(log.substring(0, 2));
            int m1 = Integer.parseInt(log.substring(3, 5));
            int s1 = Integer.parseInt(log.substring(6, 8));
            
            int h2 = Integer.parseInt(log.substring(9, 11));
            int m2 = Integer.parseInt(log.substring(12, 14));
            int s2 = Integer.parseInt(log.substring(15, 17));
            
            // 차분 배열 활용
            watchTime[h1][m1][s1] += 1;
            watchTime[h2][m2][s2] -= 1;
        }
        
        // 각 초당 시청 수 복원 (누적 합 활용)
        for (int h = 0; h <= hh; h++) {
            for (int m = 0; m < 60; m++) {
                for (int s = 0; s < 60; s++) {
                    int prev = 0;
                    
                    if (s > 0) {
                        prev = watchTime[h][m][s - 1];
                    } else if (m > 0) {
                        prev = watchTime[h][m - 1][59];
                    } else if (h > 0) {
                        prev = watchTime[h - 1][59][59];
                    }
                    
                    watchTime[h][m][s] += prev;
                }
            }
        }
        
        // 광고 길이 추출
        int adHours   = Integer.parseInt(adv_time.substring(0, 2));
        int adMinutes = Integer.parseInt(adv_time.substring(3, 5));
        int adSeconds = Integer.parseInt(adv_time.substring(6, 8));
        
        // 저장 편의를 위해 초 단위로 저장. 광고 길이만큼의 구간을 잡고 이동.
        int adDuration = adHours * 3600 + adMinutes * 60 + adSeconds;
        int currDuration = 0;
        
        long viewTime = 0;              // [타입 주의!!] 현재 구간의 총 시청 시간(초) 
        long maxViewTime = 0;           // [타입 주의!!] 구간의 최대 시청 시간(초)
        int maxViewStartInSeconds = 0;  // 총 시청 시간을 최대로 하는 시작점(초)
        
        for (int h = 0; h <= hh; h++) {
            for (int m = 0; m < 60; m++) {
                for (int s = 0; s < 60; s++) {
                    // 광고 길이만큼의 구간을 잡는 과정
                    if (currDuration < adDuration) {
                        viewTime += watchTime[h][m][s];
                        maxViewTime += watchTime[h][m][s];
                        currDuration += 1;
                        continue;
                    }
                    
                    // 현재 시간을 초 단위로 변환
                    int currTimeInSeconds = h * 3600 + m * 60 + s;
                    // 구간의 기존 시작 시간(초)
                    int excludeTimeInSeconds = currTimeInSeconds - adDuration;
                    
                    int excHH = excludeTimeInSeconds / 3600;
                    int excMM = (excludeTimeInSeconds % 3600) / 60;
                    int excSS = excludeTimeInSeconds % 60;
                    
                    // 현재 시간부터 1초만큼의 시청 시간을 더하고, 가장 앞 1초의 시청 시간을 빼 준다.
                    viewTime += watchTime[h][m][s];
                    viewTime -= watchTime[excHH][excMM][excSS];
                    
                    // 최대값 갱신 가능하면 갱신
                    if (viewTime > maxViewTime) {
                        maxViewTime = viewTime;
                        maxViewStartInSeconds = excludeTimeInSeconds + 1;
                    }
                }
            }
        }
        
        int startHour   = maxViewStartInSeconds / 3600;
        int startMinute = (maxViewStartInSeconds % 3600) / 60;
        int startSecond = maxViewStartInSeconds % 60;
        
        String answer = String.format("%02d:%02d:%02d", startHour, startMinute, startSecond);
        return answer;
    }
}
