class Solution {
    private List<String> answer;
    public List<String> letterCombinations(String digits) {
        
        answer=new ArrayList<>();
        char[][] arr=new char[10][];

        char ch='a';
        for(int i=2;i<=9;i++){
            if(i==7||i==9)arr[i]= new char[]{ch++, ch++, ch++, ch++};
            else arr[i]=new char[]{ch++, ch++, ch++};
        }
        dfs(digits, 0, "", arr);
        return answer;
    }

    private void dfs(String digits, int idx, String current, char[][] arr){
        if(idx==digits.length()){
            answer.add(current);
            return;
        }
        int num=digits.charAt(idx)-'0';
        for(int i=0;i<arr[num].length;i++){
            dfs(digits, idx+1, current+arr[num][i], arr);
        }
    }
}
