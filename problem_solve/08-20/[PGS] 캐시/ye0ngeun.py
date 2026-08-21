from collections import deque

def solution(cacheSize, cities):
    answer = 0
    cache = deque()

    for city in cities:
        city = city.lower() # 대소문자 구분 x

        # Cache Hit
        if city in cache:
            answer += 1

            # 방금 사용했으므로 가장 최근 위치로 이동
            cache.remove(city)
            cache.append(city)

        # Cache Miss
        else:
            answer += 5

            # 캐시 크기가 0이면 저장할 수 없음
            if cacheSize == 0:
                continue

            # 캐시가 꽉 찼다면 가장 오래된 도시 제거
            if len(cache) == cacheSize:
                cache.popleft()

            # 새로운 도시를 가장 최근 위치에 추가
            cache.append(city)

    return answer
