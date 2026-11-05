class Solution {

    static boolean[] vis;
    static int n = 0;
    static boolean[] check = new boolean[10000005];
    static int answer = 0;

    static void dfs(int depth, String numbers, String temp){
        if(!temp.isEmpty() && temp.charAt(0) == '0') return;

        if(!temp.isEmpty()){
            int num = Integer.parseInt(temp);

            if(!check[num]){
                answer++;
                check[num] = true;
            }
        }

        if(depth == n){
            return;
        }

        for(int i = 0; i < n; i++){
            if(vis[i]) continue;
            vis[i] = true;
            dfs(depth + 1, numbers, temp + numbers.charAt(i));
            vis[i] = false;
        }
    }

    public int solution(String numbers) {
        // int answer = 0;
        n = numbers.length();
        vis = new boolean[n];
        check[0] = true;
        check[1] = true;

        for(int i = 2; i * i < check.length; i++){
            if(check[i]) continue;
            for(int j = i * i; j < check.length; j += i){
                check[j] = true;
            }
        }

        dfs(0, numbers, "");

        return answer;
    }
}