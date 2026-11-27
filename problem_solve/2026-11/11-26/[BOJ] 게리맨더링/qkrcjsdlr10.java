package boj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class boj17471 {
    static int n;
    static int res = Integer.MAX_VALUE;
    static int[] arr;
    static ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
    public static void dfs(int depth, List<Integer> arr, int start){
        if(depth > n){
            return;
        }

        List<Integer> temp = new ArrayList<>();
        if(depth != 0 && depth != n){
            for (int i = 1; i <= n; i++) {
                if(!arr.contains(i)){
                    temp.add(i);
                }
            }

            res = Math.min(res, bfs(arr, temp));
        }


        for(int i = start; i <= n; i++){
            arr.add(i);
            dfs(depth + 1, arr,  i + 1);
            arr.remove(arr.size() - 1);
        }


    }

    public static int bfs(List<Integer> arr1, List<Integer> arr2){

        ArrayDeque<Integer> q = new ArrayDeque<>();
        boolean[] vis1 = new boolean[n + 1];
        vis1[arr1.get(0)] = true;
        q.add(arr1.get(0));

        ArrayList<Integer> temp = new ArrayList<>();
        temp.add(arr1.get(0));

        while(!q.isEmpty()){
            int cur = q.pollFirst();

            for (Integer next : graph.get(cur)) {
                if(vis1[next]) continue;
                if(arr1.contains(next)){
                    vis1[next] = true;
                    q.addLast(next);
                    temp.add(next);
                }
            }

        }


        arr1.sort(null);
        temp.sort(null);

        ArrayList<Integer> temp2 = new ArrayList<>();
        temp2.add(arr2.get(0));
        q.add(arr2.get(0));
        vis1 = new boolean[n + 1];
        vis1[arr2.get(0)] = true;
        while(!q.isEmpty()){
            int cur = q.pollFirst();

            for (Integer next : graph.get(cur)) {
                if(vis1[next]) continue;
                if(arr2.contains(next)){
                    vis1[next] = true;
                    q.addLast(next);
                    temp2.add(next);
                }
            }

        }

        arr2.sort(null);
        temp2.sort(null);
        if(temp.equals(arr1) && temp2.equals(arr2)){
            int sum1 = 0;
            int sum2 = 0;
            for (Integer i : arr1) {
                sum1 += arr[i];
            }
            for (Integer i : arr2) {
                sum2 += arr[i];
            }
            return Math.abs(sum1 - sum2);
        }else{
            return Integer.MAX_VALUE;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        n = Integer.parseInt(br.readLine());

        arr = new int[n + 1];
        StringTokenizer st = new StringTokenizer(br.readLine());

        for (int i = 1; i <= n; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 1; i <= n; i++) {
            st = new StringTokenizer(br.readLine());
            int cnt = Integer.parseInt(st.nextToken());

            for (int j = 0; j < cnt; j++) {
                int num = Integer.parseInt(st.nextToken());
                graph.get(i).add(num);
            }
        }

        dfs(0, new ArrayList<>(), 1);

        System.out.println(res == Integer.MAX_VALUE ? -1 : res);
    }

}
