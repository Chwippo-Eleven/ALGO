import java.util.*;

// LRU => 가장 오랫동안 참조되지 않은 페이지를 교체
class Solution {
    public int solution(int cacheSize, String[] cities) {
        int answer = 0;
        List<String> list = new ArrayList<>();
        int size = 0; // 현재 캐시 사이즈 카운트
        for (String st : cities){
            st = st.toUpperCase(); // 대소문자 구분 안한다고 하여 -> 대문자로 다 바꿔버리기
            if (list.contains(st)){ // hit = 1
                answer = answer + 1;
                list.remove(st); //지움
                list.add(st); // 마지막에 넣어줌(최근)
            }
            else{ // miss = 5
                answer = answer + 5;
                if (size != cacheSize){ // 사이즈가 꽉 안 차있으면
                    list.add(st); // 마지막에 넣어줌(최근)
                    size++; // 현재사이즈업
                }
                else{ // 사이즈가 캐시만큼 채워져있다면
                    if (size == cacheSize)
                        // 단. 캐시사이즈가 0일때 분리
                        if (size == 0){
                            continue;
                        }
                        else{ // 캐시최대가 0이 아니고, 꽉 찼을때
                            list.remove(0); //가장 오래 안 쓴 맨 앞 제거
                            list.add(st); // 마지막에 넣어줌(최근)
                        }
                }
            }
        }
        
        
        return answer;
    }
}
