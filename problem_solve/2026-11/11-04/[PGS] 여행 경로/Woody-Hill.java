import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class Solution {
    
    static class Flight implements Comparable<Flight> {
        String dept;
        String dest;
        
        Flight(String[] ticket) {
            this.dept = ticket[0];
            this.dest = ticket[1];
        }
        
        @Override
        public int compareTo(Flight o) {
            int cmp = dept.compareTo(o.dept);
            return cmp != 0 ? cmp : dest.compareTo(o.dest);
        }
    }
    
    private static final String START = "ICN";
    
    private boolean[] used;
    private Flight[] flights;
    private String[] answer;
    
    private Map<String, Integer> startIndex;
    
    public String[] solution(String[][] tickets) {
        int n = tickets.length;
        
        used    = new boolean[n];   // 티켓의 사용 여부 기록
        flights = new Flight[n];    // 항공편 정보를 담을 배열
        
        answer  = new String[n + 1];
        
        for (int i = 0; i < n; i++) {
            flights[i] = new Flight(tickets[i]);
        }
        
        Arrays.sort(flights);
        
        startIndex = new HashMap<>();   // 도시별 탐색 시작 인덱스
        
        for (int i = 0; i < n; i++) {
            startIndex.putIfAbsent(flights[i].dept, i);
        }
        
        dfs(START, 0);
        
        return answer;
    }
    
    private boolean dfs(String city, int depth) {
        
        answer[depth] = city;
        
        if (depth == flights.length) {
            return true;
        }
        
        Integer start = startIndex.get(city);
        if (start == null) return false;
        
        for (int i = start; i < flights.length; i++) {
            Flight flight = flights[i];
            
            if (!city.equals(flight.dept)) break;
            if (used[i]) continue;
            
            used[i] = true;
            boolean found = dfs(flight.dest, depth + 1);
            used[i] = false;

            if (found) return true;
        }
        
        return false;
    }
}
