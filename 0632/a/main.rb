N, P = gets.split.map(&:to_i)
A = N.times.map{ gets.chomp }

x = A.reverse.reduce(0){|acc, a| (a == 'half') ? (acc * 2) : (acc * 2 + 1)}

ans = 0
N.times do 
  if x % 2 == 0
    ans += P * (x/2)
  else
    ans += P * (x/2) + P/2
  end
  x /= 2
end

puts ans