import java.util.HashSet;
import java.util.Set;

class Solution {
    
    String[] user_id, banned_id;
    int n, m;
    
    Set<Integer> maskSet;
    
    public int solution(String[] user_id, String[] banned_id) {
        
        // == 인스턴스 변수 초기화 == //
        this.user_id = user_id;
        this.banned_id = banned_id;
        
        this.n = user_id.length;
        this.m = banned_id.length;
        
        this.maskSet = new HashSet<>();
        
        // 재귀 함수 호출
        matchUser(0, 0);
        
        // 집합의 크기 == 경우의 수
        return maskSet.size();
    }
    
    private void matchUser(int depth, int mask) {
        if (depth == m) {
            maskSet.add(mask);
            return;
        }
        
        String banned = banned_id[depth];
        
        for (int i = 0; i < n; i++) {
            if ((mask & (1 << i)) != 0) continue;
            
            if (possibleMatch(user_id[i], banned)) {
                matchUser(depth + 1, setBit(mask, i));
            }
        }
    }
    
    private boolean possibleMatch(String user, String banned) {
        if (user.length() != banned.length()) {
            return false;
        }
        
        for (int i = 0; i < user.length(); i++) {
            char u = user.charAt(i);
            char b = banned.charAt(i);
            
            if (b == '*') continue;
            
            if (b != u) {
                return false;
            }
        }
        
        return true;
    }
    
    private int setBit(int mask, int index) {
        return mask | (1 << index);
    }
}
