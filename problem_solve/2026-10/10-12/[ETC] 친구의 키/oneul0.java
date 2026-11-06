import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        List<List<Integer>> gr=  new ArrayList<>();
        for(int i = 0; i<=n; i++){
            gr.add(new ArrayList<>());
        }
        int[] inbound = new int[n+1];
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            gr.get(a).add(b);
            inbound[b]++;
        }
        
        Queue<Integer> q = new ArrayDeque<>();
        boolean[] visited = new boolean[n+1];
        for(int i = 1; i<=n; i++){
            if(inbound[i] == 0 && !visited[i]){
                q.offer(i);
                visited[i] = true;
            }
        }
        while(!q.isEmpty()){
            int cur = q.poll();
            bw.write(cur+" ");
            for(int next : gr.get(cur)){
                inbound[next]--;

                if(inbound[next] == 0){
                    q.offer(next);
                }
            }
        }
        bw.flush();
        br.close();
        bw.close();
    }
}