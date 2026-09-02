import java.util.*;

class Solution {
    public int[] solution(String[] enroll, String[] referral, String[] seller, int[] amount) {

        
        Map<String, Integer> map = new HashMap<>();
        // 이름, index로 map을 배열처럼 생성
        for (int i = 0; i < enroll.length; i++) {
            map.put(enroll[i], i);
        }

        // index있게 배열로 하여서 이익 저장하는 배열
        int[] answer = new int[enroll.length];

        for (int i = 0; i < seller.length; i++) {
            int money = amount[i] * 100;
            String cur = seller[i];

            while (!cur.equals("-") && money > 0) { // 민호가 최상단이라서 여기전까지 돌리기 + 돈도 0보다 커야함(이거 없으면 시간초과)
                int give = money / 10;        // 추천인에게 줄 10%
                int keep = money - give;       // 내가 가질 금액

                answer[map.get(cur)] += keep;

                money = give;                  // 추천인에게 전달할 금액
                cur = referral[map.get(cur)];  // referral 내에서 i 번째에 있는 이름은 배열 enroll 내에서 i 번째에 있는 판매원을 조직에 참여시킨 사람의 이름 
            }
        }

        return answer;
    }
}
