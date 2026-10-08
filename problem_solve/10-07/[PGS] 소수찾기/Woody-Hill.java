import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Solution {
    
    // 만들 수 있는 모든 숫자들을 저장하고 최댓값을 따로 기록
    private Set<Integer> numSet;
    private int maxNum;
    
    // 숫자를 만들기 위해 쓰이는 인스턴스 변수들
    private int len;
    private boolean[] visited;
    
    
    public int solution(String numbers) {
        
        makeNumSet(numbers);    // numSet & maxNum 확정
        
        // 체로 sqrt(maxNum)까지의 소수를 찾기
        int sqrtMaxNum = (int) Math.sqrt(maxNum);   // 최댓값의 제곱근
        
        boolean[] isPrime = sieve(sqrtMaxNum);
        
        // 소수들의 리스트 생성
        List<Integer> primes = new ArrayList<>();
        
        for (int i = 2; i <= sqrtMaxNum; i++) {
            if (isPrime[i]) primes.add(i);       
        }
        
        // numSet 원소들이 각각 소수인지 판독
        int primeCount = 0;
        
        FindPrime:
        for (int num : numSet) {
            
            // 체의 범위 안이면 O(1)에 판독 가능
            if (num <= sqrtMaxNum) {    
                if (isPrime[num]) {
                    primeCount += 1;
                }
                
            // 체의 범위 밖이면 찾아둔 소수들로 테스트
            } else {
                for (int p : primes) {
                    if (p * p > num) break;
                    
                    if (num % p == 0) {
                        continue FindPrime; // 나누어 떨어지면 소수 아님
                    }
                }
                primeCount += 1;
            }
        }
        
        return primeCount;
    }
    
    // 에라토스테네스의 체
    private boolean[] sieve(int num) {
        boolean[] isPrime = new boolean[num + 1];
        
        if (num < 2) return isPrime;
        
        Arrays.fill(isPrime, true);
        
        isPrime[0] = false;
        isPrime[1] = false;
        
        for (int p = 2; p * p <= num; p++) {
            if (!isPrime[p]) continue;
            
            for (int multiple = p * p; multiple <= num; multiple += p) {
                isPrime[multiple] = false;
            }
        }
        
        return isPrime;
    }
    
    // DFS로 숫자 생성하는 함수
    private void makeNumSet(String numbers) {
        
        // 인스턴스 변수 초기화
        numSet = new HashSet<>();
        maxNum = 0;
        
        len = numbers.length();
        visited = new boolean[len];
        
        // 실제로 숫자를 생성해 주는 함수를 호출
        make(0, 0, numbers);
        
        // 끝나면 numSet에 만들 수 있는 모든 숫자가 들어간다
    }
    
    // 실제 재귀 호출 수행 함수
    private void make(int depth, int num, String numbers) {
        
        // 만든 숫자를 집합에 넣고 최댓값도 갱신
        if (depth > 0) {
            numSet.add(num);        
            maxNum = Math.max(maxNum, num);
        }
        if (depth == len) return;
        
        // 다음 선택을 수행
        for (int i = 0; i < len; i++) {
            if (visited[i]) continue;
            
            visited[i] = true;
            
            int digit = numbers.charAt(i) - '0';
            make(depth + 1, num * 10 + digit, numbers);
            
            visited[i] = false;
        }
    }
}
