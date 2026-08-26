
import java.io.*;
import java.util.*;

public class Solution {
    static int t, h, w, n, R, C, pos;
    static int[] dr = new int[] {-1, 1, 0, 0};
    static int[] dc = new int[] {0, 0, -1, 1};
    static String s, cmd;
    static char[][] map;
    public static void main(String[] args) throws IOException{
        // 포탄이 벽에 부딫히면 -> 파괴되고, 평지로
        // 포탄이 강철에 부딪히면 -> 아무것도 x

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        t = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= t; tc++) {
            st = new StringTokenizer(br.readLine());
            h = Integer.parseInt(st.nextToken());
            w = Integer.parseInt(st.nextToken());

            map = new char[h][w];
            R = 0; C = 0;

            for (int i = 0; i < h; i++) {
                s = br.readLine();
                for (int j = 0; j < w; j++) {
                    map[i][j] = s.charAt(j);
                    if (map[i][j] != '.' && map[i][j] != '*' && map[i][j] != '#' && map[i][j] != '-') {
                        R = i;
                        C = j;
                        switch (map[i][j]) {
                            case '^':
                                pos = 0;
                                break;
                            case 'v':
                                pos = 1;
                                break;
                            case '<':
                                pos = 2;
                                break;
                            case '>':
                                pos = 3;
                                break;
                        }
                    }
                }
            }

            n = Integer.parseInt(br.readLine());
            cmd = br.readLine();

            for (int i = 0; i < n; i++) {
                char cur = cmd.charAt(i);

                // 그냥 S면 쏘면 되고, S 아니면 그 문자에 해당하는 변수 받아서 방향 바꾸고, 위치 이동시키면 되지 않나?
                if (cur == 'S') {
                    shoot(R, C);
                } else {
                    move(cur);
                }
            }


            System.out.print("#" + tc + " ");
            for (int i = 0; i < h; i++) {
                for (int j = 0; j < w; j++) {
                    System.out.print(map[i][j]);
                }
                System.out.println();
            }

        }
    }
    public static void shoot(int r, int c) {

        while (true) {
            int ddr = r + dr[pos];
            int ddc = c + dc[pos];

            if (!isIn(ddr, ddc)) {
                break;
            }

            if (map[ddr][ddc] == '*') {
                map[ddr][ddc] = '.';
                break;
            }

            if (map[ddr][ddc] == '#') {
                break;
            }

            r += dr[pos];
            c += dc[pos];
        }

    }
    public static void move(char c) {
        char[] ch = new char[] {'^', 'v', '<', '>'};
        int dir = 0;
        switch(c) {
            case 'U':
                dir = 0;
                break;
            case 'D':
                dir = 1;
                break;
            case 'L':
                dir = 2;
                break;
            case 'R':
                dir = 3;
                break;
        }
        pos = dir;
        int ddr = R + dr[dir];
        int ddc = C + dc[dir];

        if (isIn(ddr, ddc) && map[ddr][ddc] == '.') {
            map[R][C] = '.';
            R = ddr;
            C = ddc;
        }
        map[R][C]= ch[pos];
    }
    public static boolean isIn(int r, int c) {
        return r >= 0 && r < h && c >= 0 && c < w;
    }
}