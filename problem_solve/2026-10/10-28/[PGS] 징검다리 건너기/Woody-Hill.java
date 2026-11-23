import java.util.Arrays;

class Solution {
    public int solution(int[] stones, int k) {
        
        int n = stones.length;
        
        // 각각 앞과 뒤 간격을 기록하는 배열 (모두 1로 초기화)
        int[] prevOffset = new int[n];
        int[] nextOffset = new int[n];
        Arrays.fill(prevOffset, 1);
        Arrays.fill(nextOffset, 1);
        
        int[][] stonesWithIndex = new int[n][2];
        
        for (int i = 0; i < n; i++) {
            stonesWithIndex[i][0] = stones[i];
            stonesWithIndex[i][1] = i;
        }
        
        Arrays.sort(stonesWithIndex, (a, b) -> {
            int cmp = Integer.compare(a[0], b[0]);
            return cmp != 0 ? cmp : Integer.compare(a[1], b[1]);
        });
        
        // 정렬된 배열에서 하나씩 꺼내기
        for (int[] entry : stonesWithIndex) {
            int stone = entry[0];
            int index = entry[1];
            
            // 앞뒤 간격 갱신
            int prevIndex = index - prevOffset[index];
            if (prevIndex >= 0) {
                nextOffset[prevIndex] += nextOffset[index];
            }

            int nextIndex = index + nextOffset[index];
            if (nextIndex < n) {
                prevOffset[nextIndex] += prevOffset[index];
            }

            // 간격이 k를 초과하면 현재 돌의 값을 반환
            if (prevIndex >= 0 && nextOffset[prevIndex] > k) {
                return stone;
            }
            if (nextIndex < n && prevOffset[nextIndex] > k) {
                return stone;
            }
        }
        
        return stonesWithIndex[n - 1][0];
    }
}
