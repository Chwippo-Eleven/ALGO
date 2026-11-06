def solution(key, lock):
    m = len(key)
    n = len(lock)

    padding = m - 1
    size = n + padding * 2

    # 자물쇠 크기 확장
    # 하나만 걸쳐도 걸치는걸로 계산할 수 있도록
    board = [[0] * size for _ in range(size)]

    # lock을 가운데에 복사
    for i in range(n):
        for j in range(n):
            board[padding + i][padding + j] = lock[i][j]

    def rotate(arr):
        m = len(arr)
        result = [[0] * m for _ in range(m)]

        for i in range(m):
            for j in range(m):
                result[j][m - 1 - i] = arr[i][j]

        return result

    def is_open():
        for i in range(padding, padding + n):
            for j in range(padding, padding + n):
                if board[i][j] != 1:
                    return False
        return True

    for _ in range(4):

        # key의 왼쪽 위 위치
        for x in range(size - m + 1):
            for y in range(size - m + 1):

                # key 올리기
                for i in range(m):
                    for j in range(m):
                        board[x + i][y + j] += key[i][j]

                if is_open():
                    return True

                # 원복
                for i in range(m):
                    for j in range(m):
                        board[x + i][y + j] -= key[i][j]

        key = rotate(key)

    return False
