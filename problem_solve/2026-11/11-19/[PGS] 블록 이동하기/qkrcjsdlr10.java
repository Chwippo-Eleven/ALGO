import java.util.*;

class Solution {


    int n = 0;
    Set<Drone> st = new HashSet<>();

    int[] dx = {0, 1, 0, -1};
    int[] dy = {-1, 0, 1, 0};

    int[] cx1 = {-1, 1, 1, -1};
    int[] cy1 = {-1, -1, 1, 1};

    int[] cx2 = {1, 1, -1, -1};
    int[] cy2 = {-1, 1, 1, -1};

    public class Drone {
        int y1, x1, y2, x2, time = 0;

        Drone(int y1, int x1, int y2, int x2) {
            if (y1 > y2 || (y1 == y2 && x1 > x2)) {
                this.y1 = y2;
                this.x1 = x2;
                this.y2 = y1;
                this.x2 = x1;
            } else {
                this.y1 = y1;
                this.x1 = x1;
                this.y2 = y2;
                this.x2 = x2;
            }
        }

        @Override
        public boolean equals(Object o) {
            if (o == this) return true;
            if (!(o instanceof Drone)) return false;

            Drone other = (Drone) o;

            return y1 == other.y1 &&
                    x1 == other.x1 &&
                    y2 == other.y2 &&
                    x2 == other.x2;
        }

        @Override
        public int hashCode() {
            return Objects.hash(y1, x1, y2, x2);
        }
    }

    public boolean isIn(int y, int x) {
        return y < n && y >= 0 && x < n && x >= 0;
    }

    public boolean checkRot(int y, int x, int dir, int point, int[][] board) {
        int nx = x + dx[dir];
        int ny = y + dy[dir];

        if (!isIn(ny, nx)) return false;
        if (board[ny][nx] != 0) return false;

        int cx, cy;

        if (point == 1) {
            cx = x + cx1[dir];
            cy = y + cy1[dir];
        } else {
            cx = x + cx2[dir];
            cy = y + cy2[dir];
        }

        if (!isIn(cy, cx)) return false;
        if (board[cy][cx] != 0) return false;

        return true;
    }

    public List<Drone> rotDrone(Drone dr, int[][] board) {
        List<Drone> list = new ArrayList<>();

        int y1 = dr.y1;
        int x1 = dr.x1;
        int y2 = dr.y2;
        int x2 = dr.x2;

        // 두 좌표를 각각 회전축으로 사용
        for (int point = 0; point < 2; point++) {
            int y = (point == 0) ? y1 : y2;
            int x = (point == 0) ? x1 : x2;
            int otherY = (point == 0) ? y2 : y1;
            int otherX = (point == 0) ? x2 : x1;

            int curD = -1;

            for (int d = 0; d < 4; d++) {
                if (y + dy[d] == otherY &&
                        x + dx[d] == otherX) {
                    curD = d;
                    break;
                }
            }

            if (curD == -1) continue;

            // 시계 방향 90도
            int nd = (curD + 1) % 4;
            int ny = y + dy[nd];
            int nx = x + dx[nd];

            if (checkRot(y, x, nd, 1, board)) {
                Drone temp = new Drone(y, x, ny, nx);
                temp.time = dr.time + 1;
                list.add(temp);
            }

            // 반시계 방향 90도
            nd = (curD + 3) % 4;
            ny = y + dy[nd];
            nx = x + dx[nd];

            if (checkRot(y, x, nd, 2, board)) {
                Drone temp = new Drone(y, x, ny, nx);
                temp.time = dr.time + 1;
                list.add(temp);
            }
        }

        return list;
    }

    public int solution(int[][] board) {
        int answer = 0;
        n = board.length;

        ArrayDeque<Drone> q = new ArrayDeque<>();
        q.add(new Drone(0, 0, 0, 1));
        st.add(new Drone(0, 0, 0, 1));

        while(!q.isEmpty()){
            Drone cur = q.pollFirst();
            List<Drone> l = rotDrone(cur, board);

            for(Drone dr : l){
                if (st.contains(dr)) continue;
                st.add(dr);
                q.addLast(dr);
            }
            if ((cur.y1 == n - 1 && cur.x1 == n - 1) ||
                    (cur.y2 == n - 1 && cur.x2 == n - 1)) {
                return cur.time;
            }

            for(int d = 0; d < 4; d++){
                int ny1 = cur.y1 + dy[d];
                int ny2 = cur.y2 + dy[d];
                int nx1 = cur.x1 + dx[d];
                int nx2 = cur.x2 + dx[d];

                Drone next = new Drone(ny1, nx1, ny2, nx2);

                if(!isIn(ny1, nx1) || !isIn(ny2, nx2)) continue;
                if(board[ny1][nx1] == 1 ||board[ny2][nx2] == 1) continue;
                if (st.contains(next)) continue;

                next.time = cur.time + 1;
                st.add(next);
                q.addLast(next);


            }
        }



        return -1;
    }
}
