class Solution {
    public int solution(int n, int[][] computers) {
        boolean[] visited = new boolean[n];
        int answer = 0;
        
        for (int i = 0; i < n; i++) {
            if(!visited[i]) {
                dfs(i, computers, visited);
                answer++;
            }
        }
                
        return answer;
    }
    
    void dfs(int current, int[][] arr, boolean[] visited) {
        visited[current] = true;
        
        for (int next = 0; next < arr.length; next++) {
            if (arr[current][next] == 1 && !visited[next]) {
                System.out.println(arr[current][next]);
                dfs(next, arr, visited);
            }
        }
    }

}
