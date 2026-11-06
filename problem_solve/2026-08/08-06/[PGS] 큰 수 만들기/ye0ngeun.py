def solution(number, k):
    # 지금까지 확인한 숫자 중 아직 남아 있는 숫자
    result = []
    
    for digit in number:
        # 현재 숫자보다 바로 앞 숫자가 작으면 제거
        while result and k > 0 and result[-1] < digit:
            result.pop()
            k -= 1
        result.append(digit)
        
    # 내림차순이라 삭제하지 못 한 개수가 남으면 뒤에서부터 k개 제거
    if k > 0:
        result = result[:-k]

    return ''.join(result)
