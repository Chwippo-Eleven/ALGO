import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());

        System.out.println(bfs(n));

    }

    public static int bfs(int n) {

        Queue<int[]> queue = new ArrayDeque<>();
        boolean[] visited = new boolean[n + 1];

        queue.offer(new int[] {n, 0});
        visited[n] = true;

        
        while (!queue.isEmpty()) {
            int[] cur = queue.poll();

            int curNum = cur[0];
            int curCnt = cur[1];

            if (curNum == 1) return curCnt;

            // 다음으로 만들 수 있는 숫자들을 배열로 생성
            int[] nextNum = {curNum - 1,
                    curNum + 1,
                    curNum % 2 == 0 ? curNum / 2 : -1,
                    curNum % 3 == 0 ? curNum / 3 : -1
            };

            for (int next : nextNum) {
                if (next < 1 || next > n || visited[next]) continue;
                visited[next] = true;
                queue.offer(new int[] {next, curCnt + 1});
            }
        }

        return -1;

    }
}
