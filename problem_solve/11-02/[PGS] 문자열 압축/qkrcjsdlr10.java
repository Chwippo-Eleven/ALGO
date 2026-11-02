import java.util.*;

class Solution {
    public int solution(String s) {
        int answer = 0;

        int n = s.length();
        int split = 1;
        String res = s;
        while(split <= n / 2){
            String temp = "";
            String sub1 = "";

            int idx = split;
            int cnt = 1;
            for(int i = 0; i < split; i++){
                sub1 += s.charAt(i);
            }
            String sub2 = "";
            while(idx <= n - split){
                sub2 = "";
                for(int i = idx; i < idx + split; i++){
                    sub2 += s.charAt(i);
                }
                if(sub1.equals(sub2)){
                    idx += split;
                    cnt++;
                }else{
                    idx += split;
                    if(cnt != 1) temp += cnt;

                    temp += sub1;

                    sub1 = sub2;
                    cnt = 1;
                }
            }
            if(cnt != 1) temp += cnt;
            temp += sub1;

            for(int i = idx; i < n; i++){
                temp += s.charAt(i);
            }
            if(temp.length() < res.length()){
                res = temp;
            }

            split++;
        }

        System.out.println(res);
        answer = res.length();
        return answer;
    }
}