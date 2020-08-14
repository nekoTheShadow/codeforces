t = int(input())
for _ in range(t):
    n = int(input())
    if n == 3:
        print(7)
    else:
        if n % 2 == 0:
            print('1'*(n//2))
        else:
            print('7' + '1'*(n//2-1))