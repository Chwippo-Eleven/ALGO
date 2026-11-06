import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Solution {

    final static int INF = Integer.MAX_VALUE;

    static int n;
    static int maxMove;

    static int maxNum;
    static int moveCount;

    static boolean hasDuplicate;

    static void search(char[] numArr, int move) {
        if (move >= maxMove) {
            int num = Integer.parseInt(String.valueOf(numArr));
            if (num > maxNum) {
                maxNum = num;
                moveCount = move;
            } else if (num == maxNum && move < moveCount) {
                moveCount = move;
            }
            return;
        }

        Set<Integer> idxSet = new HashSet<>();
        boolean isMaxNum = true;

        for (int i = 0; i < n - 1; i++) {
            idxSet.clear();
            int searchIndex = i;

            for (int j = i + 1; j < n; j++) {
                if (numArr[j] > numArr[searchIndex]) {
                    idxSet.clear();
                    idxSet.add(j);
                    searchIndex = j;
                    isMaxNum = false;
                } else if (numArr[j] == numArr[searchIndex]) {
                    hasDuplicate = true;
                    idxSet.add(j);
                }
            }

            if (numArr[i] < numArr[searchIndex]) {
                for (int idx : idxSet) {
                    search(swap(numArr, i, idx), move + 1);
                }
            }
        }

        if (isMaxNum) {
            int num = Integer.parseInt(String.valueOf(numArr));
            if (num > maxNum) {
                maxNum = num;
                moveCount = move;
            } else if (num == maxNum && move < moveCount) {
                moveCount = move;
            }
        }
    }

    static char[] swap(char[] arr, int i, int j) {
        char[] swapArr = arr.clone();
        char temp = swapArr[i];
        swapArr[i] = swapArr[j];
        swapArr[j] = temp;
        return swapArr;
    }

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();

        for (int testCase = 1; testCase <= T; testCase++) {

            String numStr = sc.next();
            n = numStr.length();
            maxMove = sc.nextInt();

            char[] numArr = numStr.toCharArray();

            maxNum = Integer.parseInt(numStr);
            moveCount = INF;
            hasDuplicate = false;

            search(numArr, 0);

            if (!hasDuplicate && (maxMove - moveCount) % 2 == 1) {
                numArr = String.valueOf(maxNum).toCharArray();
                numArr = swap(numArr, n - 1, n - 2);
                maxNum = Integer.parseInt(String.valueOf(numArr));
            }

            System.out.printf("#%d %d\n", testCase, maxNum);
        }
        sc.close();
    }
}
