import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Solution {
    public String[] solution(String[] orders, int[] course) {
        // 등록할 메뉴를 저장하는 리스트
        List<String> bestCourseList = new ArrayList<>();
        
        for (int k : course) {  // 코스의 구성 메뉴 수에 따라서
            
            // Map에 (코스, 주문 횟수) 저장
            Map<String, Integer> courseMap = new HashMap<>();
            // 최대 주문 횟수 별도로 관리
            int bestCount = 0;  
            
            for (String order : orders) {
                // 주문에서 k개 뽑는 조합 연산 결과 모두 저장
                List<String> courseList = menuComb(order, k);
                
                // Map에 기록하고 최대 주문 횟수도 자연스럽게 갱신
                for (String co : courseList) {
                    int value = courseMap.getOrDefault(co, 0) + 1;
                    courseMap.put(co, value);
                    
                    bestCount = Math.max(bestCount, value);
                }
            }
            
            // 2번 이상 출현한 조합이 없으면 등록하지 않는다.
            if (bestCount < 2) continue;
            
            for (String co : courseMap.keySet()) {
                // 가장 많이 출현한 조합들만 코스메뉴로 등록
                if (courseMap.get(co) == bestCount) {
                    bestCourseList.add(co);
                }
            }
        }
        // 리스트를 배열로 변환
        String[] bestCourseArray = bestCourseList.toArray(new String[0]);
        Arrays.sort(bestCourseArray);   // 사전 순으로 정렬하기
        
        return bestCourseArray;
    }
    
    // order에서 k개 메뉴를 뽑는 조합 결과 반환
    private List<String> menuComb(String order, int k) {
        
        List<String> res = new ArrayList<>();
        char[] pick = new char[k];
        
        comb(0, 0, order, k, pick, res);
        
        return res;
    }
    
    // 재귀 기반 조합 구현 함수
    private void comb(int depth, int start, String order, int k, char[] pick, List<String> ret) {
        
        if (depth == k) {
            char[] temp = pick.clone(); // 복사해서 정렬하지 않으면 pick이 꼬인다
            Arrays.sort(temp);          // 정렬을 해야 String 비교 만으로 같은 코스인지 구분 가능
            ret.add(String.valueOf(temp));
            return;
        }
        
        for (int i = start; i < order.length(); i++) {
            pick[depth] = order.charAt(i);
            comb(depth + 1, i + 1, order, k, pick, ret);
        }
    }
}
