import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Solution {

    static Set<Pair> stt = new HashSet<>();
    static int sz;
    static int mx;

    public static class Pair{
        int first;
        int second;

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof Pair)) return false;

            Pair p = (Pair) obj;

            return first == p.first && second == p.second;
        }

        @Override
        public int hashCode() {
            return Objects.hash(first, second);
        }

        public Pair(int first, int second){
            this.first = first;
            this.second = second;
        }
    }

    public static void dfs(char[] arr, int n, int depth){
        int number = 0;
        String temp = "";

        for (int i = 0; i < sz; i++) {
            temp += arr[i];
        }
        number = Integer.parseInt(temp);

        Pair p = new Pair(number, depth);

        if(stt.contains(p)){
            return;
        }

        stt.add(p);

        if(depth == n){
            mx = Math.max(mx, number);
            return;
        }

        for(int i = 0; i < sz - 1; i++){
            for(int j = i + 1; j < sz; j++){
                char tmp = arr[i];
                arr[i] = arr[j];
                arr[j] = tmp;
                dfs(arr, n, depth + 1);
                tmp = arr[i];
                arr[i] = arr[j];
                arr[j] = tmp;
            }
        }
    }

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int tc = Integer.parseInt(br.readLine());
        for(int t = 1; t <= tc; t++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            stt.clear();
            mx = 0;
            String num = st.nextToken();
            int n = Integer.parseInt(st.nextToken());

            sz = num.length();
            char[] arr = new char[sz];

            for(int i = 0; i < sz; i++){
                arr[i] = num.charAt(i);
            }

            dfs(arr, n, 0);

            System.out.println("#" + t + " " +mx);
        }

    }
}