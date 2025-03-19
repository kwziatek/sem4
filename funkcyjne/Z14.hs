sum_of_div :: Int -> Bool
sum_of_div n = n == (sum [d| d <- [1..(n - 1)], mod n d == 0])

perfect_numbers n = filter sum_of_div [1..n]
