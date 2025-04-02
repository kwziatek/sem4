approx :: Int -> Double
approx n = foldr (\k acc -> acc + 1 / fromIntegral (product [1..k])) 0 [1..n]

