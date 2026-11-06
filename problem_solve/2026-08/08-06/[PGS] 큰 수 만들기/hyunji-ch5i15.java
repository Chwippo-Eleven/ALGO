import java.util.*;

class Solution {
    public String solution(String number, int k) { // number = "1231234", k=3
        StringBuilder stack = new StringBuilder(); // 정말 스택처럼 사용할 용도
        // 숫자의 위치를 바꿀 수 없으므로 그리디가 더 수월해짐

        // ex) "1231234", k=3
        for (char c : number.toCharArray()) { // number를 왼쪽부터 한 글자씩 순회 (O(n))
            // ★ 그리디 핵심: 지금 숫자가 이전에 쌓아둔 숫자보다 크면
            while (stack.length() > 0 // 스택이 비어있지 않고
                    && k > 0 // 아직 지울 수 있는 여유(k)가 남아있고
                    && stack.charAt(stack.length() - 1) < c) // stack 최상단값 < c

            {
                stack.deleteCharAt(stack.length() - 1); // stack 최상단값 삭제
                k--; // 제거횟수 감소
            }
            stack.append(c);
        }
        // 끝까지 순회했는데도 k가 남아있으면 뒤에서부터 잘라냄
        stack.setLength(stack.length() - k);

        return stack.toString();
    }
}