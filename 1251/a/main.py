T = int(input())
for _ in range(T):
    s = input()
    t = [s[0]]
    for i in range(1, len(s)):
        if t[-1][0] == s[i]:
            t[-1] += s[i]
        else:
            t.append(s[i])
    
    # 正常に動いている場合True
    d = {k : False for k in s}
    for k in t:
        if len(k) % 2 != 0:
            d[k[0]] = True

    ans = [k for k in d if d[k]]
    ans.sort()
    print(''.join(ans))