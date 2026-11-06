class Solution {
    
    char[] letter;
    List<String> letterComb;

    char[][] keypad = {
        {},
        {},
        {'a', 'b', 'c'},
        {'d', 'e', 'f'},
        {'g', 'h', 'i'},
        {'j', 'k', 'l'},
        {'m', 'n', 'o'},
        {'p', 'q', 'r', 's'},
        {'t', 'u', 'v'},
        {'w', 'x', 'y', 'z'},
    };
    
    public List<String> letterCombinations(String digits) {
        int n = digits.length();

        letter = new char[n];
        letterComb = new ArrayList<>();

        combination(0, n, digits);

        return letterComb;
    }

    private void combination(int index, int n, String digits) {

        if (index == n) {
            letterComb.add(String.valueOf(letter));
            return;
        }

        int digit = digits.charAt(index) - '0';

        for (char ch : keypad[digit]) {
            letter[index] = ch;
            combination(index + 1, n, digits);
        }
    }
}
