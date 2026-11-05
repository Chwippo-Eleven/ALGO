import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Solution {

    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {-1, 0, 1, 0};



    static class Pair{
        int first;
        int second;

        Pair(int first, int second){
            this.first = first;
            this.second = second;
        }
    }


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int tc = Integer.parseInt(br.readLine());

        for(int t = 1; t <= tc; t++){
            int n = Integer.parseInt(br.readLine());
            int[][] board = new int[n][n];
            int res = 0;

            for(int i = 0; i < n; i++){
                StringTokenizer st = new StringTokenizer(br.readLine());

                for(int j = 0; j < n; j++){
                    board[i][j] = Integer.parseInt(st.nextToken());
                }
            }

            Queue<Pair> q = new ArrayDeque<>();

            int today = 0;

            while(today <= 100){
                int now = 0;

                int[][] temp = new int[n][n];
                int[][] vis = new int[n][n];

                for(int i = 0; i < n; i++){
                    temp[i] = board[i].clone();
                }

                for(int i = 0; i < n; i++){
                    for(int j = 0; j < n; j++){
                        if(temp[i][j] <= today){
                            temp[i][j] = -1;
                        }
                    }
                }

//            for(int[] row : temp){
//                for(int cur : row){
//                    System.out.print(cur + " ");
//                }
//                System.out.println();
//            }
//            System.out.println();

                while(true){
                    boolean check = false;

                    for(int i = 0; i < n; i++){
                        if(!q.isEmpty()) break;
                        for(int j = 0; j < n; j++){
                            if(temp[i][j] != -1){
                                q.add(new Pair(i, j));
                                check = true;
                                break;
                            }
                        }
                    }

                    if(!check) break;

                    while(!q.isEmpty()){

                        Pair cur = q.poll();
                        int curX = cur.second;
                        int curY = cur.first;
                        temp[curY][curX] = -1;

//                    System.out.println(curY + " " + curX);

                        for(int d = 0; d < 4; d++){
                            int nx = curX + dx[d];
                            int ny = curY + dy[d];

                            if(nx < 0 || nx >= n || ny < 0 || ny >= n) continue;
                            if(temp[ny][nx] == -1) continue;

                            temp[ny][nx] = -1;
                            q.add(new Pair(ny, nx));
                        }
                    }
                    now++;
                }

                res = Math.max(now, res);

                today++;
            }

            System.out.println("#" + t + " " + res);
        }
    }
}