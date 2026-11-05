import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

class Solution {
    
    static class Cart extends HashMap<String, Integer> {
        // 장바구니에서 특정 상품 1개 추가
        private void addItem(String item) {
            put(item, getOrDefault(item, 0) + 1);
        }
        // 장바구니에서 특정 상품 1개 제거
        private void removeItem(String item) {
            Integer amount = get(item);
            if (amount == null) return;
            else if (amount == 1) remove(item);
            else if (amount >= 2) put(item, amount - 1);
        }
    }
    
    public int[] solution(String[] gems) {
        
        int n = gems.length;
        
        Set<String> gemSet = new HashSet<>();
        
        // Set 이용해 보석 종류를 저장
        for (String gem : gems) {
            gemSet.add(gem);
        }
        int gemKind = gemSet.size();    // 보석 종류의 수
        
        Cart shoppingCart = new Cart(); // 직접 구현한 장바구니
        
        int[] range = new int[2];
        int minRangeLength = Integer.MAX_VALUE;
        
        // 연산은 0-index로, 출력은 1-index로 하는 것에 유의할 것!
        int start = 0;
        int end   = 0;
        
        while (end < n) {
            // 1. 모든 보석을 담을 때까지 end를 움직여 범위를 확장한다
            while (shoppingCart.size() < gemKind && end < n) {
                shoppingCart.addItem(gems[end++]);
            }
            // 2. 조건이 깨질 때까지 start를 움직여 범위를 축소한다
            while (shoppingCart.size() == gemKind) {
                shoppingCart.removeItem(gems[start++]);
            }
            // 1번 반복문: (end - 1)번 보석까지 담아야 한다
            // 2번 반복문: (start - 1)번이 유효 구간의 시작점이다
            
            // 결과 갱신. [start - 1, end - 1]이 [start, end] 형태로 기록
            int rangeLength = end - start + 1;
            if (rangeLength < minRangeLength) {
                range[0] = start;
                range[1] = end;
                minRangeLength = rangeLength;
            }
        }
        
        return range;
    }
}
