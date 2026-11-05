T = int(input())

for test_case in range(1, T + 1):
    N, M = map(int, input().split())

    A = set(map(int, input().split()))
    B = set(map(int, input().split()))

    if A == B:
        print("=")
    elif A < B:
        print("<")
    elif A > B:
        print(">")
    else:
        print("?")
