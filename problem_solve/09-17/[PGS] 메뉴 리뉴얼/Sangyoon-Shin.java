import java.util.*;

class Solution {
    static Map<String, Integer> map; // 현재 보는 메뉴에 대한 모든 조합

    public String[] solution(String[] orders, int[] course) {

        // 풀이 전 생각
        // orders를 순회하면서, 2개 ~ orders.length개 만큼 조합으로 뽑기
        // 만들어진 조합을 map의 키로 쓰고, 다른 사람에게서도 그 조합이 만들어질때마다 cnt++
        // map 순회하면서 string 배열에 넣고 정렬

        map = new HashMap<>();

        for (String cur : orders){
            // ab ba는 같은 거로 취급해야함
            char[] tmp = cur.toCharArray();
            Arrays.sort(tmp);

            cur = new String(tmp);
            dfs(cur, 0, ""); // 현재 주문에서 모든 조합 구하기
        }

        ArrayList<String> res = new ArrayList<>();

        for (int size : course){

            // 일단 해당되는 사이즈에서 주문횟수 최대값 찾기
            int max = 0;

            for (String s : map.keySet()){
                if (s.length() == size && map.get(s) >= 2){
                    max = Math.max(max, map.get(s));
                }
            }

            // 최대값인 애들 정답 리스트에 넣어주기
            for (String s : map.keySet()){
                if (s.length() == size && map.get(s) == max){
                    res.add(s);
                }
            }
        }

        Collections.sort(res);

        String[] result = new String[res.size()];
        for (int i = 0; i < res.size(); i++){
            result[i] = res.get(i);
        }
        return result;

    }
    public void dfs(String s, int idx, String cur){

        if (idx == s.length()){
            return;
        }

        for (int i = idx; i < s.length(); i++){
            String copy = new String(cur);
            cur += String.valueOf(s.charAt(i));
            if (cur.length() >= 2){
                map.put(cur, map.getOrDefault(cur, 0) + 1);
            }
            dfs(s, i + 1, cur);
            cur = copy;
        }
    }
}