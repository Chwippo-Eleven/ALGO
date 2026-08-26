import java.util.*;

class Solution {
    int[] dx = { 0, 0, -1, 1 };
    int[] dy = { -1, 1, 0, 0 };

    int[] answer = new int[5];

    public int[] solution(String[][] places) {

        for (int i = 0; i < 5; i++) { // 테이블 인덱스

            char[][] arr = new char[5][5];
            boolean[][] visited = new boolean[5][5];

            for (int j = 0; j < 5; j++) { // 행뭉텅이
                arr[j] = places[i][j].toCharArray();
            }

            // 완전 탐색 함수 호출
            search(arr, visited, i);
        }

        return answer;
    }

    private void search(char[][] arr, boolean[][] visited, int index) {
        // 완전탐색하며 bfs함수 호출
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (arr[i][j] == 'P') {
                    if (!bfs(i, j, arr, visited)) {
                        answer[index] = 0;
                        return; // 함수 종료
                    }
                }
            }
        }
        answer[index] = 1;
    }

    private boolean bfs(int sx, int sy, char[][] arr, boolean[][] visited) {
        Queue<Node> q = new ArrayDeque<>();
        q.offer(new Node(sx, sy, 0));
        visited[sx][sy] = true;

        while (!q.isEmpty()) {
            Node cur = q.poll();
            int x = cur.x;
            int y = cur.y;
            int dist = cur.dist;
            if (dist >= 2)
                continue; // 맨해튼 거리 2이하
            for (int i = 0; i < 4; i++) {
                int nx = x + dx[i];
                int ny = y + dy[i];
                // 범위 벗어남 체크
                if (nx < 0 || ny < 0 || nx >= 5 || ny >= 5)
                    continue;
                if (visited[nx][ny])
                    continue;
                // X칸이면 이동할 수 없음
                if (arr[nx][ny] == 'X')
                    continue;

                int newDist = cur.dist + 1;

                if (arr[nx][ny] == 'P') {
                    return false;
                }

                q.offer(new Node(nx, ny, newDist));
                visited[nx][ny] = true;
            }

        }

        return true;

    }

    class Node {
        int x, y, dist;

        Node(int x, int y, int dist) {
            this.x = x;
            this.y = y;
            this.dist = dist;
        }
    }
}
// 상하좌우 한 칸씩 이동
// X칸으로는 이동 X
// O || P로만 이동 O
// 맨해튼 거리 2이하일 때, P만나면 위반
