import java.util.Scanner;
import java.io.FileInputStream;

class Solution {
    
    static int maxValue;
    static int length;
    static int k;
    
	public static void main(String args[]) throws Exception {
		Scanner sc = new Scanner(System.in);
		int T;
		T=sc.nextInt();

		for(int test_case = 1; test_case <= T; test_case++) {
            int number = sc.nextInt();
            k = sc.nextInt();
            
            char[] arr = String.valueOf(number).toCharArray();
            length = arr.length;
            maxValue = 0;
            // 숫자 최대 6자리, 교환 횟수 최대 10
            boolean[][] visited = new boolean[1000000][11];
            
            dfs(arr, 0, visited);
            
            System.out.println("#" + test_case + " " + maxValue);
		}
	}
    
    public static void dfs(char[] number, int count, boolean[][] visited) {
        // k 번 바꾸기를 완료했다면
        if (count == k) {
            maxValue = Math.max(Integer.parseInt(new String(number)), maxValue);
            return;
        }
        // 같은 수를 같은 바꾸기를 통해 만들어냈다면 가지치기
        int currentNumber = Integer.parseInt(new String(number));
        if (visited[currentNumber][count])
            return;
        // 방문체크
        visited[currentNumber][count] = true;
        
        for (int i = 0; i < length-1; i++){
        	for (int j = i+1; j < length; j++){
            	if (i==j) continue;
                // 자리 바꾸기
                char temp = number[i];
                number[i] = number[j];
                number[j] = temp;
                
                dfs(number, count+1, visited);
                // 원상복구
                temp = number[i];
                number[i] = number[j];
                number[j] = temp;
            }
        }
    }
}
