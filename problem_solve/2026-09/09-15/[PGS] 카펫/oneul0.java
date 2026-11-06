class Solution {
    public int[] solution(int brown, int yellow) {
        int[] answer = new int[2];

        int area = brown+yellow;

        //노랑이 존재하기 위한 최소 높이와 너비
        for(int i = 3; i<=area; i++){
            int c = i;
            int r = area/c;

            if(r < 3) continue;

            if(r >= c){
                if((r-2)*(c-2) == yellow){
                    answer[0] = r;
                    answer[1] = c;
                    break;
                }
            }
        }

        return answer;
    }
}