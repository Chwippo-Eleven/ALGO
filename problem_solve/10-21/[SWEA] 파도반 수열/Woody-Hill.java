import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
 
public class Solution {
    public static void main(String[] args) throws Exception {
         
        // SWEA 3376. 파도반 수열
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
         
        int T = Integer.parseInt(br.readLine());
         
        long[] padovan = padovanSequence(100);
         
        for (int testCase = 1; testCase <= T; testCase++) {
            int N = Integer.parseInt(br.readLine());
            bw.write("#" + testCase + " " + padovan[N] + "\n");
        }
         
        bw.flush();
        bw.close();
        br.close();
    }
     
    public static long[] padovanSequence(int N) {
         
        // 0 ~ N까지의 값을 담을 수열 생성
        long[] padovan = new long[1 + N];
         
        // 6방향의 빗변 길이를 나타내는 정수 배열
        long[] side = new long[6];
         
        // 길이 1인 정사각형을 하나 놓은 초기 상태에서 시작
        padovan[1] = 1;
         
        side[0] = 1;
        side[2] = 1;
        side[4] = 1;
         
        // N까지 파도반 수열 확장
        for (int i = 2; i <= N; i++) {
             
            // 1. 가장 긴 빗변 위치 찾기
            long maxLen = side[0];
            int  maxIdx = 0;
             
            for (int j = 1; j < 6; j++) {
                if (side[j] > side[maxIdx]) {
                    maxLen = side[j];
                    maxIdx = j;
                }
            }
             
            // 2. 가장 긴 빗변 위치에 그만한 길이를 가진 삼각형 추가
            side[maxIdx] = 0;
             
            side[(maxIdx + 5) % 6] += maxLen;   // (maxIdx - 1)과 동일
            side[(maxIdx + 1) % 6] += maxLen;
             
            padovan[i] = maxLen;    // Update
        }
         
        return padovan;
    }
}
