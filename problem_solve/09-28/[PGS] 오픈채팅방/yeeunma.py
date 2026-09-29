def solution(record):
    user = {}
    
    for r in record:
        r = list(r.split(' '))
        if len(r) == 3:
            user[r[1]] = r[2]
    
    answer = []
        
    for r in record:
        r = list(r.split(' '))
        if 'Enter' in r:
            answer.append(f"{user.get(r[1])}님이 들어왔습니다.")
        elif 'Leave' in r:
            answer.append(f"{user.get(r[1])}님이 나갔습니다.")

    return answer
