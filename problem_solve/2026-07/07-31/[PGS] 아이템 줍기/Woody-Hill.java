import java.util.ArrayDeque;
import java.util.Arrays;

class Solution {
    
    final int N = 51 * 2;       // 크기를 51 이상 주고 홀수에는 Vertex, 짝수에는 Edge
    
    final char BLANK = '.';         // 외곽 빈칸
    final char INNER = 'I';         // 내부 정점
    final char OUTER = 'O';         // 외부 정점
    final char UNKNOWN = '?';       // 초기화용
    
    final char[] LINE = {'|', '-'}; // 가로세로 선
    final char FAKELINE = 'X';      // 가짜 선(이후 진짜 선으로 대체)
    
    // 8방향 이동. 12시 방향부터 시계방향으로
    // 4방향 이동은 0 - 2 - 4 - 6 으로 가능
    final int[] dx = {0, 1, 1, 1, 0, -1, -1, -1};
    final int[] dy = {1, 1, 0, -1, -1, -1, 0, 1};
    
    // 좌표 표현용
    static class Coord {
        int x;
        int y;
        
        Coord(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    
    public int solution(int[][] rectangle, int characterX, int characterY, int itemX, int itemY) {
        
        // 인덱스 범위의 2배 이상 크기로 배열 선언 후 초기화.
        char[][] plane = new char[N][N];
        fillArray(plane, UNKNOWN);
        
        // 홀수 인덱스에는 Vertex, 짝수 인덱스에는 Edge
        // 이 함수에서는 파라미터 값을 모두 2배로 처리해야 한다.
        
        for (int[] rect : rectangle) {
            int left   = 2 * rect[0];
            int bottom = 2 * rect[1];
            int right  = 2 * rect[2];
            int top    = 2 * rect[3];
            
            int x = left;
            int y = bottom;
            
            // 4방향 이동으로 직사각형 그리기
            for (int dir = 0; dir < 8; dir += 2) {

                while (true) {
                    // 일단 모든 정점을 내부 정점으로 처리
                    plane[x][y] = INNER;

                    // 연결 Edge 좌표
                    int eX = x + dx[dir];
                    int eY = y + dy[dir];

                    // 다음 Vertex 좌표
                    int vX = x + 2 * dx[dir];
                    int vY = y + 2 * dy[dir];

                    if (vX < left || vX > right || vY < bottom || vY > top) {
                        break;
                    }
                    
                    // 일단 선 그리지 않고 가짜 선 추가
                    plane[eX][eY] = FAKELINE;
                    
                    // 다음 좌표로
                    x = vX;
                    y = vY;
                }
            }
        }
        
        // 외곽 정점을 OUTER로 바꾼다
        drawOuter(plane);
        
        // 출력용 코드. 테스트 케이스가 예쁘게 나온다. //
        
        int xRange = 21;
        int yRange = 21;
        
        char[][] rotated = rotate90CCW(plane, xRange, yRange);
        
        for (int x = 0; x < 21; x++) {
            for (int y = 0; y < 21; y++) {
                System.out.print(rotated[x][y]);
            }
            System.out.println();
        }
        
        // ------------------------------------- //
        
        // 최소 이동 횟수 구하기
        int move = 0;
        
        int curX = 2 * characterX;
        int curY = 2 * characterY;
        
        int backward = -1;  // 무의미한 값. 첫 이동 이후 정해진다.
        
        // 도착할 때까지 반복
        while (curX != 2 * itemX || curY != 2 * itemY) {
            // 4방향 이동
            for (int dir = 0; dir < 8; dir += 2) {
                // 반대방향으로 돌아가지 않는다!
                if (dir == backward) continue;
                
                int eX = curX + dx[dir];
                int eY = curY + dy[dir];
                
                int vX = curX + 2 * dx[dir];
                int vY = curY + 2 * dy[dir];
                
                // 선으로 연결되어 있고, 외부 정점이어야 한다.
                if (contains(LINE, plane[eX][eY]) && plane[vX][vY] == OUTER) {
                    curX = vX;
                    curY = vY;
                    backward = (dir + 4) % 8;   // 반대 방향의 인덱스
                    break;
                }
            }
            move += 1;
        }
        
        // 전체 외부 정점의 개수 == 외곽의 총 둘레
        int outerCount = countElements(plane, OUTER);
        
        // 만약 긴 쪽으로 이동해서 도착했다면 반대쪽으로 이동한 횟수를 반환한다.
        return Math.min(move, outerCount - move);
    }
    
    // 외부 정점을 표시해 주는 함수
    private void drawOuter(char[][] plane) {
        // 탐색용 스택
        ArrayDeque<Coord> stack = new ArrayDeque<>();
        stack.push(new Coord(0, 0));
        
        while (!stack.isEmpty()) {
            Coord p = stack.pop();
            int curX = p.x;
            int curY = p.y;
            
            // 미확인 정점이 아니면 넘어간다.
            if (plane[curX][curY] != UNKNOWN) continue;
            
            // 기본적으로는 빈칸이다.
            plane[curX][curY] = BLANK;

            for (int dir = 0; dir < 8; dir++) {
                int x = curX + dx[dir];
                int y = curY + dy[dir];
                
                // 인덱스 에러 방지
                if (!isIn(x, y)) continue;
                
                
                if (plane[x][y] == INNER) {
                    // 정점을 만나면 외부 정점으로 표기
                    plane[x][y] = OUTER;
                } else if (plane[x][y] == FAKELINE) {
                    // 가짜 벽을 만나면 진짜 벽 설치
                    int up = plane[x][y + 1];
                    plane[x][y] = (up == INNER || up == OUTER) ? LINE[0] : LINE[1];   
                } else if (plane[x][y] == UNKNOWN) {
                    // 미확인 정점은 스택에 넣기
                    stack.push(new Coord(x, y));
                }
            }
        }
    }
    
    // 유틸 함수. 이차원 배열 채우기
    private void fillArray(char[][] arr, char c) {
        for (char[] row : arr) {
            Arrays.fill(row, c);
        }
    }
    
    // 유틸 함수. 일차원 배열에서 특정 값을 포함하는지 확인
    private boolean contains(char[] arr, char target) {
        for (int element : arr) {
            if (element == target) {
                return true;
            }
        }
        return false;
    }
    
    // 유틸 함수. 이차원 배열에서 특정 값의 개수 세기
    private int countElements(char[][] arr, char target) {
        int cnt = 0;
        for (char[] row : arr) {
            for (char element : row) {
                if (element == target) {
                    cnt += 1;
                }
            }
        }
        return cnt;
    }
    
    // 출력용. 좌표평면처럼 볼 수 있게 반시계 90도 회전
    private char[][] rotate90CCW(char[][] arr) {
        int n = arr.length;
        int m = arr[0].length;
        return rotate90CCW(arr, n, m);
    }
    
    // 오버로딩. 출력 범위 조절이 가능하다.
    private char[][] rotate90CCW(char[][] arr, int n, int m) {
        char[][] rotated = new char[m][n];
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < m; c++) {
                rotated[m - 1 - c][r] = arr[r][c];
            }
        }
        return rotated;
    }
    
    // 유틸 함수. 인덱스 에러 방지
    private boolean isIn(int x, int y) {
        return 0 <= x && x < N && 0 <= y && y < N;
    }
}
