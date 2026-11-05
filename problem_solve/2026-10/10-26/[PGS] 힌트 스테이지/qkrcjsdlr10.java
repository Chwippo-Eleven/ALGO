import java.util.*;

class Solution {

    ArrayList<Hint> h;
    int n = 0;
    int m = 0;

    int mn = Integer.MAX_VALUE;

    class Hint {
        int cost;
        List<Integer> stages;

        Hint(int cost, List<Integer> stages) {
            this.cost = cost;
            this.stages = stages;
        }
    }

    public void dfs(int depth, List<Hint> h, List<Hint> temp, int[][] cost, int sum){
        if(depth == m){
            for(int i = 0; i < n; i++){
                int cnt = 0;
                for(int j = 0; j < temp.size(); j++){
                    for(int stage : temp.get(j).stages){
                        if(stage == i + 1){
                            cnt++;
                        }
                    }
                }

                if(cnt >= cost[i].length){
                    int a = cost[i].length;
                    sum += cost[i][a - 1];
                }else{
                    if(cnt == 0){
                        sum += cost[i][0];
                    }else{
                        sum += cost[i][cnt];
                    }
                }
            }

            mn = Math.min(mn, sum);
            return;
        }

        dfs(depth + 1, h, temp, cost, sum);

        temp.add(h.get(depth));
        dfs(depth + 1, h, temp, cost, sum + h.get(depth).cost);
        temp.remove(temp.size() - 1);
    }

    public int solution(int[][] cost, int[][] hint) {
        int answer = 0;

        n = cost.length;
        m = hint.length;
        h = new ArrayList<>();


        for(int i = 0; i < hint.length; i++){
            List<Integer> temp = new ArrayList<>();
            for(int j = 1; j < hint[i].length; j++){
                temp.add(hint[i][j]);
            }
            h.add(new Hint(hint[i][0], temp));
        }

        dfs(0, h, new ArrayList<>(), cost, 0);
        answer = mn;
        return answer;
    }
}