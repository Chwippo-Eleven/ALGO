import java.util.*;

class Solution {
    
    static List<String> splitString(String str){
        str = str.toUpperCase();
        List<String> arr = new ArrayList<>();
        
        for(int i = 0; i < str.length() - 1; i++){
            String temp = "";
            for(int j = i; j < i + 2; j++){
                if(str.charAt(j) <= 'Z' && str.charAt(j) >= 'A'){
                    temp += str.charAt(j);
                }
            }
            if(temp.length() == 2){
                arr.add(temp);
            }
        }
        return arr;
    }
    
    public int solution(String str1, String str2) {
        double answer = 0;
        
        List<String> arr1 = splitString(str1);
        List<String> arr2 = splitString(str2);
        List<String> arr3 = splitString(str2);
        
        boolean[] vis = new boolean[arr2.size()];
        
        int cnt = 0;
        
        for(String key : arr1){
            if(!arr2.contains(key)){
                arr3.add(key);
            }else{
                boolean check = false;
                for(int i = 0; i < arr2.size(); i++){
                    if(key.equals(arr2.get(i))){
                        if(vis[i] == false){
                            vis[i] = true;
                            check = true;
                            break;
                        }
                    }
                }
                if(check == false){
                    arr3.add(key);
                }
            }
        }
        
        vis = new boolean[arr2.size()];
        
        for(String key : arr1){
            for(int i = 0; i < arr2.size(); i++){
                if(key.equals(arr2.get(i)) && vis[i] == false){
                    vis[i] = true;
                    cnt++;
                    break;
                }
            }
        }
        
        double temp = 0;
        
        if(cnt == arr3.size()){
            temp = 1;
        }else{
            temp = (double) cnt / arr3.size();
        }
        
        if(temp == 1){
            answer = 65536;
        }else{
            answer = temp * 65536;
        }
        
        return (int) answer;
    }
}