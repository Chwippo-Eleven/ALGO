from collections import deque

def solution(queue1, queue2):
    answer = 0
    queue1 = deque(queue1)
    queue2 = deque(queue2)
    qmax = max(max(queue1),max(queue2))
    sum1 = sum(queue1)
    sum2 = sum(queue2)
    qsum = sum1+sum2
    
    limit = (len(queue1)+len(queue2))*2
    if qsum%2 != 0 or qmax>(qsum/2):
        return -1
    while answer<limit:
        if sum1 > sum2:
            a = queue1.popleft()
            queue2.append(a)
            sum1 -= a
            sum2 += a
        elif q1sum < q2sum:
            b = queue2.popleft()
            queue1.append(b)
            sum2 -= b
            sum1 += b
        else:
            return answer
        answer += 1
    return -1
