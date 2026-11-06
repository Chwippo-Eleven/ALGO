class Solution {
    int max = Integer.MIN_VALUE;
    int[] answer = { -1 };

    public int[] solution(int n, int[] info) { // n: 화살 수
        int[] result = new int[11]; // 라이언용

        dfs(0, n, info, new int[11]);
        return answer;
    }

    // 조합
    private void dfs(int depth, int n, int[] info, int[] result) {
        // 종료 조건
        if (depth >= 11) {
            if (n > 0)
                result[10] += n; // 남은 화살 처리

            int r_score = 0, a_score = 0;
            // 점수 계산 (마지막에 한꺼번에 !)
            for (int i = 0; i < 11; i++) {
                if (info[i] == 0 && result[i] == 0)
                    continue;
                if (info[i] >= result[i])
                    a_score += 10 - i;
                else
                    r_score += 10 - i;
            }

            int diff = r_score - a_score;
            if (diff > 0) {
                if (diff > max) {
                    max = diff;
                    answer = result.clone(); // ^^배열끼리^^ 복사해서 값 넣기
                } else if (diff == max) {
                    // 낮은 점수 우선 비교
                    if (isBetter(result, answer)) {
                        answer = result.clone(); // ★새롭게 알게됨: 배열 복사
                    }
                }
            }
            if (n > 0)
                result[10] -= n; // 원상복귀(백트래킹)
            return;
        }

        // 이길 수 있다면
        if (n >= info[depth] + 1) {
            result[depth] = info[depth] + 1;
            dfs(depth + 1, n - (info[depth] + 1), info, result);
            result[depth] = 0; // 백트래킹
        }
        // 지는 경우 (gg)
        // else로 안 뺀 이유는 ^^모든 분기를 탐색^^해야되기 때문이다. (이기는 분기는 가능할때만 의미가 있으므로 조건문으로 사전 검증)
        result[depth] = 0;
        dfs(depth + 1, n, info, result);

    }

    // 동점일 경우, 낮은 점수 칸에 더 많이 쓴 배열 채택
    private boolean isBetter(int[] cur, int[] best) {
        for (int i = 10; i >= 0; i--) {
            if (cur[i] != best[i]) {
                return cur[i] > best[i]; // true면 result로 갱신하라는 뜻
            }
        }
        return false;
    }
}
