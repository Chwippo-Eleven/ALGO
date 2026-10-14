class Solution {
    class Pair implements Comparable<Pair>{
        int count;
        int single;
        Pair(int count, int single){
            this.count = count;
            this.single = single;
        }
        public int compareTo(Pair o){
            if(this.count == o.count) return o.single - this.single;
            return this.count - o.count;
        }
    }
    final int maxval = Integer.MAX_VALUE;
    final int minval = Integer.MIN_VALUE;
    public int[] solution(int target) {
        Pair[] dp = new Pair[target+1];
        for(int i = 0; i<=target; i++){
            dp[i] = new Pair(maxval, minval);
        }
        dp[target]=new Pair(0,0);
        
        for (int i = target; i >= 0; i--) {
            if (dp[i].count == maxval) continue;

            for (int n = 1; n <= 20; n++) {
                for (int mul = 1; mul <= 3; mul++) {

                    int s = n * mul;
                    int bonus = (mul == 1) ? 1 : 0;

                    if (i < s) continue;

                    Pair next = new Pair(
                        dp[i].count + 1,
                        dp[i].single + bonus
                    );

                    if (next.compareTo(dp[i - s]) < 0) {
                        dp[i - s] = next;
                    }
                }
            }
            
            if (i >= 50) {
                Pair next = new Pair(
                    dp[i].count + 1,
                    dp[i].single + 1
                );

                if (next.compareTo(dp[i - 50]) < 0) {
                    dp[i - 50] = next;
                }
            }
        }
        
        
        return new int[] {dp[0].count, dp[0].single};
    }
}