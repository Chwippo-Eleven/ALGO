import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Queue;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();

        List<Integer>[] graph  = new List[1 + n];
        int[] inDegree = new int[1 + n];

        // 그래프 초기화
        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int i = 0; i < m; i++) {
            int a = sc.nextInt();
            int b = sc.nextInt();

            // 중복되는 간선 입력이 있다...
            if (!graph[a].contains(b)) {
                graph[a].add(b);
                inDegree[b] += 1; 
            }
        }

        Queue<Integer> queue = new ArrayDeque<>();
        
        // 진입 차수가 0인 노드를 큐에 넣기
        for (int i = 1; i <= n; i++) {
            if (inDegree[i] == 0) {
                queue.offer(i);
            }
        }

        while (!queue.isEmpty()) {
            int u = queue.poll();

            System.out.print(u + " ");

            for (int v : graph[u]) {
                if (--inDegree[v] == 0) {
                    queue.offer(v);
                }
            }
        }
        // 반복문 끝나면 출력 완료
    }
}
