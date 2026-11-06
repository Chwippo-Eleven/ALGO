import java.util.*;
import java.io.*;

public class Main {
    static int answer;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();

        for (int t = 1; t <= T; t++) {
            int num = sc.nextInt();
            int count = sc.nextInt();
            int size = String.valueOf(num).length();
            int[] arr = new int[size];
            for (int i=0; i<size; i++) {
                arr[i] = Integer.parseInt(String.valueOf(num).substring(i,i+1));
            }
            answer = 0;
            boolean[][] visited = new boolean[1000000][11];
            dfs(0, count, arr, visited, size,num);
            System.out.println("#"+t+" "+answer);
        }

    }

    private static void dfs(int depth, int k, int[] arr, boolean[][] visited, int size, int value) {
        // 종료조건 (k번 다 채움)
        if (depth == k) {
            answer = Math.max(answer, value);
            return;
        }

        // swap
        for (int i=0; i<size; i++) {
            for (int j=i+1; j<size; j++) {
                if (arr[i] == arr[j]) continue;

                int digitI = arr[i]; // swap 전 값 저장
                int digitJ = arr[j];

                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;

                long I = (long) Math.pow(10, size-1-i);
                long J = (long) Math.pow(10, size-1-j);
                int newValue = (int) (value + (digitJ - digitI) * I + (digitI - digitJ) * J);

                if (!visited[newValue][depth]) {
                    visited[newValue][depth] = true; // 방문처리
                    dfs(depth+1, k, arr, visited, size, newValue);
                }

                // 백트래킹
                int back_temp = arr[j];
                arr[j] = arr[i];
                arr[i] = back_temp;

            }
        }

    }
}