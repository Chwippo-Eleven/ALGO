class Solution {
    public String solution(String number, int k) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < number.length(); i++) {
            char c = number.charAt(i);

            while (sb.length() > 0 &&
                   k > 0 &&
                   sb.charAt(sb.length() - 1) < c) {
                sb.deleteCharAt(sb.length() - 1);
                k--;
            }

            sb.append(c);
        }

        // 아직 제거해야 할 숫자가 남았다면 뒤에서 제거
        if (k > 0) {
            sb.setLength(sb.length() - k);
        }

        return sb.toString();
    }
}