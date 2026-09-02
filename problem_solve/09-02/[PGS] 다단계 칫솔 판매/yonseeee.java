import java.util.*;

class Solution {
    public int[] solution(String[] enroll, String[] referral, String[] seller, int[] amount) {
        int[] answer = new int[enroll.length];
        
        Map<String, String> parent=new HashMap<>();
        Map<String, Integer> index=new HashMap<>();
        
        for(int i=0;i<enroll.length;i++){
            parent.put(enroll[i], referral[i]);
            index.put(enroll[i], i);
        }
        
        int[]earn=new int[enroll.length+1];
        
        for(int i=0;i<seller.length;i++){
            
            String current=seller[i];
            int money=amount[i]*100;
            
            while(!current.equals("-")&&money>0){
                int up=money/10;
                int mine=money-up;
                
                earn[index.get(current)]+=mine;
                
                current=parent.get(current);
                money=up;
            }
        }
        
        for(int i=0;i<earn.length-1;i++){
            answer[i]=earn[i];
        }
        return answer;
    }
}
