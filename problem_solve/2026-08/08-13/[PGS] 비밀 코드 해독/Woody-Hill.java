import java.util.ArrayList;
import java.util.List;

class Solution {
    public int solution(int n, int[][] q, int[] ans) {
        
        // 1 ~ n까지 들어있는 배열 생성
        int[] numbers = new int[n];
        for (int x = 0; x < n; x++) {
            numbers[x] = x + 1;
        }
        
        // 가능한 모든 암호 조합 생성
        List<int[]> passCodes = combination(numbers, 5);
        
        for (int i = 0; i < q.length; i++) {
            int[] query = q[i];
            int answer = ans[i];
            
            // 암호 분석 결과와 맞지 않는 암호 삭제
            passCodes.removeIf(code -> countSame(code, query) != answer);
        }
        
        return passCodes.size();
    }
    
    // 두 정수 배열에 같은 원소가 몇 개인지 구하는 함수
    private int countSame(int[] arr1, int[] arr2) {
        int count = 0;
        for (int n1 : arr1) {
            for (int n2 : arr2) {
                if (n1 == n2) {
                    count++;
                    break;
                }
            }
        }
        return count;
    }
    
    // 배열에서 k개를 선택하는 모든 경우를 리스트 형태로 반환
    private List<int[]> combination(int[] arr, int k) {
        
        List<int[]> result = new ArrayList<>();
        int[] pick = new int[k];
        comb(arr, 0, 0, pick, result);
        
        return result;
    }
    
    // combination의 내부에서 동작하는 함수. 원소를 k개만큼 고르고 결과 배열에 넣는다.
    private void comb(int[] arr, int start, int depth, int[] pick, List<int[]> result) {
        if (depth == pick.length) {
            result.add(pick.clone());
            return;
        }
        
        for (int i = start; i < arr.length; i++) {
            pick[depth] = arr[i];
            comb(arr, i + 1, depth + 1, pick, result);
        }
    }
}
