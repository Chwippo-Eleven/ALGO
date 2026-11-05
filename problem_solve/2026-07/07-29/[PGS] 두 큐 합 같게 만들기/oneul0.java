class Solution {
    /**
        투포인터와 슬라이딩 윈도우로 푸는 방법
        전체합 = 큐1의 합 + 큐2의 합 일때,
        큐1의 합 == 전체합/2 이 되도록 만들면 됨
        
        이때, 윈도우는 큐1의 합이고
        윈도우의 합(window value < target || window value > target) 따라
        현재 합 > target → 왼쪽 포인터 이동
        현재 합 < target → 오른쪽 포인터 이동
        
        업다운 게임이랑 비슷함
        현재 합을 올려야 함 → queue2의 맨 앞 원소를 queue1으로 이동
        현재 합을 내려야 함 → queue1의 맨 앞 원소를 queue2로 이동
        
    */
    public int solution(int[] queue1, int[] queue2) {
        int len = queue1.length+queue2.length;
        int[] totalArr = new int[len];
        
        long totalSum = 0;
        long q1Sum = 0;
        
        int idx = 0;
        for(int i = 0; i<queue1.length; i++){
            totalArr[idx++] = queue1[i];
            totalSum += queue1[i];
            q1Sum += queue1[i];
        }
        
        for(int i = 0; i<queue2.length; i++){
            totalArr[idx++] = queue2[i];
            totalSum += queue2[i];
        }
        
        if(totalSum%2 == 1) return -1;
        
        long targetSum = totalSum >>>1;
        
        //투포인터
        int count = 0;
        int i1 = 0, i2= queue1.length;
        while(i1<i2 && i1<len){
            if(q1Sum > targetSum){
                q1Sum -= totalArr[i1++];
                count++;
            }
            else if(q1Sum < targetSum){
                if(i2 >= len) return -1;
                q1Sum += totalArr[i2++];
                count++;
            }
            else{
                return count;
            }
        }
        
        //슬라이딩 윈도우로도 될 거 같은데
        
        return -1;
    }
}

 