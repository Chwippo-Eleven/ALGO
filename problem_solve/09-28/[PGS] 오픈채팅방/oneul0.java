import java.util.*;
class Solution {
    public String[] solution(String[] record) {
        Map<String, String> entered = new HashMap<>();
        for(String str : record){
            String[] strs = str.split(" ");
            if(strs[0].equals("Enter") || strs[0].equals("Change")){
                entered.put(strs[1], strs[2]);
            }
        }
        List<String> result = new ArrayList<>();
        for(int i = 0; i<record.length; i++){
            String[] strs = record[i].split(" ");
            switch(strs[0]){
                case "Enter" :
                    result.add(entered.get(strs[1])+"님이 들어왔습니다.");
                    break;
                case "Leave" :
                    result.add(entered.get(strs[1])+"님이 나갔습니다.");
                    break;
                default: break;
            }
        }
        String[] answer = new String[result.size()];
        for(int i = 0; i<result.size(); i++){
            answer[i] = result.get(i);
        }
        return answer;
    }
}