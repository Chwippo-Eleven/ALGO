def solution(routes):
    routes.sort(key=lambda x: x[1])

    answer = 0
    camera = -30001

    for start, end in routes:
        if camera < start:
            camera = end
            answer += 1

    return answer
