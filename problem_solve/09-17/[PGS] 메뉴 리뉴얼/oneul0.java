import java.util.*;
class Solution {
    class Sets implements Comparable<Sets> {
        int count;
        String str;
        Sets(int count, String str){
            this.count = count;
            this.str = str;
        }
        @Override
        public int compareTo(Sets o){
            return o.count - this.count;
        }
    }
    List<Character> food = new ArrayList<>();
    Set<String> set = new HashSet<>();
    List<Sets> curList;
    String[] orders;
    public String[] solution(String[] orders, int[] course) {
        this.orders = orders;
        for(String o : orders){
            for(char c : o.toCharArray()){
                if(food.indexOf(c) == -1)
                    food.add(c);
            }
        }
        Collections.sort(food);
        this.orders = orders;
        for(int i = 0; i<course.length; i++){
            curList = new ArrayList<>();
            comb(new StringBuilder(), course[i], 0);
            Collections.sort(curList);
            if(curList.isEmpty()) continue;
            int max = curList.get(0).count;

            for(Sets s : curList){
                if(s.count != max) break;
                set.add(s.str);
            }
        }
        int idx = 0;
        String[] answer = new String[set.size()];
        for(String str : set){
            answer[idx++] = str;
        }
        Arrays.sort(answer);
        return answer;
    }
    public void comb(StringBuilder sb, int len, int start){
        if(sb.length() == len){
            int count = 0;

            for(String order : orders){
                boolean contains = true;

                for(int i = 0; i < sb.length(); i++){
                    if(order.indexOf(sb.charAt(i)) == -1){
                        contains = false;
                        break;
                    }
                }

                if(contains) count++;
            }

            if(count >= 2){
                curList.add(new Sets(count, sb.toString()));
            }

            return;
        }

        for(int i = start; i<food.size(); i++){
            sb.append(food.get(i));
            comb(sb, len, i+1);
            sb.deleteCharAt(sb.length()-1);
        }
    }
}