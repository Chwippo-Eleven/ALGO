import java.util.HashMap;
import java.util.Map;

class Solution {
    public String[] solution(String[] record) {
        
        int n = record.length;
        
        Map<String, String> nickname = new HashMap<>();
        
        String[][] log = new String[n][];
        int messageCnt = 0;
        
        for (int i = 0; i < n; i++) {
            log[i] = record[i].split(" ");
            String cmd = log[i][0];
            
            if (cmd.equals("Enter")) {
                nickname.put(log[i][1], log[i][2]);
                messageCnt += 1;
            } else if (cmd.equals("Leave")) {
                messageCnt += 1;
            } else if (cmd.equals("Change")) {
                nickname.put(log[i][1], log[i][2]);
            }
        }
        
        String[] logMessage = new String[messageCnt];
        int msgIndex = 0;
        
        for (int i = 0; i < n; i++) {
            String cmd = log[i][0];
            
            if (cmd.equals("Enter")) {
                logMessage[msgIndex++] = nickname.get(log[i][1]) + "님이 들어왔습니다.";
            } else if (cmd.equals("Leave")) {
                logMessage[msgIndex++] = nickname.get(log[i][1]) + "님이 나갔습니다.";
            }
        }
        
        return logMessage;
    }
}
