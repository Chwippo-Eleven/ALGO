import java.util.*;

class Solution {
    public String solution(String play_time, String adv_time, String[] logs) {
        int play=toSec(play_time);
        int adv=toSec(adv_time);
        
        long[]arr=new long[play+1];
        
        for(String log: logs){
            String[] split=log.split("-");
            int start=toSec(split[0]);
            int end=toSec(split[1]);
            
            arr[start]++;
            arr[end]--;
        }
        
        for(int i=1;i<arr.length;i++){
            arr[i]=arr[i-1]+arr[i];
        }
        
        for(int i=1;i<arr.length;i++){
            arr[i]=arr[i-1]+arr[i];
        }
        
        int start=0;
        long max=arr[adv-1];
        
        for(int i=0;i<=play-adv;i++){
            if(max<arr[i+adv]-arr[i]){
                max=arr[i+adv]-arr[i];
                start=i+1;
            }
        }
        
        
        return toStr(start);
    }
    
    private int toSec(String time){ 
        String[] tmp= time.split(":");
        
        return Integer.parseInt(tmp[0])*60*60
            +Integer.parseInt(tmp[1])*60
            +Integer.parseInt(tmp[2]);
        
    }
    
    private String toStr(int s){
        String sec=String.valueOf(s%60);
        if(sec.length()==1) sec="0"+sec;
        s/=60;
        
        String minute=String.valueOf(s%60);
        if(minute.length()==1) minute="0"+minute;
        s/=60;
        
        String hour=String.valueOf(s);
        if(hour.length()==1) hour="0"+hour;
        
        return hour+":"+minute+":"+sec;
    }
}
