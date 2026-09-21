package hw;

import java.io.*;
import java.util.*;

public class swea24420_집합_비교 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int tc = Integer.parseInt(br.readLine());

        for (int t = 1; t <= tc; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            int n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());

            Set<Integer> a = new HashSet<>();
            Set<Integer> b = new HashSet<>();

            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < n; i++) {
                a.add(Integer.parseInt(st.nextToken()));
            }

            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < m; i++) {
                b.add(Integer.parseInt(st.nextToken()));
            }

            char answer;

            if (a.equals(b)) {
                answer = '=';
            } else if (a.containsAll(b)) {
                answer = '>';
            } else if (b.containsAll(a)) {
                answer = '<';
            } else {
                answer = '?';
            }

            sb.append(answer).append("\n");
        }

        System.out.print(sb);
    }
}