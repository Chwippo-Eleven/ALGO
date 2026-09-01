import java.util.*;

class Solution {
    public int solution(int[][] routes) {
        int answer = 0;
        int n=routes.length;
        boolean[]used=new boolean[n];
        
        Arrays.sort(routes, (a,b)->a[1]-b[1]);

        
        for(int i=0;i<n;i++){
            int out=routes[i][1];
            
            int j;
            for(j=i+1;j<n;j++){
                if(!(out>=routes[j][0]&&out<=routes[j][1]))break;
            }
            i=j-1;
            answer++;
        }
        return answer;
    }
}
