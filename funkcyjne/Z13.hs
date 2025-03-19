phi :: Int -> Int
phi n = length [k | k <- [1..n], gcd k n == 1]

sum_of_phi :: Int -> Int
sum_of_phi n = sum [phi k | k <- [1..n], n mod k == 0]

sum_phi n = n {-z jakiejś tam właśności matematycznej -}


