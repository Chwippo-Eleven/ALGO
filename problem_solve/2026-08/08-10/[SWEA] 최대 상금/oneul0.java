import java.util.*;
import java.io.*;

class Solution
{
    static int answer= 0, n = 0;
    static Set<String>[] set;
    public static void main(String args[]) throws Exception
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        Scanner sc = new Scanner(System.in);
        int T = Integer.parseInt(br.readLine());
        for(int test_case = 1; test_case <= T; test_case++)
        {
            answer= 0;

            StringTokenizer st = new StringTokenizer(br.readLine());
            char[] initScore = st.nextToken().toCharArray();
            n = Integer.parseInt(st.nextToken());
            set = new HashSet[n+1];
            for(int i =0; i<=n; i++){
                set[i] = new HashSet<>();
            }
            dfs(initScore, 0);
            bw.write("#"+test_case+" " + answer +"\n");
        }
        bw.flush();
        br.close();
        bw.close();
    }

    //무식하게 간다
    public static void dfs(char[] score, int idx){
        if(idx == n){
            int num = makeNum(score);
            answer = Math.max(answer, num);
            return;
        }

        String key = Arrays.toString(score);

        if(!set[idx].add(key)) return;

        for(int i = 0; i<score.length; i++){
            for(int j = i+1; j<score.length; j++){
                swap(score, i, j);

                dfs(score, idx+1);

                swap(score, i, j);
            }
        }
    }

    public static void swap(char[] score, int a, int b){
        char tmp = score[a];
        score[a] = score[b];
        score[b] = tmp;
    }

    public static int makeNum(char[] score){
        return Integer.parseInt(new String(score));
    }
}