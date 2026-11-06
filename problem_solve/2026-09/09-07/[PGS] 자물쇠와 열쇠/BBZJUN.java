class Solution {

    public boolean solution(int[][] key, int[][] lock) {

        int M = key.length;
        int N = lock.length;

        // 자물쇠 주변으로 key가 이동할 수 있도록 공간 확장
        int size = N + (M - 1) * 2;

        int[][] board = new int[size][size];

        // 가운데에 자물쇠 배치
        for (int y = 0; y < N; y++) {
            for (int x = 0; x < N; x++) {
                board[y + M - 1][x + M - 1] = lock[y][x];
            }
        }

        // 4방향 회전
        for (int r = 0; r < 4; r++) {

            // key가 들어갈 수 있는 모든 위치 확인
            for (int y = 0; y <= size - M; y++) {
                for (int x = 0; x <= size - M; x++) {

                    // key를 board에 더하기
                    for (int ky = 0; ky < M; ky++) {
                        for (int kx = 0; kx < M; kx++) {
                            board[y + ky][x + kx] += key[ky][kx];
                        }
                    }

                    // 자물쇠가 열렸는지 확인
                    if (check(board, M, N)) {
                        return true;
                    }

                    // 원상복구
                    for (int ky = 0; ky < M; ky++) {
                        for (int kx = 0; kx < M; kx++) {
                            board[y + ky][x + kx] -= key[ky][kx];
                        }
                    }
                }
            }

            // key 90도 회전
            key = rotate(key);
        }

        return false;
    }


    // 자물쇠 영역이 모두 1인지 확인
    public boolean check(int[][] board, int M, int N) {

        for (int y = M - 1; y < M - 1 + N; y++) {
            for (int x = M - 1; x < M - 1 + N; x++) {

                if (board[y][x] != 1) {
                    return false;
                }
            }
        }

        return true;
    }


    // 시계 방향 90도 회전
    public int[][] rotate(int[][] key) {

        int M = key.length;
        int[][] rotated = new int[M][M];

        for (int y = 0; y < M; y++) {
            for (int x = 0; x < M; x++) {

                rotated[x][M - 1 - y] = key[y][x];
            }
        }

        return rotated;
    }
}
