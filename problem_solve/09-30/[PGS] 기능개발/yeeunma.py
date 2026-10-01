from collections import deque

def solution(progresses, speeds):
    queue_prog = deque()
    queue_speed = deque()
    
    answer = []
    
    for i in range(len(progresses)):
        queue_prog.append(progresses[i])
        queue_speed.append(speeds[i])
        
        
    while len(queue_prog)!=0:
        for i in range(len(queue_prog)):
            queue_prog[i] += queue_speed[i]
        if queue_prog[0] >= 100:
            cnt = 0
            while queue_prog and queue_prog[0]>=100:
                queue_prog.popleft()
                queue_speed.popleft()
                cnt += 1
            if cnt != 0:
                answer.append(cnt)
            
    return answer
