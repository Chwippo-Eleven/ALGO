class Solution {
    public long solution(int cap, int n, int[] deliveries, int[] pickups) {
        long answer = 0;
        int d = n - 1, p = n - 1;

        while (true) {
            while (d >= 0 && deliveries[d] == 0) d--;
            while (p >= 0 && pickups[p] == 0) p--;
            if (d < 0 && p < 0) break;

            answer += (long) (Math.max(d, p) + 1) * 2;

            int c = cap;
            while (d >= 0 && c > 0) {
                int t = Math.min(c, deliveries[d]);
                deliveries[d] -= t; c -= t;
                if (deliveries[d] == 0) d--;
            }
            c = cap;
            while (p >= 0 && c > 0) {
                int t = Math.min(c, pickups[p]);
                pickups[p] -= t; c -= t;
                if (pickups[p] == 0) p--;
            }
        }
        return answer;
    }
}