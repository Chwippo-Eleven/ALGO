import java.util.*;
import java.io.FileInputStream;
class Solution
{	
    
    static int max = 0;
    // [카운트 횟수][숫자] : 로 이미 했는지 체크하여 재방문 안하도록함
    static boolean[][] visited;
	public static void main(String args[]) throws Exception
	{
		Scanner sc = new Scanner(System.in);
		int T;
		T=sc.nextInt();
		for(int test_case = 1; test_case <= T; test_case++)
		{
			int num = sc.nextInt();
            int x = sc.nextInt();
            int tmpNum = num; // 숫자 안변하게 복사
            int tmpLen = 0;
            int[] numArr = new int[6];//최대 6자리니까 박고
            while (tmpNum>0){ // 뒷자리부터 앞으로
            	numArr[tmpLen] = tmpNum%10;
                tmpNum = tmpNum/10;
                tmpLen++;//길이체크
            }
            max = 0;
            visited = new boolean[x + 1][1000000];
            TTT(0, x, tmpLen, numArr);
            System.out.printf("#%d %d\n", test_case, max);
		}
	}
    
    public static void TTT(int count, int x, int tmpLen, int[] num){
        // 현재 숫자를 만든다
        int current = 0;

        for (int j = tmpLen - 1; j >= 0; j--) {
            current = current * 10 + num[j];
        }

        // 같은 교환 횟수에서 같은 숫자를 이미 확인했다면 종료
        if (visited[count][current]) {
            return;
        }

        //[카운트][해당숫자] 방문처리
        visited[count][current] = true;

        // 교환 횟수를 모두 사용
        if (count == x) {
            max = Math.max(max, current);
            return;
        }
        
        
        for (int i=0; i<tmpLen - 1; i++){
            for (int ii=i+1; ii<tmpLen; ii++){
            	int swap =num[i];//바꾸기
                num[i] = num[ii];
                num[ii] = swap;
                TTT(count+1, x, tmpLen, num); // 바꾸고 다시 호출
                swap =num[i]; //바꾼거 취소
                num[i] = num[ii];
                num[ii] = swap;
            }
             
    	}
        return ;
	}
}
