import java.util.HashMap;
import java.util.Map;

class Solution {
    
    // 해시맵 매번 쓰기 싫어서 Aliasing 느낌으로 해 봤습니다...
    class MultiSet extends HashMap<String, Integer> {
        public MultiSet() {
            super();
        }
        
        public MultiSet(HashMap<String, Integer> map) {
            super(map);
        }
        
        // merge 이용해서 합집합 생성
        public MultiSet union(MultiSet ms) {
            MultiSet union = new MultiSet(this);
            
            ms.forEach((key, value) -> union.merge(key, value, Math::max));
            
            return union;
        }
        
        // 직접 순회하며 교집합 생성
        public MultiSet intersection(MultiSet ms) {
            MultiSet intersection = new MultiSet();
            
            for (Map.Entry<String, Integer> entry : this.entrySet()) {
                String key = entry.getKey();

                if (ms.containsKey(key)) {
                    int value1 = entry.getValue();
                    int value2 = ms.get(key);

                    intersection.put(key, Math.min(value1, value2));
                }
            }

            return intersection;
        }
        
        // 다중집합 크기 반환
        public int getSize() {
            int size = 0;
            for (int value : this.values()) {
                size += value;
            }
            return size;
        }
    }
    
    public int solution(String str1, String str2) {
        
        // MultiSet에는 ("토큰" : 개수) 쌍을 넣는다
        MultiSet tokenSet1 = createTokenSet(str1);
        MultiSet tokenSet2 = createTokenSet(str2);
        
        // 토큰 다중집합의 합집합과 교집합 구하기
        MultiSet union        = tokenSet1.union(tokenSet2);
        MultiSet intersection = tokenSet1.intersection(tokenSet2);
        
        // 합집합과 교집합의 원소 개수 구하기
        int unionSize = union.getSize();
        int interSize = intersection.getSize();
        
        // Jaccard Similarity 구하기
        double similarity = 0;
        
        if (unionSize == 0) {
            similarity = 1; 
        }
        else {
            similarity = (double) interSize / unionSize;
        }
        
        return (int) (similarity * 65536);
    }
    
    // 토큰 다중집합 생성
    private MultiSet createTokenSet(String str) {
        
        MultiSet tokens = new MultiSet();
        
        str = str.toLowerCase();
        
        for (int idx = 0; idx < str.length() - 1; idx++) {
            char c1 = str.charAt(idx);
            char c2 = str.charAt(idx + 1);
            
            if (!isLetter(c1) || !isLetter(c2)) { continue; }
            
            String token = String.valueOf(new char[] {c1, c2});
            tokens.put(token, tokens.getOrDefault(token, 0) + 1);
        }
        
        return tokens;
    }
    
    // 알파벳 검사하는 유틸 함수
    private boolean isLetter(char c) {
        return ('A' <= c && c <= 'Z') || ('a' <= c && c <= 'z');
    }
}
