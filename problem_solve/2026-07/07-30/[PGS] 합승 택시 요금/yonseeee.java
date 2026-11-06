import java.util.*;

class Solution {
    class Node{
        int num;
        int sum;
        public Node(int num, int sum){
            this.num=num;
            this.sum=sum;
        }
    }
    public int solution(int n, int s, int a, int b, int[][] fares) {
    
        int[][]fee = new int[n+1][n+1];
        
        for(int[]fare:fares){
            fee[fare[0]][fare[1]]=fare[2];
            fee[fare[1]][fare[0]]=fare[2];
        }
        
        int[][]dist=new int[n+1][n+1];
        for(int[] d: dist){
            Arrays.fill(d, Integer.MAX_VALUE);
        }
        
        Queue<Node> pq=new PriorityQueue<>((o1, o2)->o1.sum-o2.sum);
        int[] st= {s,a,b};
        
        for(int i=0;i<3;i++){//시작점이 s, a, b일때 각각 구하기
            int[]d=dist[i];
            d[st[i]]=0;
            
            pq.add(new Node(st[i], 0));
            
            while(!pq.isEmpty()){//다익스트라
                Node cur = pq.poll();
                for(int j=1;j<=n;j++){
                    if(fee[cur.num][j]!=0){
                        if(d[j]>cur.sum+fee[cur.num][j]){
                            d[j]=cur.sum+fee[cur.num][j];
                            pq.add(new Node(j, d[j]));
                        }
                      
                    }
                }
            }
        }
        
        int min=Integer.MAX_VALUE;
        
        for(int i=1;i<=n;i++){
            int sum=0;
            for(int j=0;j<3;j++){
                sum+=dist[j][i];
            }
            min=Math.min(sum, min);
        }
        return min;
    }
}
