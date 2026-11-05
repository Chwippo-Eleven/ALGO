class Solution {
    String digits;
    List<String> result = new ArrayList<>();
    String[] strings = {" ", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
    public List<String> letterCombinations(String digits) {
        this.digits = digits;
        comb(0, new StringBuilder());
        return result;
    }
    public void comb(int depth, StringBuilder str){
        if(depth >= digits.length()) {
            result.add(str.toString());
            return;
        }
        int cur = digits.charAt(depth)-'0';
        for(char c : strings[cur].toCharArray()){
            str.append(c);
            comb(depth+1, str);
            str.deleteCharAt(str.length()-1);
        }
    }
}