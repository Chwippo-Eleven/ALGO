class Solution {
    public int solution(String s) {
        
        int n = s.length();
        int m = n / 2;
        
        int bestCompressLength = n;
        
        for (int cut = 1; cut <= m; cut++) {
            StringBuilder sb = new StringBuilder();
            
            String currToken = "";
            int count = 0;
            
            // 1. Cut 단위만큼 잘라보며 토큰 확인
            for (int i = 0; i + cut <= n; i += cut) {
                String nextToken = s.substring(i, i + cut);
                
                if (nextToken.equals(currToken)) {
                    count++;
                    continue;
                }
                
                appendToken(sb, currToken, count);
                
                currToken = nextToken;
                count = 1;
            }
            
            // 2. 나머지 토큰들 붙이기
            appendToken(sb, currToken, count);
            
            int rem = n % cut;
            
            if (rem > 0) {
                sb.append(s.substring(n - rem, n));    
            }
            
            // 3. 최적 결과 갱신
            bestCompressLength = Math.min(bestCompressLength, sb.length());
        }
        
        return bestCompressLength;
    }
    
    private void appendToken(StringBuilder sb, String token, int count) {
        if (count > 1) {
            sb.append(count).append(token);
        } else if (count > 0) {
            sb.append(token);
        }
    }
}
