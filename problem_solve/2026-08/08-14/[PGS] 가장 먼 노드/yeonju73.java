import java.util.*;
/**
    1번에서 가장 멀리 떨어진 노드가 몇개인지 리턴
    최단 경로로 이동했을 때 간선의 개수가 가장 많은 노드
    가중치가 없는 그래프 -> 간선 개수만 체크하면 됨
    bfs로 풀면됨
**/
class Solution {
    public int solution(int n, int[][] edge) {
        int answer = 0;
        Queue<Integer> queue = new ArrayDeque<>();
        
        // 각 노드로 가는 값 저장
        int[] distance = new int[n+1];
        List<Integer>[] graph = new ArrayList[n+1];
        
        boolean[] visited = new boolean[n+1];
        for(int i = 1; i <= n; i++){
            graph[i] = new ArrayList<>();
        }
        for(int[] e: edge){
            graph[e[0]].add(e[1]);
            graph[e[1]].add(e[0]);
        }
        
        queue.offer(1);
        visited[1] = true;
        
        while(!queue.isEmpty()) {
            int currentNode = queue.poll();
            
            for(int nextNode: graph[currentNode]){
                if(!visited[nextNode]){
                    visited[nextNode] = true;
                    distance[nextNode] = distance[currentNode] + 1;
                    queue.offer(nextNode);
                }
            }
        }
        int maxValue = 0;
        for(int i = 2; i <= n; i++){
            if(distance[i] > maxValue){
                maxValue = distance[i];
                answer=1;
            }
            else if(distance[i] == maxValue){
                answer++;
            }
        }
        
        return answer;
    }
    
}
