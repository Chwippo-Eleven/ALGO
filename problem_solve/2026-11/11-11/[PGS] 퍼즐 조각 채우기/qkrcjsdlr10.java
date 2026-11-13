import java.util.*;

class Solution {

    int[] dx = {0, 1, 0, -1};
    int[] dy = {-1, 0, 1, 0};
    ArrayList<ArrayList<int[][]>> pList = new ArrayList<>();
    ArrayList<int[][]> bList = new ArrayList<>();

    class Pair{
        int first, second;

        Pair(int first, int second){
            this.first = first;
            this.second = second;
        }
    }

    public int[][] rotPuzzle(int[][] puzzle){
        int n = puzzle.length;
        int m = puzzle[0].length;

        int[][] temp = new int[m][n];
        // System.out.println(n + " " + m);
        for(int i = 0; i < n; i++){
            for(int j = 0; j < m; j++){
                temp[j][n - 1 - i] = puzzle[i][j];
            }
        }

        // for(int[] row : temp){
        //     for(int x : row){
        //         System.out.print(x);
        //     }
        //     System.out.println();
        // }
        // System.out.println();

        return temp;
        //화이팅하ㅔ요 줴줴이야
    }

    public void bfs(int[][] board, int target){
        int n = board.length;
        int m = board[0].length;
        int cnt = 0;
        boolean[][] vis = new boolean[n][m];

        for(int i = 0; i < n; i++){
            for(int j = 0; j < m; j++){
                if(board[i][j] == target){
                    if(vis[i][j]) continue;
                    Deque<Pair> q = new ArrayDeque<>();
                    Deque<Pair> qPair = new ArrayDeque<>();
                    int sx = Integer.MAX_VALUE, sy = Integer.MAX_VALUE, ex = 0, ey = 0;

                    q.addLast(new Pair(i, j));
                    vis[i][j] = true;


                    while(!q.isEmpty()){
                        Pair cur = q.pollFirst();
                        int y = cur.first;
                        int x = cur.second;

                        qPair.addLast(new Pair(y, x));

                        ex = Math.max(ex, x);
                        ey = Math.max(ey, y);
                        sx = Math.min(sx, x);
                        sy = Math.min(sy, y);

                        for(int d = 0; d < 4; d++){
                            int nx = x + dx[d];
                            int ny = y + dy[d];

                            if(nx < 0 || nx >= m || ny < 0 || ny >= n) continue;
                            if(board[ny][nx] != target) continue;
                            if(vis[ny][nx]) continue;
                            vis[ny][nx] = true;
                            q.addLast(new Pair(ny, nx));
                        }
                    }

                    int pn = ey - sy + 1;
                    int pm = ex - sx + 1;
                    // System.out.println(pn + " " + pm);
                    int[][] temp = new int[pn][pm];

                    while(!qPair.isEmpty()){
                        Pair cur = qPair.pollFirst();
                        int y = cur.first;
                        int x = cur.second;

                        temp[y - sy][x - sx] = 1;
                    }

                    if(target == 1){
                        pList.add(new ArrayList<>());
                        pList.get(cnt).add(temp);

                        for(int k = 0; k < 3; k++){
                            temp = rotPuzzle(temp);
                            pList.get(cnt).add(temp);
                        }
                    }else{
                        bList.add(temp);
                    }


                    cnt++;
                }
            }
        }

    }

    public int matching(int[][] board, int[][] puzzle){
        int cnt = 0;
        int n = board.length;
        int m = board[0].length;
        if(n != puzzle.length ||
                m != puzzle[0].length){
            return cnt;
        }

        for(int i = 0; i < n; i++){
            for(int j = 0; j < m; j++){
                if(board[i][j] != puzzle[i][j]) return 0;
                if(board[i][j] == 1) cnt++;
            }
        }

        return cnt;
    }

    public int solution(int[][] game_board, int[][] table) {
        int answer = 0;

        bfs(game_board, 0);
        bfs(table, 1);
        boolean[] used1 = new boolean[bList.size()];
        boolean[] used2 = new boolean[pList.size()];
        for(int i = 0; i < bList.size(); i++){
            if(used1[i]) continue;
            for(int j = 0; j < pList.size(); j++){
                if(used1[i]) break;
                if(used2[j]) continue;
                for(int k = 0; k < 4; k++){
                    int m = matching(bList.get(i), pList.get(j).get(k));
                    if(m != 0){
                        used1[i] = true;
                        used2[j] = true;
                        answer += m;
                        break;
                    }

                }
            }
        }


        return answer;
    }
}