import java.util.*;
import java.io.*;

class Solution
{
    static int n;
    static int[][] arr;
    static int[] dx = {0, 0, -1, 1}, dy = {-1, 1, 0, 0};

    public static void main(String args[]) throws Exception
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;
        List<String> indexs = Arrays.asList("left", "right", "up", "down");

        int T = Integer.parseInt(br.readLine().trim());

        for (int test_case = 1; test_case <= T; test_case++)
        {
            st = new StringTokenizer(br.readLine());
            n = Integer.parseInt(st.nextToken());
            int dir = indexs.indexOf(st.nextToken());

            arr = new int[n][n];
            for (int i = 0; i < n; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < n; j++) {
                    arr[i][j] = Integer.parseInt(st.nextToken());
                }
            }

            //move
            //오른쪽으로 밀면 오른쪽 블록부터 이동함
            //deque에 담아서 peekFirst와 현재 블록이 같다면 합치고 pollLast로 flush
            move(dir);

            bw.write("#" + test_case + "\n");
            for (int i = 0; i < n; i++) {
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < n; j++) {
                    sb.append(arr[i][j]);
                    if (j < n - 1) sb.append(' ');
                }
                bw.write(sb.toString());
                bw.write("\n");
            }
        }
        bw.flush();
        bw.close();
    }

    //밀리는 방향 쪽 끝에서부터 읽어서 Deque에 담고, 같은 값이면 합친다
    static void move(int dir) {
        for (int k = 0; k < n; k++) {
            //각 줄의 시작점 = 밀리는 방향의 끝 칸
            int sx = dx[dir] == 1 ? n - 1 : (dx[dir] == -1 ? 0 : k);
            int sy = dy[dir] == 1 ? n - 1 : (dy[dir] == -1 ? 0 : k);

            Deque<Integer> dq = new ArrayDeque<>();
            boolean merged = false; //마지막 블록이 이미 합쳐진 블록인지

            int x = sx, y = sy;
            for (int t = 0; t < n; t++) {
                int v = arr[x][y];
                if (v != 0) {
                    if (!dq.isEmpty() && !merged && dq.peekLast() == v) {
                        dq.pollLast();
                        dq.addLast(v * 2);
                        merged = true;   //합쳐진 블록 체크
                    } else {
                        dq.addLast(v);
                        merged = false;
                    }
                }
                //밀리는 방향의 반대로
                x -= dx[dir];
                y -= dy[dir];
            }

            x = sx; y = sy;
            for (int t = 0; t < n; t++) {
                arr[x][y] = dq.isEmpty() ? 0 : dq.pollFirst();
                x -= dx[dir];
                y -= dy[dir];
            }
        }
    }
}