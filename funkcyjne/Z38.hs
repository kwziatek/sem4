module Z38 where
dec2Int :: [Int] -> Int
dec2Int xs = foldl (\acc x -> acc * 10 + x) 0 xs
