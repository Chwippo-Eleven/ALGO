import java.util.*;

class Solution {
    String[][] tickets;
    boolean[] used;
    List<String> route;
    
    String[] answer;
    public String[] solution(String[][] tickets) {
        
        
        Arrays.sort(tickets, (a,b)->{
            if(!a[0].equals(b[0]))return a[0].compareTo(b[0]);
            else return a[1].compareTo(b[1]);
        });
        this.tickets = tickets;
        
        used = new boolean[tickets.length];
        route = new ArrayList<>();
        
        route.add("ICN");
        dfs("ICN", 0);
        
        return answer;
    }
    
    public boolean dfs(String current, int count){
        if(count == tickets.length){
            answer = new String[route.size()];
            
            for(int i = 0; i < route.size(); i++){
                answer[i] = route.get(i);
            }
            return true;
        }
        for(int i = 0; i < tickets.length;i++){
            if(used[i])continue;
            if(!tickets[i][0].equals(current))continue;
            
            used[i] = true;
            route.add(tickets[i][1]);
            
            if(dfs(tickets[i][1], count+1))return true;
            
            used[i] = false;
            route.remove(route.size()-1);
        }
        
        return false;
    }
}
