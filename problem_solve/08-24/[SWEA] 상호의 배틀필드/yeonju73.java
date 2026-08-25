import java.util.*;
import java.io.FileInputStream;

class Solution{
	public static void main(String args[]) throws Exception{
		Scanner sc = new Scanner(System.in);
		int T;
		T=sc.nextInt();
        
		for(int test_case = 1; test_case <= T; test_case++){
            int n = sc.nextInt();
            int m = sc.nextInt();
            sc.nextLine();

            char[][] map = new char[n][m];
            int[] position = new int[2];

            Map<Character, int[]> direc = new HashMap<>();
            direc.put('^', new int[]{-1, 0});
            direc.put('v', new int[]{1, 0});
            direc.put('<', new int[]{0, -1});
            direc.put('>', new int[]{0, 1});
            
            for(int i = 0; i < n; i++){

                char[] line = sc.nextLine().toCharArray();
                
                for(int j = 0; j < m; j++){
                    char c = line[j];

                    if (c == '^' || c == 'v' || c == '<' || c == '>'){
                        position[0] = i;
                        position[1] = j;
                    }
                    map[i][j] = c;
                }
            }
            int tryNum = sc.nextInt();
            sc.nextLine();
            char[] tryArray = sc.nextLine().toCharArray();

            // 시뮬레이션
            for(char t: tryArray) {
                int x = position[0];
                int y = position[1];

                int nextX;
                int nextY;

                if(t == 'U'){
                    nextX = direc.get('^')[0] + x;
                    nextY = direc.get('^')[1] + y;
                    if(0 <= nextX && nextX < n && 0 <= nextY && nextY < m && map[nextX][nextY]=='.'){
                        position[0] = nextX;
                        position[1] = nextY;
                        map[x][y] = '.';
                        map[nextX][nextY] = '^';
                        continue;
                    }
                    map[x][y] = '^';

                }else if(t == 'D'){
                    nextX = direc.get('v')[0] + x;
                    nextY = direc.get('v')[1] + y;
                    if(0 <= nextX && nextX < n && 0 <= nextY && nextY < m && map[nextX][nextY]=='.'){
                        position[0] = nextX;
                        position[1] = nextY;
                        map[x][y] = '.';
                        map[nextX][nextY] = 'v';
                        continue;
                    }
                    map[x][y] = 'v';
                
                }else if(t == 'L'){
                    nextX = direc.get('<')[0] + x;
                    nextY = direc.get('<')[1] + y;
                    if(0 <= nextX && nextX < n && 0 <= nextY && nextY < m && map[nextX][nextY]=='.'){
                        position[0] = nextX;
                        position[1] = nextY;
                        map[x][y] = '.';
                        map[nextX][nextY] = '<';
                        continue;
                    }
                    map[x][y] = '<';
                
                }else if(t == 'R'){
                    nextX = direc.get('>')[0] + x;
                    nextY = direc.get('>')[1] + y;
                    if(0 <= nextX && nextX < n && 0 <= nextY && nextY < m && map[nextX][nextY]=='.'){
                        position[0] = nextX;
                        position[1] = nextY;
                        map[x][y] = '.';
                        map[nextX][nextY] = '>';
                        continue;
                    }
                    map[x][y] = '>';
                
                }
                /**
                 * 전차가 이동을 하려고 할 때, 만약 게임 맵 밖이라면 전차는 당연히 이동하지 않는다.
                    전차가 포탄을 발사하면, 포탄은 벽돌로 만들어진 벽 또는 강철로 만들어진 벽에 충돌하거나 게임 맵 밖으로 나갈 때까지 직진한다.
                    만약 포탄이 벽에 부딪히면 포탄은 소멸하고, 부딪힌 벽이 벽돌로 만들어진 벽이라면 이 벽은 파괴되어 칸은 평지가 된다.
                    강철로 만들어진 벽에 포탄이 부딪히면 아무 일도 일어나지 않는다.
                    게임 맵 밖으로 포탄이 나가면 아무런 일도 일어나지 않는다.
                 */
                else if(t == 'S'){
                    char direction = map[x][y];

                    int dx = direc.get(direction)[0];
                    int dy = direc.get(direction)[1];
                    
                    nextX = x + dx;
                    nextY = y + dy;

                    // 맵 밖으로 나가거나 벽에 부딪힐 때까지 이동
                    while(0 <= nextX && nextX < n && 0 <= nextY && nextY < m){
                        if(map[nextX][nextY] == '*'){
                            map[nextX][nextY] = '.';
                            break;
                        } 
                        if(map[nextX][nextY] == '#'){
                            break;
                        }
                        nextX += dx;
                        nextY += dy;
                    }
                }
            }

            // 출력
            System.out.print("#" + test_case + " ");
            for(int i = 0; i < n; i++){
                for(int j = 0; j < m; j++){
                    System.out.print(map[i][j]);
                }
                System.out.println();
            }
		}
	}
}
