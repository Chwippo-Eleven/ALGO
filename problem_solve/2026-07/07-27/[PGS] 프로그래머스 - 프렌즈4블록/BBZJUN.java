import java.util.*;

class Solution {
    public int solution(int m, int n, String[] board) {
        int answer = 0;
        int[][] arr = new int[n][m];
        char[][] charArr = new char[n][m];

        int index = 0;
        for (String st : board) {
            charArr[index++] = st.toCharArray();
        }

        int tmp = -1;
        while (true) {
            tmp = 0;
            // 1로 지울거 체크
            for (int i = 0; i < n - 1; i++) {
                for (int j = 0; j < m - 1; j++) {
                    if (charArr[i][j] == charArr[i + 1][j]
                            && charArr[i + 1][j] == charArr[i][j + 1]
                            && charArr[i][j + 1] == charArr[i + 1][j + 1]
                            && arr[i][j] != 2 && arr[i + 1][j] != 2
                            && arr[i][j + 1] != 2 && arr[i + 1][j + 1] != 2) {
                        tmp = 1;
                        arr[i][j] = 1;
                        arr[i + 1][j] = 1;
                        arr[i][j + 1] = 1;
                        arr[i + 1][j + 1] = 1;
                    }
                }
            }

            if (tmp == 0) break; // 지울거 없으면 깸

            // 답++
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    if (arr[i][j] == 1) answer++;
                }
            }

            // 열별로 보면서 쳌
            for (int j = 0; j < m; j++) {
                //해당 열에서 살아남는거
                char[] survivors = new char[n];
                int cnt = 0;

                // 안 겹친거 저장
                for (int i = 0; i < n; i++) {
                    if (arr[i][j] == 0) {
                        survivors[cnt++] = charArr[i][j];
                    }
                }

                // 문자 아래부터 넣어줌
                int bottom = n - 1;
                for (int k = cnt - 1; k >= 0; k--) {
                    charArr[bottom][j] = survivors[k];
                    arr[bottom][j] = 0; // 다시 이걸로 겹치는거 볼거라 0 초기화
                    bottom--;
                }

                // 위는 이제 안 쓸거니까 2
                for (int i = 0; i <= bottom; i++) {
                    arr[i][j] = 2;
                }
            }
        }

        return answer;
    }
}
