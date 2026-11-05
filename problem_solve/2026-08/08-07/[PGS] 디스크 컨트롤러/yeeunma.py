import heapq

def solution(jobs):
    jobs.sort()  # 도착 시간 기준 정렬

    heap = []
    time = 0          # 현재 시간
    idx = 0           # 아직 힙에 넣지 않은 작업의 인덱스
    answer = 0        # 총 반환 시간
    count = 0         # 처리한 작업 개수
    n = len(jobs)

    while count < n:

        # 현재 시간까지 도착한 작업들을 모두 힙에 넣기
        while idx < n and jobs[idx][0] <= time:
            request, duration = jobs[idx]
            heapq.heappush(heap, (duration, request))
            idx += 1

        if heap:
            # 작업 시간이 가장 짧은 작업 선택
            duration, request = heapq.heappop(heap)

            time += duration
            answer += time - request
            count += 1
        else:
            # 아직 도착한 작업이 없다면 다음 작업 도착 시간으로 이동
            time = jobs[idx][0]

    return answer // n
