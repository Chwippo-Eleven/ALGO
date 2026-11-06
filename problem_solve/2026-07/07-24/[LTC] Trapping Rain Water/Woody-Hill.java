class Solution {
    public int trap(int[] height) {
        int n = height.length;

        int water = 0;

        // Left to Right //
        int highest = 0;
        int secondHighest = 0;

        int firstIndex = -1;
        int secondIndex = -1;

        for (int i = 0; i < n; i++) {
            int h = height[i];

            if (h >= highest) {
                
                secondHighest = highest;
                secondIndex = firstIndex;

                highest = h;
                firstIndex = i;

                for (int idx = firstIndex - 1; idx > secondIndex; idx--) {
                    water += secondHighest - height[idx];
                }

                secondHighest = 0;
                secondIndex = -1;
                
            } else if (h >= secondHighest) {
                secondHighest = h;
                secondIndex = i;
            }
        }
        // ------------- // 

        // Right to Left //
        int boundary = firstIndex;

        highest = 0;
        secondHighest = 0;

        firstIndex = -1;
        secondIndex = -1;

        for (int i = n - 1; i >= boundary; i--) {
            int h = height[i];

            if (h >= highest) {
                
                secondHighest = highest;
                secondIndex = firstIndex;

                highest = h;
                firstIndex = i;

                for (int idx = firstIndex + 1; idx < secondIndex; idx++) {
                    water += secondHighest - height[idx];
                }

                secondHighest = 0;
                secondIndex = -1;
                
            } else if (h >= secondHighest) {
                secondHighest = h;
                secondIndex = i;
            }
        }
        // -------------- //

        return water;
    }
}
