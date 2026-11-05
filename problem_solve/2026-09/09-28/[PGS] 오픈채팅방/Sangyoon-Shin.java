import java.util.*;

class Solution {
    public String[] solution(String[] record) {

        Map<String, String> log = new HashMap<>(); // id, 이름

        int cnt = 0;

        // 일단 문자열 분리
        for (int i = 0; i < record.length; i++){
            String[] cur = record[i].split(" ");

            // 앞 글자가 leave면 두 개만 받기
            String cmd = cur[0];
            String id = cur[1];
            String name;

            if (!cmd.equals("Leave")){
                name = cur[2];

                // enter, change 모두 이름 갱신해주면 됨.
                log.put(id, name);
            }

            if (!cmd.equals("Change")){ // change에 대해서는 반환값이 없기때문에 정답 넣을 배열 크기에서 제외시켜야함.
                cnt++;
            }
        }


        String[] res = new String[cnt];

        int idx = 0;
        for (int i = 0; i < record.length; i++){
            String[] cur = record[i].split(" ");

            String cmd = cur[0];
            String id = cur[1];
            String name = log.get(id);

            if (cmd.equals("Enter")){
                res[idx++] = name + "님이 들어왔습니다.";
            } else if (cmd.equals("Leave")){
                res[idx++] = name + "님이 나갔습니다.";
            }
        }
        return res;
    }
}