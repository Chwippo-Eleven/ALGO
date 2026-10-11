import java.util.*;

class Solution {

    int[] dy = {-1, 0, 1, 0};
    int[] dx = {0, 1, 0, -1};
    int n, m;

    static class Pair {
        int first, second;

        Pair(int first, int second) {
            this.first = first;
            this.second = second;
        }
    }

    public Pair findLand(int[][] grid) {
        for (int y = 0; y < n; y++) {
            for (int x = 0; x < m; x++) {
                if (grid[y][x] == 1) {
                    return new Pair(y, x);
                }
            }
        }

        return new Pair(-1, -1);
    }

    public boolean isIn(int y, int x) {
        return y >= 0 && y < n && x >= 0 && x < m;
    }

    public int maxAreaOfIsland(int[][] grid) {
        int answer = 0;
        n = grid.length;
        m = grid[0].length;

        ArrayDeque<Pair> q = new ArrayDeque<>();

        while (true) {
            Pair start = findLand(grid);
            if (start.first == -1) break;

            grid[start.first][start.second] = 0;
            q.offer(start);

            int area = 1;

            while (!q.isEmpty()) {
                Pair cur = q.poll();

                for (int d = 0; d < 4; d++) {
                    int ny = cur.first + dy[d];
                    int nx = cur.second + dx[d];

                    if (!isIn(ny, nx)) continue;
                    if (grid[ny][nx] == 0) continue;

                    grid[ny][nx] = 0;
                    q.offer(new Pair(ny, nx));
                    area++;
                }
            }

            answer = Math.max(answer, area);
        }

        return answer;
    }
}