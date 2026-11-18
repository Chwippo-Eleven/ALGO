import java.util.*;
class Solution {
    int m, n;
    int[] dx = {-1, 1, 0, 0};
    int[] dy = {0, 0, -1, 1};
    String[] board;

    public int solution(String[] board) {
        this.board = board;
        this.m = board.length;
        this.n = board[0].length();

        int startX = 0, startY = 0;
        int goalX = 0, goalY = 0;

        //시작점과 도착점 탐색
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                char c = board[i].charAt(j);

                if (c == 'R') {
                    startX = i;
                    startY = j;
                } else if (c == 'G') {
                    goalX = i;
                    goalY = j;
                }
            }
        }

        Queue<int[]> q = new ArrayDeque<>();
        boolean[][] visited = new boolean[m][n];

        q.offer(new int[]{startX, startY, 0});
        visited[startX][startY] = true;

        while (!q.isEmpty()) {
            int[] cur = q.poll();

            //4방향으로 기울이기
            for (int dir = 0; dir < 4; dir++) {
                int[] next = tilt(cur, dir);

                int nx = next[0];
                int ny = next[1];

                //움직이지 않았다면 탐색하지 않음
                if (nx == cur[0] && ny == cur[1]) continue;

                //이미 방문한 위치라면 탐색하지 않음
                if (visited[nx][ny]) continue;

                //도착점에 도달했다면 이동 횟수 반환
                if (nx == goalX && ny == goalY) {
                    return cur[2] + 1;
                }

                visited[nx][ny] = true;
                q.offer(new int[]{nx, ny, cur[2] + 1});
            }
        }

        return -1;
    }

    //기울여서 도달할 수 있는 최종 위치 반환
    public int[] tilt(int[] point, int dir) {
        int x = point[0];
        int y = point[1];

        while (true) {
            int nx = x + dx[dir];
            int ny = y + dy[dir];

            if (nx < 0 || ny < 0 || nx >= m || ny >= n) break;
            if (board[nx].charAt(ny) == 'D') break;

            x = nx;
            y = ny;
        }

        return new int[]{x, y};
    }
}
