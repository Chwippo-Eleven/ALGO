import java.util.*;

class Solution {
    public int solution(String s) {
        int answer = Integer.MAX_VALUE;
        
        int len = s.length();
        
        for(int i = 1; i <= len; i++){
            answer = Math.min(answer, press(s, i));
        }
        
        
        return answer;
    }
    
    public int press(String s, int len){
        String result = "";
        
        String cur, prev="";
        
        
        int i, cnt = 1;
        for(i = 0; i < s.length() - len + 1;i++){
            cur = s.substring(i, i+len);
            if(cur.equals(prev)){
                cnt++;
            }else{
                if(!prev.equals("")){
                    if(cnt > 1)
                        result += (cnt+prev);
                    else result+=prev;
                }
                cnt = 1;
            }
            
            prev = cur;
            
            i += (len-1);
        }
        if(cnt > 1) result += (cnt+prev);
        else result+=prev;
        result+=s.substring(i);
        
        return result.length();
    }
}
