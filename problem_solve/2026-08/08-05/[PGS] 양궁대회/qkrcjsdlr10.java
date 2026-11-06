import java.util.*;

class Solution {
    static int[] res;
    static int mx = 0;
    
    public static int curScore(int[] arr1, int[] arr2){
        int aScore = 0;
        int bScore = 0;
        for(int i = 0; i < arr1.length; i++){
            int score = 10 - i;
            if(arr1[i] == 0 && arr2[i] == 0) continue;
            if(arr1[i] > arr2[i]){
                aScore += score;
            }else{
                bScore += score;
            }
        }
        if(aScore > bScore){
            return aScore - bScore;
        }else{
            return -1;
        }
    }
    
    public static void dfs(int depth, int cnt, int n, int[] info, int[] temp){
        if(depth == 11){
            temp[10] = n - cnt;    
            
            int score = curScore(temp, info);
            if(score == -1) return;
            if(score > mx){
                res = temp.clone();
                mx = score;
            }else if(score == mx){
                for(int i = 10; i >= 0; i--){
                    if(temp[i] > res[i]){
                        res = temp.clone();
                        break;
                    } else if(temp[i] < res[i]){
                        break;
                    }
                }
            }
            temp[10] = 0;
            return;
        }
        
        dfs(depth + 1, cnt, n, info, temp);
        
        if(cnt + info[depth] + 1 <= n){
            temp[depth] = info[depth] + 1;
            dfs(depth + 1, cnt + info[depth] + 1, n, info, temp);
            temp[depth] = 0;
        }
    }
    
    public int[] solution(int n, int[] info) {
        int[] answer = new int[info.length];
        int[] temp = new int[11];
        res = new int[11];
        mx = -1;
        dfs(0, 0, n, info, temp);
        
        for(int a : res){
            System.out.print(a + " ");
        }
        if (mx == -1) {
            return new int[] { -1 };
        }
        
        return res;
    }
}