import java.io.*;
import java.util.*;

public class Solution {
    static int t, cnt, res;
    static String val;
    static Set<String> set;
    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        t = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= t; tc++){
            st = new StringTokenizer(br.readLine());
            val = st.nextToken();
            cnt = Integer.parseInt(st.nextToken());

            // 모든 경우의 수를 따지기
            // 가지치기 안하면 맨 마지막 케이스에서 터지네.
            // 한 번 봤던 애는 또 안보게끔 하면 될 듯
            // 근데, 중요한건 count 3에서 1234랑, count 1에서 1234는 완전히 다르다는거지
            // count 1일때는 더 바꿔서 최댓값을 만들 수 있는 후보임
            char[] cur = val.toCharArray();
            res = 0;
            set = new HashSet<>();

            dfs(cur, 0);
            System.out.println("#" + tc + " " + res);
        }
    }
    public static void dfs(char[] arr, int count){
        String cur = count + " " + new String(arr);
        if (set.contains(cur)){
            return;
        }
        set.add(cur); // 현재 count에서 해당하는 숫자열 set에 넣어주기

        if (count == cnt){
            int sum = 0;
            int pro = 1;
            for (int i = arr.length - 1; i >= 0; i--){
                sum += (arr[i] - '0') * pro;
                pro *= 10;
            }
            res = Math.max(res, sum);
            return;
        }
        for (int i = 0; i < arr.length -1 ; i++){
            for (int j = i + 1; j < arr.length; j++){
                swap(i, j, arr);
                dfs(arr, count + 1);
                swap(i, j, arr); // 백트래킹 무조건 필요. for문 돌면서, 이전 idx에서 바뀐 배열 쓰면 안되니까
            }
        }
    }
    public static void swap(int x, int y, char[] arr){
        char tmp = arr[x];
        arr[x] = arr[y];
        arr[y] = tmp;
    }
}
