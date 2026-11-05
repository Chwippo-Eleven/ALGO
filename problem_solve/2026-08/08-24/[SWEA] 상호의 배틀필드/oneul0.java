import java.util.*;
import java.io.*;

class Solution
{
	public static void main(String args[]) throws Exception
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        
		int T = Integer.parseInt(br.readLine());
        
		for(int test_case = 1; test_case <= T; test_case++)
		{
            bw.write("#"+test_case+" ");
            StringTokenizer st = new StringTokenizer(br.readLine());
            int H = Integer.parseInt(st.nextToken());
            int W = Integer.parseInt(st.nextToken());
            char[][] map = new char[H][W];
            int[] pos = new int[3];
            for(int i = 0; i<H; i++){
                String tmp = br.readLine();
                for(int j = 0; j<W; j++){
                    map[i][j] = tmp.charAt(j);
                    
                    if(map[i][j] == '^') pos = new int[]{i, j, 0};
                    else if(map[i][j] == 'v') pos = new int[]{i, j, 1};
                    else if(map[i][j] == '<') pos = new int[]{i, j, 2};
                    else if(map[i][j] == '>') pos = new int[]{i, j, 3};
                }
            }
            int N = Integer.parseInt(br.readLine());
            String cmd = br.readLine();
            if(N > 0) simulation(H, W, N, map, cmd, pos);
            
            //print
            for(int i = 0; i<H; i++){
                bw.write(String.valueOf(map[i]));
                bw.newLine();
            }
		}
        bw.flush();
        br.close();
        bw.close();
	}
    
    static int[] dx = {-1,1,0,0}, dy = {0,0,-1,1};
    public static void move(int dir, char[][] map, int[] pos, char newTank, int H, int W){
        map[pos[0]][pos[1]] = newTank;
        int nx = pos[0] + dx[dir];
        int ny = pos[1] + dy[dir];
        pos[2] = dir;
        if(isIn(H, W, nx, ny)){
            if(map[nx][ny] != '.') return;
            map[pos[0]][pos[1]] = '.';
            map[nx][ny] = newTank;
            pos[0] = nx;
            pos[1] = ny;
        }
    }
    
    public static void shoot(int H, int W, char[][] map, int[] pos){
        int x = pos[0];
        int y = pos[1];
        int dir = pos[2];
        while(true){
            x += dx[dir];
            y += dy[dir];
            if(!isIn(H, W, x,y)) break;
            if(map[x][y] == '#') break;
            if(map[x][y] == '*'){
                map[x][y] = '.';
                break;
            }
        }
    }
    
    public static void simulation(int H, int W, int N, char[][] map, String cmd, int[] pos){
        for(char c : cmd.toCharArray()){
            switch (c) {
                case 'S': shoot(H, W, map, pos); break;
                case 'U': move(0, map, pos, '^', H, W); break;
                case 'D': move(1, map, pos, 'v', H, W); break;
                case 'L': move(2, map, pos, '<', H, W); break;
                case 'R': move(3, map, pos, '>', H, W); break;
                default: break;
            }
        }
    }
    
    public static boolean isIn(int H, int W, int x, int y){
        return x >=0 && y>=0 && x<H && y<W;
    }
}