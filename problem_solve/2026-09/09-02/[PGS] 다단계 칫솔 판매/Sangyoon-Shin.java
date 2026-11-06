import java.util.*;

class Solution {
    static HashMap<String, Integer> nameToIdx;
    static int[] res;
    static int n;
    static ArrayList<Integer>[] g;
    public int[] solution(String[] enroll, String[] referral, String[] seller, int[] amount) {

        n = enroll.length;
        res = new int[n]; // 정답 담을 배열
        nameToIdx = new HashMap<>();
        // referral은 center을 제외한 직원들만 있음.
        g = new ArrayList[n];

        for (int i = 0; i < n; i++){
            g[i] = new ArrayList<>();
        }

        for (int i = 0; i < n; i++){
            nameToIdx.put(enroll[i], i);
        }

        // 이제 referral 순회하면서 연결해주면 됨. 양방향 x. 내 부모만 알면 된다. 위로 올라가면서 계산하면 되니까.
        for (int i = 0; i < n; i++){
            if (referral[i].equals("-")){
                continue;
            } else {
                int parent = nameToIdx.get(referral[i]);
                int child = i;
                g[child].add(parent);
            }
        }


        for (int i = 0; i < seller.length; i++){
            int start = nameToIdx.get(seller[i]); // 시작점으로부터 올라가면서 계산해야함.
            int value = 100 * amount[i];
            dfs(start, value);
        }

        return res;
    }
    public static void dfs(int start, int value){

        // 여기서 진짜 좀 헤맸다;;
        // 현재 금액에서 90%를 내가 먼저 가져가는게 아니라, 10% 절사한 금액을 먼저 위로 보내고, 남은걸 내가 가져와야 함.

        int remain = value / 10;
        int mine = value - remain;

        res[start] += mine;

        if (remain == 0){ // 남은 금액 없으면 끝
            return;
        }

        if (g[start].size() != 0){ // 더 올라갈 부모 노드가 없어도 끝
            dfs(g[start].get(0), remain);
        }

    }
}