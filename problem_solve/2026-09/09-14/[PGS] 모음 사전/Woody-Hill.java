class Solution {
    public int solution(String word) {
        
        char[] letters = {'A', 'E', 'I', 'O', 'U'};
        
        int n = letters.length;
        
        int[] offset = new int[n + 1];
        
        for (int i = 4; i >= 0; i--) {
            offset[i] = 1 + 5 * offset[i + 1];
        }
        
        int wordOrder = 0;
        
        OuterLoop:
        for (int idx = 0; idx < word.length(); idx++) {
            char ch = word.charAt(idx);
            
            for (int ord = 0; ord < n; ord++) {
                if (letters[ord] == ch) {
                    wordOrder += 1 + ord * offset[idx];
                    continue OuterLoop;
                }
            }
        }
        
        return wordOrder;
    }
}
