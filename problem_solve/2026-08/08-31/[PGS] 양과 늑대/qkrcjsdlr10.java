import java.util.*;

class Solution {
    
    List<Node>[] graph;
    int answer = 0;
    
    class Node{
        int state;
        int from;
        int to;
        
        Node(int state, int from, int to){
            this.state = state;
            this.from = from;
            this.to = to;
        }
    }
    
    void dfs(int sheep, int wolf, List<Integer> nextList, int[] info){
        answer = Math.max(answer, sheep);
        
        for(int i = 0; i < nextList.size(); i++){
            int next = nextList.get(i);
            
            int nextSheep = sheep;
            int nextWolf = wolf;
            
            if(info[next] == 0){
                nextSheep++;
            }else{
                nextWolf++;
            }
            
            if(nextSheep <= nextWolf) continue;
            
            List<Integer> tempNextList = new ArrayList<>(nextList);
            tempNextList.remove(i);
            
            for (Node child : graph[next]) {
                tempNextList.add(child.to);
            }
            
            dfs(nextSheep, nextWolf, tempNextList, info);
            
        }
    }
    
    public int solution(int[] info, int[][] edges) {
        int n = info.length;
        
        graph = new ArrayList[n + 1];
        
        for (int i = 0; i < info.length; i++) {
            graph[i] = new ArrayList<>();
        }
        
        for(int i = 0; i < edges.length; i++){
            int from = edges[i][0];
            int to = edges[i][1];
            int state = info[to];
            
            graph[from].add(new Node(state, from, to));
        }
        List<Integer> nextList = new ArrayList<>();

        for (Node child : graph[0]) {
            nextList.add(child.to);
        }
        
        dfs(1, 0, nextList, info);
        
        return answer;
    }
}