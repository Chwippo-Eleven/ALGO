import java.util.*;

class Solution {
    int n;
    public class Path{
        ArrayList<String> path;
        boolean[] vis;

        Path(ArrayList<String> path, boolean[] vis){
            this.path = path;
            this.vis = vis;
        }

        Path(Path p) {
            this.path = new ArrayList<>(p.path);
            this.vis = p.vis.clone();
        }
    }

    public String[] solution(String[][] tickets) {
        String[] answer = {};

        n = tickets.length;

        ArrayDeque<Path> q = new ArrayDeque<>();
        ArrayList<String> path = new ArrayList<>();
        path.add("ICN");
        q.add(new Path(path, new boolean[n]));

        while(!q.isEmpty()){
            Path cur = q.pollFirst();
            boolean check = false;
            for(boolean x : cur.vis){
                if(!x){
                    check = true;
                }
            }
            if(!check){
                String[] temp = cur.path.toArray(new String[0]);

                if(answer.length == 0){
                    answer = temp;
                }else{
                    for(int i = 0; i < temp.length; i++){
                        int cmp = temp[i].compareTo(answer[i]);

                        if(cmp < 0){
                            answer = temp;
                            break;
                        }

                        if(cmp > 0) break;
                    }
                }

                continue;
            }

            for(int i = 0; i < n; i++){
                if(cur.vis[i]) continue;
                if(tickets[i][0].equals(cur.path.get(cur.path.size() - 1))){
                    Path next = new Path(cur);
                    next.vis[i] = true;
                    next.path.add(tickets[i][1]);
                    q.add(next);
                }
            }
        }

        return answer;
    }
}