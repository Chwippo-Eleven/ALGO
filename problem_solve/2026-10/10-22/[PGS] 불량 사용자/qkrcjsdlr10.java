import java.util.*;

class Solution {
    boolean[] vis;
    int answer = 0;
    Set<ArrayList<String>> st = new HashSet<>();


    public void dfs(int depth, ArrayList<String> temp,  int n, int m, String[] user_id, String[] banned_id){
        if(depth == m){
            ArrayList<String> arr = new ArrayList<>(temp);
            arr.sort(null);

            if(!st.contains(arr)){
                st.add(arr);
                answer++;
            }
            return;
        }

        for(int i = 0; i < n; i++){
            if(vis[i]) continue;
            if(!matchId(user_id[i], banned_id[depth])) continue;
            vis[i] = true;
            temp.add(user_id[i]);
            dfs(depth + 1, temp, n, m, user_id, banned_id);
            vis[i] = false;
            temp.remove(temp.size() - 1);
        }
    }

    public boolean matchId(String userId, String bannedId){
        if(userId.length() != bannedId.length()) return false;

        for(int i = 0; i < userId.length(); i++){
            if(bannedId.charAt(i) == '*') continue;
            if(bannedId.charAt(i) != userId.charAt(i)) return false;
        }

        return true;
    }

    public int solution(String[] user_id, String[] banned_id) {


        vis = new boolean[user_id.length];
        dfs(0, new ArrayList<>(), user_id.length, banned_id.length, user_id, banned_id);
        return answer;
    }
}