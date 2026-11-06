import java.util.*;
class Solution {
    class Pair implements Comparable<Pair> {
        int s, e;

        Pair(int s, int e) {
            this.s = s;
            this.e = e;
        }

        public int compareTo(Pair o) {
            int len1 = this.e - this.s;
            int len2 = o.e - o.s;
            if (len1 == len2) {
                return this.s - o.s;
            }
            return len1 - len2;
        }
    }

    public int[] solution(String[] gems) {
        Set<String> type = new HashSet<>();
        for (String g : gems) {
            type.add(g);
        }
        int n = type.size();
        Map<String, Integer> idxs = new HashMap<>();
        int idx = 0;

        for (String g : type) {
            idxs.put(g, idx++);
        }

        int[] count = new int[n];
        int kinds = 0;
        List<Pair> pairs = new ArrayList<>();
        int l = 0;

        for (int r = 0; r < gems.length; r++) {
            int rightIdx = idxs.get(gems[r]);
            if (count[rightIdx] == 0) {
                kinds++;
            }
            count[rightIdx]++;

            while (kinds == n) {
                pairs.add(new Pair(l, r));

                int leftIdx = idxs.get(gems[l]);

                count[leftIdx]--;

                if (count[leftIdx] == 0) {
                    kinds--;
                }

                l++;
            }
        }

        Collections.sort(pairs);

        Pair answer = pairs.get(0);
        return new int[]{answer.s + 1, answer.e + 1};
    }
}
