import java.util.*;
class Solution {
    public int solution(String s) {
        int n = s.length();
        int answer = n;
        for(int i = 1; i<=n/2; i++){
            answer = Math.min(answer, compressedLen(s, i, n));
        }
        return answer;
    }
    
    public int compressedLen(String s, int len, int n){
        Queue<String> q = new ArrayDeque<>();
        for(int i = 0; i<n; i+=len){
            q.offer(s.substring(i, Math.min(i+len, n)));
        }
        StringBuilder sb = new StringBuilder();
        while(!q.isEmpty()){
            String cur = q.poll();
            int count = 1;
            while(cur.equals(q.peek())){
                count++;
                q.poll();
            }
            if(count > 1){
                sb.append(count);
            }
            sb.append(cur);
        }
        return sb.length();
    }
}
//길이를 하나씩 늘려가며 압축할 수 있는 부분을 찾는다
//다 잘라서 넣고 압축 후 길이 측정

