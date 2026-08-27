import java.util.*;
class Solution {
    int answer = 0;
    List<List<Integer>> gr = new ArrayList<>();
    int[][] types;
    public int solution(int n, int infection, int[][] edges, int k) {
        this.types = new int[n+1][n+1];
        for(int i = 0; i<=n; i++){
            gr.add(new ArrayList<>());
        }
        for(int[] edge : edges){
            gr.get(edge[0]).add(edge[1]);
            gr.get(edge[1]).add(edge[0]);
            types[edge[0]][edge[1]] = edge[2];
            types[edge[1]][edge[0]] = edge[2];
        }
        
        permute(infection, k, n, new int[k], 0);
        return answer;
    }
    //동일한 종류를 다시 열 수 있는 가능성도 있기 때문에 중복 값이 허용되는 순열로 생성해야한다.
    public void permute(int start, int k, int n, int[] order, int idx){
        if(idx == k){
            answer = Math.max(answer, infect(start, order, n, k));
            return;
        }
        for(int i = 1; i<=3; i++){
            order[idx] = i;
            permute(start, k, n, order, idx+1);
        }
    }
    public int infect(int start, int[] order, int n, int k){
        List<Integer> infected = new ArrayList<>();
        infected.add(start);
        //파이프 개방 순서
        for(int i = 0; i<k; i++){
            //탐색용 큐
            Queue<Integer> q = new ArrayDeque<>(infected);
            while(!q.isEmpty()){
                //현재 노드
                int cur = q.poll();
                //현재 노드에서 갈 수 있는 모든 노드
                for(int next : gr.get(cur)){
                    //현재 열린 파이프와 이동할 파이프의 타입이 같고 && 이미 감염된 노드가 아니라면
                    if(order[i] == types[cur][next] && infected.indexOf(next) == -1){
                        //감염된 노드로 체크하고
                        infected.add(next);
                        //이동할 탐색 큐에 넣는다
                        q.offer(next);
                    }
                }
            }
        }
        //감염된 노드의 개수 반환
        return infected.size();
    }
}