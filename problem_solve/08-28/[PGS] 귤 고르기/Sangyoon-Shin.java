import java.util.*;

class Solution {
    public int solution(int k, int[] tangerine) {

        HashMap<Integer, Integer> map = new HashMap<>();

        // 종류를 최소로 만들고 싶은거면.. 개수가 많은 애들을 우선으로 선택하면 되는거 아닌가?
        // (크기, 개수)로 담아주고
        for (int i = 0; i < tangerine.length; i++){
            map.put(tangerine[i], map.getOrDefault(tangerine[i], 0) + 1);
        }

        // 과일 개수만 뽑아서 내림차순으로 정렬
        ArrayList<Integer> cnt = new ArrayList<>();
        for (Integer val : map.values()){
            cnt.add(val);
        }
        Collections.sort(cnt, Collections.reverseOrder());

        int res = 0;
        int sum = 0;
        for (int i = 0; i < cnt.size(); i++){
            if (sum + cnt.get(i) >= k){ // 지금까지 합에서 현재의 개수를 더한게 k랑 같거나 커도 종류 1개는 추가된 것
                res++;
                break;
            } else {
                sum += cnt.get(i);
                res++;
            }
        }
        return res;
    }
}