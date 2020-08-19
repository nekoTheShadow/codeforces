n = int(input())
p = list(map(int, input().split()))
s = input()

ans = bob1 = bob2 = 0
for i in range(n):
    if s[i] == 'B':
        ans += p[i]
        bob1 += p[i]
        bob2 += p[i]

for i in range(n):
    if s[i] == 'A':
        bob1 += p[i]
    else:
        bob1 -= p[i]
    ans = max(ans, bob1)

for i in reversed(range(n)):
    if s[i] == 'A':
        bob2 += p[i]
    else:
        bob2 -= p[i]
    ans = max(ans, bob2)

print(ans)