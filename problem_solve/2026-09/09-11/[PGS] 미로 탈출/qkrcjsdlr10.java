import java.util.*;

class Solution {

    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {-1, 0, 1, 0};

    static class Pair {
        int first;
        int second;

        Pair(int first, int second) {
            this.first = first;
            this.second = second;
        }
    }

    public int solution(String[] maps) {
        int answer = 0;

        int n = maps.length;
        int m = maps[0].length();

        int sx = 0, sy = 0;
        int tx = 0, ty = 0;
        int lx = 0, ly = 0;

        int[][] vis = new int[n][m];

        for (int i = 0; i < n; i++) {
            Arrays.fill(vis[i], -1);
        }

        ArrayDeque<Pair> q = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (maps[i].charAt(j) == 'S') {
                    sx = j;
                    sy = i;
                    vis[i][j] = 0;
                } else if (maps[i].charAt(j) == 'E') {
                    tx = j;
                    ty = i;
                } else if (maps[i].charAt(j) == 'L') {
                    lx = j;
                    ly = i;
                }
            }
        }

        // S -> L
        q.offer(new Pair(sy, sx));

        while (!q.isEmpty()) {
            Pair cur = q.poll();

            int y = cur.first;
            int x = cur.second;

            for (int i = 0; i < 4; i++) {
                int nx = x + dx[i];
                int ny = y + dy[i];

                if (nx < 0 || nx >= m || ny < 0 || ny >= n) continue;
                if (maps[ny].charAt(nx) == 'X' || vis[ny][nx] != -1) continue;

                if (maps[ny].charAt(nx) == 'L') {
                    vis[ny][nx] = vis[y][x] + 1;
                    answer += vis[ny][nx];

                    q.clear();
                    break;
                }

                q.offer(new Pair(ny, nx));
                vis[ny][nx] = vis[y][x] + 1;
            }
        }

        q.clear();

        // L에 도착할 수 있었을 때만 두 번째 BFS
        if (vis[ly][lx] != -1) {
            q.offer(new Pair(ly, lx));

            vis = new int[n][m];

            for (int i = 0; i < n; i++) {
                Arrays.fill(vis[i], -1);
            }

            vis[ly][lx] = 0;
        }

        // L -> E
        while (!q.isEmpty()) {
            Pair cur = q.poll();

            int y = cur.first;
            int x = cur.second;

            for (int i = 0; i < 4; i++) {
                int nx = x + dx[i];
                int ny = y + dy[i];

                if (nx < 0 || nx >= m || ny < 0 || ny >= n) continue;
                if (maps[ny].charAt(nx) == 'X' || vis[ny][nx] != -1) continue;

                q.offer(new Pair(ny, nx));
                vis[ny][nx] = vis[y][x] + 1;
            }
        }

        if (vis[ty][tx] != -1 && vis[ly][lx] != -1) {
            answer += vis[ty][tx];
        } else {
            answer = -1;
        }

        return answer;
    }
}