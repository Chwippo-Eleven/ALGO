import java.util.*;
class Solution {
    final int PRICE = 100;
    int n;
    int[] parents, profits;
    public int[] solution(String[] enroll, String[] referral, String[] seller, int[] amount) {
        this.n = enroll.length;
        parents = new int[n];
        profits = new int[n];
        Map<String, Integer> idx= new HashMap<>();
        idx.put("-", -1);
        for(int i = 0;i<n; i++){
            idx.put(enroll[i], i);
        }
        for(int i = 0; i<n; i++){
            if(referral[i] != "-") parents[i] = -1;
            parents[i] = idx.get(referral[i]);
        }
        
        for(int i = 0; i<seller.length; i++){
            int cur = idx.get(seller[i]);
            int money = amount[i]*PRICE;
            
            while(cur != -1 && money > 0){
                int kickback = money/10;
                profits[cur] += (money-kickback);
                money/=10;
                cur = parents[cur];
            }
        }
        
        return profits;
    }
}