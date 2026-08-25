import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Solution {
	
	static final char PLANE = '.';
	static final char BRICK = '*';
	static final char STEEL = '#';
	static final char WATER = '-';
	
	static final char[] TANK = {'^', 'v', '<', '>'};
	static final char[] MOVE = {'U', 'D', 'L', 'R'};
	static final char SHOOT = 'S';
	
	static final int[] dr = {-1, 1, 0, 0};
	static final int[] dc = {0, 0, -1, 1};
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		int T = Integer.parseInt(br.readLine());
		 
		for (int testCase = 1; testCase <= T; testCase++) {
		    StringTokenizer st = new StringTokenizer(br.readLine());
		    
		    int H = Integer.parseInt(st.nextToken());
		    int W = Integer.parseInt(st.nextToken());
		    
		    char[][] field = new char[H][W];
		    int tankRow = -1, tankCol = -1,  tankDir = -1;
		    
		    for (int r = 0; r < H; r++) {
		    	field[r] = br.readLine().toCharArray();
		    	for (int c = 0; c < W; c++) {
		    		for (int dir = 0; dir < 4; dir++) {
		    			if (field[r][c] == TANK[dir]) {
		    				tankRow = r;
		    				tankCol = c;
		    				tankDir = dir;
		    			}
		    		}
		    	}
		    }
		    
		    int N = Integer.parseInt(br.readLine()); // Unused...
		    
		    char[] command = br.readLine().toCharArray();
		    
		    for (char cmd : command) {
		    	// Move
		    	for (int dir = 0; dir < 4; dir++) {
		    		if (cmd == MOVE[dir]) {
		    			tankDir = dir;
		    			field[tankRow][tankCol] = TANK[tankDir];
		    			
		    			int moveRow = tankRow + dr[dir];
		    			int moveCol = tankCol + dc[dir];
		    			if (isIn(moveRow, moveCol, H, W) && field[moveRow][moveCol] == PLANE) {
		    				field[moveRow][moveCol] = field[tankRow][tankCol];
		    				field[tankRow][tankCol] = PLANE;
		    				
		    				tankRow = moveRow;
		    				tankCol = moveCol;
		    			}
		    			break;
		    		}
		    	}
		    	
		    	// Shoot
		    	if (cmd == SHOOT) {
		    		int shellRow = tankRow + dr[tankDir];
		    		int shellCol = tankCol + dc[tankDir];
		    		
		    		while(isIn(shellRow, shellCol, H, W)) {
		    			if (field[shellRow][shellCol] == BRICK) {
		    				field[shellRow][shellCol] = PLANE;
		    				break;
		    			} else if (field[shellRow][shellCol] == STEEL) {
		    				break;
		    			}
		    			
		    			shellRow += dr[tankDir];
		    			shellCol += dc[tankDir];
		    		}
		    	}
		    }
		    
		    bw.write(String.format("#%d ", testCase));
		    
		    for (int r = 0; r < H; r++) {
		    	for (int c = 0; c < W; c++) {
		    		bw.write(field[r][c]);
		    	}
		    	bw.write("\n");
		    }
		}
		bw.flush();
		bw.close();
		br.close();
    }
	
	private static boolean isIn(int r, int c, int H, int W) {
		return 0 <= r && r < H && 0 <= c && c < W;
	}
}
