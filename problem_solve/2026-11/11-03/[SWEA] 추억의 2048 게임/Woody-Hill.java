import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;
 
public class Solution {
 
    public static void main(String[] args) throws Exception{
         
        // SWEA 6109. 추억의 2048게임
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
         
        int T = Integer.parseInt(br.readLine());
         
        for (int testCase = 1; testCase <= T; testCase++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
             
            int N = Integer.parseInt(st.nextToken());
            String S = st.nextToken();
             
            int[][] board = new int[N][N];
             
            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                 
                for (int j = 0; j < N; j++) {
                    board[i][j] = Integer.parseInt(st.nextToken());
                }
            }
             
            // 주어진 방향에 따라 중력 벡터 적용
            if (S.equals("left")) {
                applyGravity(board, 0, -1);
                 
            } else if (S.equals("right")) {
                applyGravity(board, 0, 1);
                 
            } else if (S.equals("up")) {
                applyGravity(board, -1, 0);
                 
            } else if (S.equals("down")) {
                applyGravity(board, 1, 0);
            }
             
            // 결과 출력
            bw.write("#" + testCase + "\n");
             
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    bw.write(board[i][j] + " ");
                }
                bw.newLine();
            }
        }
         
        bw.flush();
        bw.close();
        br.close();
    }
     
    // 중력 적용 방향에 따라 board를 변경하는 함수
    private static void applyGravity(int[][] board, int dr, int dc) {
        int R = board.length;
        int C = board[0].length;
         
        boolean vertical = (dr != 0);
        int direction = vertical ? dr : dc;
         
        int lines  = vertical ? C : R;  // 중력 적용 대상
        int length = vertical ? R : C;  // 중력 적용 방향
         
        for (int line = 0; line < lines; line++) {
             
            int write = direction == 1 ? length - 1 : 0;
             
            for (int read = write; 0 <= read && read < length; read -= direction) {
                int r = vertical ? read : line;
                int c = vertical ? line : read;
                 
                if (board[r][c] == 0) continue;
                 
                int wr = vertical ? write : line;
                int wc = vertical ? line : write;
                 
                for (int pair = read - direction; 0 <= pair && pair < length; pair -= direction) {
                    int pr = vertical ? pair : line;
                    int pc = vertical ? line : pair;
                     
                    // 같은 수를 발견하면
                    if (board[pr][pc] == board[r][c]) {
                        board[r][c] <<= 1;    // 값 2배
                        board[pr][pc] = 0;  // 초기화
                        break;
                     
                    // 0을 제외한 다른 수를 발견하면 조기 종료
                    } else if (board[pr][pc] != 0) {
                        break;
                    }
                }
                 
                if (read != write) {
                    board[wr][wc] = board[r][c];
                    board[r][c] = 0;
                }
                 
                write -= direction;
            }
        }
    }
}
