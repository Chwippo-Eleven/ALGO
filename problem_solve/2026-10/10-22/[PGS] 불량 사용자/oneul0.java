import java.util.*;
class Solution {
    public int solution(String[] user_id, String[] banned_id) {
        List<Integer> masked_idx = new ArrayList<>();
        for(String id : banned_id){
            int mask = 0;
            for(int i = 0; i < id.length(); i++){
                if(id.charAt(i) == '*'){
                    mask |= (1 << i);
                }
            }
            masked_idx.add(mask);
        }

        Set<Integer> states = new HashSet<>();
        states.add(0);

        for(int j = 0; j < banned_id.length; j++){
            Set<Integer> next = new HashSet<>();

            for(int state : states){
                for(int i = 0; i < user_id.length; i++){
                    //이미 사용된 아이디 건너뛰기
                    if((state & (1 << i)) != 0) continue;

                    //길이가 다르면 매칭 불가
                    if(user_id[i].length() != banned_id[j].length())
                        continue;

                    StringBuilder cur = new StringBuilder(user_id[i]);

                    //* 위치에 마스킹
                    for(int m = 0; m < cur.length(); m++){
                        if((masked_idx.get(j) & (1 << m)) != 0){
                            cur.setCharAt(m, '*');
                        }
                    }

                    //새 조합 추가
                    if(cur.toString().equals(banned_id[j])){
                        next.add(state | (1 << i));
                    }
                }
            }

            states = next;
        }

        return states.size();
    }
}