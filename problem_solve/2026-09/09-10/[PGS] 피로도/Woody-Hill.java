class Solution {
    
    int[][] dungeons;
    boolean[] visited;
    
    int n;
    int maxVisit;
    
    public int solution(int k, int[][] dungeons) {
        n = dungeons.length;
        maxVisit = 0;
        
        this.dungeons = dungeons;
        visited = new boolean[n];
        
        explore(0, k);
        return maxVisit;
    }
    
    void explore(int cnt, int energy) {
        
        for (int i = 0; i < n; i++) {
            if (visited[i]) continue;
            
            if (energy >= dungeons[i][0]) {
                visited[i] = true;
                explore(cnt + 1, energy - dungeons[i][1]);
                visited[i] = false;
            }
        }
        
        maxVisit = Math.max(maxVisit, cnt);
    }
}
