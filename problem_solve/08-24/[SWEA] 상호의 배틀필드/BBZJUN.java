import java.util.*;
import java.io.*;


class Solution
{
	public static void main(String args[]) throws IOException 
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine()); 
		for(int test_case = 1; test_case <= T; test_case++)
		{
			StringTokenizer st = new StringTokenizer(br.readLine());
			int H = Integer.parseInt(st.nextToken());
			int W = Integer.parseInt(st.nextToken());

			char[][] arr = new char[H][W];

			int XXX = 0; // 초기위치
			int YYY = 0; // 초기위치
			char startMove = '.'; // 초기방향

			// 표 생성 + 초기 위치 찾기
			for (int i = 0; i < H; i++) {
				String str = br.readLine();
				for (int j = 0; j < W; j++) {
					arr[i][j] = str.charAt(j);
					if (arr[i][j]=='^' || arr[i][j]=='v' || arr[i][j]=='<' || arr[i][j]=='>') { // 첫 입력에서 위치 잡히면
						YYY = i; // Y저장
						XXX = j; // X저장
						startMove = arr[i][j]; // 초기방향
					}
				}
			}

			char dir = startMove;
			int N = Integer.parseInt(br.readLine());
			String in = br.readLine();

			for (int i = 0; i < N; i++) {
				char c = in.charAt(i);
				int dy = 0;
                int dx = 0;

				if (c == 'U') { 
                    dir = '^';
                    dy = -1;
                    dx = 0; 
                }
				if (c == 'D') { 
                    dir = 'v'; 
                    dy = 1;  
                    dx = 0; 
                }
				if (c == 'L') {
                    dir = '<'; 
                    dy = 0;  
                    dx = -1; 
                }
				if (c == 'R') { 
                    dir = '>'; 
                    dy = 0;  
                    dx = 1; 
                }

				if (c == 'U' || c == 'D' || c == 'L' || c == 'R') { // 이동
					int ny = YYY + dy;
					int nx = XXX + dx;
					if (ny >= 0 && ny < H && nx >= 0 && nx < W && arr[ny][nx] == '.') {//갈 수 있는지 체크
						arr[YYY][XXX] = '.';
						YYY = ny;
						XXX = nx;
					}
					arr[YYY][XXX] = dir; // 방향 갱신
				}

				if (c == 'S') {
					int Sdy = 0;
                    int Sdx = 0;

					if (dir == '^') { 
                        Sdy = -1; 
                        Sdx = 0; 
                    }
					if (dir == 'v') { 
                        Sdy = 1;  
                        Sdx = 0; 
                    }
					if (dir == '<') { 
                        Sdy = 0;  
                        Sdx = -1; 
                    }
					if (dir == '>') { 
                        Sdy = 0;  
                        Sdx = 1; 
                    }

					int by = YYY + Sdy;
					int bx = XXX + Sdx;

					while (by >= 0 && by < H && bx >= 0 && bx < W) { //뚫기
						if (arr[by][bx] == '*') {
							arr[by][bx] = '.';
							break;
						}
						if (arr[by][bx] == '#') {
							break;
						}
						by += Sdy;
						bx += Sdx;
					}
				}
			}
			System.out.printf("#%d ", test_case);
            for (int i = 0; i < H; i++) {
				for (int j = 0; j < W; j++) {
                	System.out.printf("%c", arr[i][j]);
                }
                System.out.println();
            }
		}

	}
}
