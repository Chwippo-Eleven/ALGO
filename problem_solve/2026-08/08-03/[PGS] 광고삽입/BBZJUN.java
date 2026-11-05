class Solution {
    public String solution(String play_time, String adv_time, String[] logs) {
        
        //초로 보면 겹치는 시간대 타임을 확인하는데, 계산이 더 쉬움
        int playTime = toSeconds(play_time); // 동영상 재생시간 길이 초로 바꾸기
        int advTime = toSeconds(adv_time); //광고시간 초로 체크
        
        int[] diff = new int[playTime + 1]; // 동영상들의 시작점과 끝점을 표시할 배열(누적으로 해서 한번에 총 광고 시간을 체크할거임)누적합이라
        
        // 각 영상 재생 체크
        for (String log : logs) {
            String[] parts = log.split("-");
            int start = toSeconds(parts[0]);
            int end = toSeconds(parts[1]);
            diff[start] += 1; // (이제 해당부분부터 시작하는지 체크+1 해서 누적해서 더해주다가)
            diff[end] -= 1; // (끝나는지 체크하여 -1로 누적해서 다시 더해줌)
        }
        
         // 각 영상들로 모은 시작~끝을 바탕으로 총 누적을 계산
        int[] viewCount = new int[playTime + 1];
        int cnt = 0;
        for (int t = 0; t < playTime; t++) {
            cnt += diff[t]; // 위에서 설명한 시작과 끝을 +1,-1로 값을 더해주면서 누적으로 더할 수 있게된다.
            viewCount[t] = cnt;
        }
        
        // 여기서도 시간 그냥 viewCount로 계산하고자 했는데, 시간초과가 난다. 중첩으로 viewCount를 직접 더해서 max를 찾으려고 했다.이를 위해서 합이 얼마인지 모든 가능한 시작점 해서 구해서 비교해야함
        // long으로 해서!
        // 미리 해당구간까지 얼마나 인지 합을 구하고, 나중에 T를 구하고 싶으면 T - (T-1)로 때린다
        long[] accTime = new long[playTime + 1];
        accTime[0] = 0L; // long초기화는 L해야함
        for (int t = 0; t < playTime; t++) {
            accTime[t + 1] = accTime[t] + viewCount[t];
        }
        
        // 최대 구하기
        long maxVal = -1L;
        int bestStart = 0;
        for (int i = 0; i <= playTime - advTime; i++) {
            long windowSum = accTime[i + advTime] - accTime[i];
            if (windowSum > maxVal) {
                maxVal = windowSum;
                bestStart = i;
            }
        }
        
        return toTimeString(bestStart);
}
    public int toSeconds(String time) {
        String[] p = time.split(":");
        int h = Integer.parseInt(p[0]);
        int m = Integer.parseInt(p[1]);
        int s = Integer.parseInt(p[2]);
        return h * 3600 + m * 60 + s;
    }
    
    public String toTimeString(int totalSeconds) {
        int h = totalSeconds / 3600;
        int m = (totalSeconds % 3600) / 60;
        int s = totalSeconds % 60;
        return String.format("%02d:%02d:%02d", h, m, s);
    }
}
