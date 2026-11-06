import java.util.ArrayDeque;

class Solution {
    public String solution(String number, int k) {
        
        int n = number.length();
        
        ArrayDeque<Character> deque = new ArrayDeque<>();
        
        int removeCount = 0;    // k가 될 때까지 숫자 지우기
        int index = 0;          // number String 조회용
        
        // 그리디하게 숫자 지우기
        while (removeCount < k) {
            // 새 원소를 추가할 수 있는 경우
            if (index < n) {
                // Wrapper Class 이용해 null 처리
                Character currNum = number.charAt(index);
                Character prevNum = deque.peek();
                
                // 이전 원소가 더 작으면 지울 만큼 지운다
                if (prevNum != null && prevNum < currNum) {
                    deque.pop();
                    removeCount += 1;
                    continue;
                }
                // 현재 원소 추가하고 넘어가기
                deque.push(currNum);
                index += 1;
            } 
            // 추가할 원소가 없는 경우
            else {
                deque.pop();
                removeCount += 1;
            }
        }
        // 남은 숫자 push
        while (index < n) {
            deque.push(number.charAt(index++));
        }
        
        StringBuilder sb = new StringBuilder();
        
        // StringBuilder 이용해 데크 뒤에서부터 추출
        while (!deque.isEmpty()) {
            sb.append(deque.pollLast());
        }
        
        return sb.toString();
    }
}
