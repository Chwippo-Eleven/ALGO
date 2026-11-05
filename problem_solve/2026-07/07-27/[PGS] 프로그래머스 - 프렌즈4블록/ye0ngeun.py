def solution(m, n, board):
    answer = 0
    board = [list(row) for row in board]
    
    while True:
        # 이번 턴에 삭제할 블록 좌표 저장
        remove = set()
        
        for i in range(m - 1):
            for j in range(n - 1):
                now = board[i][j]
                
                # 이미 빈칸이면 제외
                if now == "0":
                    continue
                
                # 같은 문자로 이루어진 2*2 블록인지 확인
                if (
                    now == board[i][j + 1]
                    and now == board[i + 1][j + 1]
                    and now == board[i + 1][j]
                ):
                    remove.add((i, j))
                    remove.add((i, j + 1))
                    remove.add((i + 1, j + 1))
                    remove.add((i + 1, j))

        if not remove:
            break
            
        answer += len(remove)
        
        # 삭제 대상 블록을 빈칸으로 변경
        for i, j in remove:
            board[i][j] = "0"
        
        # 중력 구현
        for col in range(n):
            write_row = m - 1
            
            for read_row in range(m - 1, -1, -1):
                if board[read_row][col] == "0":
                    continue
                
                if write_row != read_row:
                    board[write_row][col] = board[read_row][col]
                    board[read_row][col] = "0"
                    
                write_row -= 1
        
    return answer
