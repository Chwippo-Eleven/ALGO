import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.StringTokenizer;
 
public class Solution {
    public static void main(String[] args) throws Exception {
         
        // SWEA 24420. 집합 비교
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
         
        int T = Integer.parseInt(br.readLine());
         
        for (int testCase = 1; testCase <= T; testCase++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
             
            int sizeA = Integer.parseInt(st.nextToken());
            int sizeB = Integer.parseInt(st.nextToken());
             
            int[] setA = new int[sizeA];
            int[] setB = new int[sizeB];
             
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < sizeA; i++) {
                setA[i] = Integer.parseInt(st.nextToken());
            }
             
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < sizeB; i++) {
                setB[i] = Integer.parseInt(st.nextToken());
            }
             
            Arrays.sort(setA);
            Arrays.sort(setB);
             
            char state = '=';
             
            int idxA = 0;
            int idxB = 0;
             
            while (idxA < sizeA || idxB < sizeB) {
                 
                // 집합 A에 집합 B에 없는 원소가 있는 경우
                if (idxB == sizeB || (idxA < sizeA && setA[idxA] < setB[idxB])) {
                    if (state == '<') {  // 상태가 충돌할 때
                        state = '?';
                        break;
                    }
                    state = '>';
                    idxA++;
                     
                // 집합 B에 집합 A에 없는 원소가 있는 경우
                } else if (idxA == sizeA || (idxB < sizeB && setA[idxA] > setB[idxB])) {
                    if (state == '>') {  // 상태가 충돌할 때
                        state = '?';
                        break;
                    }
                    state = '<';
                    idxB++;
                     
                // 같은 원소를 바라볼 때
                } else {
                    idxA++;
                    idxB++;
                }
            }
             
            bw.write(state + "\n");
        }
        bw.flush();
        bw.close();
        br.close();
    }
}
