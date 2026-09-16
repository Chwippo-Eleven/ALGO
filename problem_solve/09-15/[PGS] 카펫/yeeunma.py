def solution(brown, yellow):
    xy = brown+yellow
    
    answer = []
    
    for i in range(1,xy+1):
        v_div = xy/i
        if xy%i==0 and xy/i==(brown/2+2)-i:
            answer.append(xy/i)
    if len(answer)==1:
        answer.append(xy/answer[0])

    return sorted(answer,reverse=True)
