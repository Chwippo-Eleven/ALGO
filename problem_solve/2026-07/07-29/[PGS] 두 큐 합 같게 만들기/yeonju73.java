import java.util.*;

// 각 큐의 원소합을 같게 만드는 최소 작업 수
// 방법이 없는 경우 -1 반환
// 두 큐가 같은 값을 가지고 있어야하니까 홀수는 바로 -1
class Solution {
    public long solution(int[] queue1, int[] queue2) {        
        int left = 0;
        int right = queue1.length;
        long totalSum = 0;
        long[] doubleQueue = new long[right*2];
        
        for (int i = 0; i < right; i++) {
            doubleQueue[i] = queue1[i];
            doubleQueue[i+right] = queue2[i];
            totalSum = totalSum + queue1[i] + queue2[i];
        }
        
        // 홀수일 경우 성공할 수 없음
        if (totalSum % 2 != 0) return -1;
        long target = totalSum / 2;
        
        // 절반 값 보다 크다면 left 오른쪽으로, 작다면 right 오른쪽으로(+1)
        // 큐 길이*3 까지 반복
        long count = 0;
        long windowSum = 0;
        
        for (int i = left; i < right; i++) {
            windowSum += doubleQueue[i];
        }
        
        while (count <= queue1.length * 3){
            // 순환하는 것까진 무의미
            if (left == right || right >= doubleQueue.length) return -1; // window가 비었는데도 못 맞춤
            if(windowSum == target)
                return count;
            else if (windowSum > target){
                // left자리에 있던 값을 빼고 한 칸 이동
                windowSum -= doubleQueue[left];
                left++;
                count++;
            } else {
                // right 값 더함 그리고 이동!!!!
                count++;
                windowSum += doubleQueue[right];
                right++;
            }
        }
        // while 문 안에서 정답 찾고 리턴되지 않으면
        return -1;
    }
}
