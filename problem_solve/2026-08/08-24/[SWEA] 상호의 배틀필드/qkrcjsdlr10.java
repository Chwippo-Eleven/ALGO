import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution {

    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {-1, 0, 1, 0};
    static char[] tankDir = {'^', '>', 'v', '<'};

    public static int getDir(char c){
        if(c == '^' || c == 'U'){
            return 0;
        }else if(c == '>' || c == 'R'){
            return 1;
        }else if(c == 'v' || c == 'D'){
            return 2;
        }else if(c == '<' || c == 'L'){
            return 3;
        }

        return -1;
    }

    public static boolean isOut(int x, int y, int h, int w){
        return x < 0 || y < 0 || x >= w || y >= h;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int tc = Integer.parseInt(br.readLine());

        for(int t = 1; t <= tc; t++){
            int h = 0, w = 0, cmdSize = 0, curX = 0, curY = 0;
            String commands = "";
            StringTokenizer st = new StringTokenizer(br.readLine());
            h = Integer.parseInt(st.nextToken());
            w = Integer.parseInt(st.nextToken());

            char[][] board = new char[h][w];

            for(int i = 0; i < h; i++){
                String temp = br.readLine();
                for(int j = 0; j < w; j++){
                    board[i][j] = temp.charAt(j);
                    if(board[i][j] == '^' || board[i][j] == '>' || board[i][j] == 'v' || board[i][j] == '<'){
                        curX = j;
                        curY = i;
                    }
                }
            }

            cmdSize = Integer.parseInt(br.readLine());
            commands = br.readLine();

            char[] cmd = new char[cmdSize];

            for(int i = 0; i < cmdSize; i++){
                cmd[i] = commands.charAt(i);
            }

            int curD = getDir(board[curY][curX]);

            for(char c : cmd){
                if(c == 'S'){
                    int tempX = curX;
                    int tempY = curY;
                    while(true){
                        int nx = tempX + dx[curD];
                        int ny = tempY + dy[curD];

                        if(isOut(nx, ny, h, w)) break;
                        if(board[ny][nx] == '#') break;
                        if(board[ny][nx] == '*'){
                            board[ny][nx] = '.';
                            break;
                        }
                        tempX = nx;
                        tempY = ny;
                    }
                    continue;
                }
                int dir = getDir(c);
                if(dir != -1) curD = dir;

                int nx = curX + dx[curD];
                int ny = curY + dy[curD];
                board[curY][curX] = tankDir[getDir(c)];
                if(isOut(nx, ny, h, w)) continue;
                if(board[ny][nx] != '.') continue;

                board[curY][curX] = '.';

                curX = nx;
                curY = ny;

                board[curY][curX] = tankDir[getDir(c)];
            }

            System.out.print("#" + t + " ");

            for(char[] row : board){
                for(char cur : row){
                    System.out.print(cur);
                }
                System.out.println();
            }
        }
    }
}