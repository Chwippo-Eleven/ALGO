import java.util.*;
class Solution {
    String word;
    boolean found = false;
    int count = 1, answer = 0;
    char[] chars = {'A', 'E', 'I', 'O', 'U'};
    public int solution(String word) {
        this.word = word;
        dfs(0, new StringBuilder());
        return answer;
    }
    public void dfs(int depth, StringBuilder sb){
        if(found || depth == 5) return;
                
        for(char c : chars){
            sb.append(c);
            if(sb.toString().equals(word)){
                found = true;
                answer = count;
            }
            count++;
            dfs(depth+1, sb);
            sb.deleteCharAt(sb.length()-1);
        }
    }
}