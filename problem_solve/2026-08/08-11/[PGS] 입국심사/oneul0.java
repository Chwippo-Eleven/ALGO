// while문의 조건을 l<r로 둘 때와 l<=r로 둘 때의 차이
// l<r로 두면 r이 최소값이 될 수 있음
// l<=r로 두면 r이 최소값이 될 수 없음
// 따라서 l<r로 둔다면 r 자체가 최소값이 될 수 있으므로 return r을 해주면 됨
// 하지만 l<=r로 두면 r이 최소값이 될 수 없으므로 return l을 해주어야 함
// lower bound 풀이

// case1 l<r
class Solution {
    public long solution(int n, int[] times) {
        return bs(times, n);
    }
    
    public long bs(int[] times, int n){
        long l = 0;
        long r = 1_000_000_000L*100_000;
        while(l<r){
            long cnt = 0;
            long mid = (l+r)>>>1;
            
            for(int time : times){
                cnt += mid/time;
                if(cnt>=n) break;
            }
            if(cnt >= n){
                r = mid;
            }
            else {
                l = mid+1;
            }
        }
        return r;
    }
}

// case 2 l<=r
class Solution {
    public long solution(int n, int[] times) {
        return bs(times, n);
    }
    
    public long bs(int[] times, int n){
        long l = 0;
        long r = 1_000_000_000L*100_000;
        while(l<=r){
            long cnt = 0;
            long mid = (l+r)>>>1;
            
            for(int time : times){
                cnt += mid/time;
                if(cnt>=n) break;
            }
            if(cnt >= n){
                r = mid-1;
            }
            else {
                l = mid+1;
            }
        }
        return l;
    }
}

//answer++ 하며 특정 시점마다 나누어 떨어지는 times[i]를 찾았는데
//시간복잡도가 생각보다 많이 나오고 원하는대로 동작하지 않음
//되냐 안되냐 이진문제로 바꾸기 위해 특정 시점을 찾고
//그 시점에 모든 n을 처리할 수 있는지를 구하여 min time을 구하기