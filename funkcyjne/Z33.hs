subCard :: Int -> [a] -> [[a]]
subCard k _ | k < 0 = []
subCard 0 _ = [[]]
subCard _ [] = []
subCard k (x:xs) = map (x:) (subCard (k-1) xs) ++ subCard k xs

