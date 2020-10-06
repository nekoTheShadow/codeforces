def print_and_exit(v)
  puts v
  exit 0
end

h1, h2 = gets.split.map(&:to_i)
a, b = gets.split.map(&:to_i)

print_and_exit(0)  if h1 + 8*a >= h2
print_and_exit(-1) if a <= b

p (h2 - h1 - 8 * a).quo(12 * (a - b)).ceil