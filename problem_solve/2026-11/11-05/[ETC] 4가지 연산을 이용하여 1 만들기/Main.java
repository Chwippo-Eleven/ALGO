import java.util.*;
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        Queue<int[]> q = new ArrayDeque<>();
        Set<Integer> visited = new HashSet<>();
        q.offer(new int[]{n, 0});
        visited.add(n);
        int answer = 0;
        while(!q.isEmpty()){
            int[] cur = q.poll();
            
            if(cur[0] == 1) {
                answer = cur[1];
                break;
            }
            if(!visited.contains(cur[0]+1)) {
                q.offer(new int[]{cur[0]+1, cur[1]+1});
                visited.add(cur[0]+1);
            }
            if(!visited.contains(cur[0]-1)) {
                q.offer(new int[]{cur[0]-1, cur[1]+1});
                visited.add(cur[0]-1);
            }
            if(cur[0]%2==0 && !visited.contains(cur[0]/2)) {
                q.offer(new int[]{cur[0]/2, cur[1]+1});
                visited.add(cur[0]/2);
            }
            if(cur[0]%3==0 && !visited.contains(cur[0]/3)) {
                q.offer(new int[]{cur[0]/3, cur[1]+1});
                visited.add(cur[0]/3);
            }
        }
        System.out.print(answer);

    }
}

