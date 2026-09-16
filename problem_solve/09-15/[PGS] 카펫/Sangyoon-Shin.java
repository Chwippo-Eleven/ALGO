class Solution {
    public int[] solution(int brown, int yellow) {

        int total = brown + yellow; // 전체 카펫 크기

        for (int r = 3; r <= total; r++){
            if (total % r != 0){
                continue;
            }
            int c = total / r; // r * c = total 이니까, c = total / r;
            if ((r - 2) * (c - 2) == yellow){ // c * r 짜리 사각형에서 안쪽 영역은 각각 위, 아래 2개씩 줄어든 크기의 사각형
                int[] res = new int[] {c, r};
                return res;
            }
        }
        return new int[] {};
    }
}