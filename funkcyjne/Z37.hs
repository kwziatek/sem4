module Z37 where
f :: [Integer] -> [Integer]
f [] = []
f (x:xs)
    |even x = x : f xs
    |otherwise = f xs


solution :: [Integer] -> Int
solution [] = 0
solution xs = length (foldF xs)
    where
    foldF xs = foldr (\x acc -> if even x then x:acc else acc) [] xs
