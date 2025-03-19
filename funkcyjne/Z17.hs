nub' :: [Int] -> [Int]
nub' [] = []
nub' (x:xs) = x:nub'(filter (/= x) xs)
