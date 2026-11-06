class Solution {
    public int solution(int[] queue1, int[] queue2) {
        int n = queue1.length;
        
        long sum1 = 0, sum2 = 0;
        
        for (int i = 0; i < n; i++) {
            sum1 += queue1[i];
            sum2 += queue2[i];
        }
        
        int s1 = 0, s2 = n;
        int answer = 0;
        
        while (sum1 != sum2 && answer < 3 * n) {
            if (sum1 < sum2) {
                int value = (s2 < n) ? queue1[s2] : queue2[s2 - n];
                sum1 += value;
                sum2 -= value;
                s2 = (s2 + 1) % (2 * n);
            } else {
                int value = (s1 < n) ? queue1[s1] : queue2[s1 - n];
                sum1 -= value;
                sum2 += value;
                s1 = (s1 + 1) % (2 * n);
            }
            answer += 1;
        }
            
        return answer < 3 * n ? answer : -1;
    }
}
