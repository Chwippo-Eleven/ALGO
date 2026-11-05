import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;//TIP 코드를 <b>실행</b>하려면 <shortcut actionId="Run"/>을(를) 누르거나

// 에디터 여백에 있는 <icon src="AllIcons.Actions.Execute"/> 아이콘을 클릭하세요.
public class Solution {

    static int[][] board;
    static int d = 0, w = 0, k = 0;
    static int res;

    public static void changeRow(int row, int c) {
        for (int i = 0; i < w; i++) {
            board[row][i] = c;
        }
    }

    public static boolean isValid() {

        for(int i = 0; i < w; i++){
            int cur = board[0][i];
            int cnt = 1;

            for(int j = 1; j < d; j++){
                if(cur == board[j][i]){
                    cnt++;
                }else{
                    cur = board[j][i];
                    cnt = 1;
                }
                if(cnt >= k) break;
            }
            if(cnt < k) return false;
        }

        return true;
    }

    public static void dfs(int row, int count) {
        if (count >= res) {
            return;
        }

        if (row >= d) {
            boolean check = true;

            check = isValid();

            if(check == true){
                res = Math.min(res, count);
            }

            return;
        }

        int[] curRow = board[row].clone();

        dfs(row + 1, count);
        changeRow(row, 1);
        dfs(row + 1, count + 1);
        changeRow(row, 0);
        dfs(row + 1, count + 1);
        board[row] = curRow.clone();
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int tc = Integer.parseInt(br.readLine());
        for(int t = 1; t <= tc; t++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            d = Integer.parseInt(st.nextToken());
            w = Integer.parseInt(st.nextToken());
            k = Integer.parseInt(st.nextToken());
            res = Integer.MAX_VALUE;

            board = new int[d][w];

            for (int i = 0; i < d; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < w; j++) {
                    board[i][j] = Integer.parseInt(st.nextToken());
                }
            }

            dfs(0, 0);

            System.out.println("#" + t + " " + res);
        }

    }
}