import java.util.*;

class Solution {
    
    
    public String[] solution(String[] record) {
        String[] answer = {};
        List<String> res = new ArrayList<>();
        Map<String, String> mp = new HashMap<>();
        
        for(String re : record){
            String[] temp = re.split(" ");
            String cmd = temp[0];
            String uid = temp[1];
            String name = "";
            
            if(temp.length == 3){
                name = temp[2];
            }
            
            if(cmd.equals("Enter") || cmd.equals("Change")){
                mp.put(uid, name);
            }
        }
        
        for(String re : record){
            String[] temp = re.split(" ");
            String cmd = temp[0];
            String uid = temp[1];
            String name = "";
            
            if(temp.length == 3){
                name = temp[2];
            }
            
            if(cmd.equals("Enter")){
                res.add(mp.get(uid)+"님이 들어왔습니다.");
            }else if(cmd.equals("Leave")){
                res.add(mp.get(uid)+"님이 나갔습니다.");
            }
        }
        
        answer = res.toArray(new String[0]);
        
        return answer;
    }
}