class Solution {

    static int N = 0;
    static int M = 0;

    public static boolean checkblock(int y, int x, char[][] board) {
        return board[y][x] == board[y][x + 1] &&
               board[y][x] == board[y + 1][x] &&
               board[y][x] == board[y + 1][x + 1];
    }

    public static void removeblock(int y, int x, char[][] temp) {
        for(int i = 0; i < 2; i++) {
            for(int j = 0; j < 2; j++) {
                temp[y + i][x + j] = ' ';
            }
        }
    }

    // 블럭 떨어뜨리기
    public static void slidblock(int y, int x, char[][] map) {
        int ny = y;

        for(int i = y + 1; i < N; i++) {
            if(map[i][x] == ' ') {
                ny = i;
            } else {
                break;
            }
        }

        if(ny != y) {
            map[ny][x] = map[y][x];
            map[y][x] = ' ';
        }
    }

    // 2차원 배열 깊은 복사
    public static char[][] copy(char[][] arr) {
        char[][] result = new char[N][M];

        for(int i = 0; i < N; i++) {
            result[i] = arr[i].clone();
        }

        return result;
    }

    public int solution(int m, int n, String[] board) {

        int answer = 0;

        N = m;
        M = n;

        char[][] map = new char[N][M];

        for(int i = 0; i < N; i++) {
            map[i] = board[i].toCharArray();
        }

        while(true) {

            // 현재 상태 복사
            char[][] temp = copy(map);

            boolean detect = false;

            // 삭제할 블럭 찾기
            for(int i = 0; i < N - 1; i++) {
                for(int j = 0; j < M - 1; j++) {

                    if(map[i][j] == ' ') continue;

                    if(checkblock(i, j, map)) {
                        removeblock(i, j, temp);
                        detect = true;
                    }
                }
            }

            // 삭제된 개수 계산
            int cnt = 0;

            for(int i = 0; i < N; i++) {
                for(int j = 0; j < M; j++) {
                    if(temp[i][j] != map[i][j]) {
                        cnt++;
                    }
                }
            }

            answer += cnt;

            // 더 이상 삭제할 블럭 없음
            if(!detect) {
                break;
            }

            // 삭제 적용
            map = copy(temp);


            // 아래로 떨어뜨리기
            for(int i = N - 2; i >= 0; i--) {
                for(int j = M - 1; j >= 0; j--) {

                    if(map[i][j] != ' ' && map[i + 1][j] == ' ') {
                        slidblock(i, j, map);
                    }
                }
            }
        }

        return answer;
    }
}