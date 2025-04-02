dcp :: Int -> Double

dcp n = let pairs = [(k,l) | k <- [1..n], l <- [1..n], gcd k l == 1] in fromIntegral(length pairs) / fromIntegral(n ^ 2)
