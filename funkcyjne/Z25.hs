module Z25 where
qs :: Ord a => [a] -> [a]
qs [] = []
qs [x] = [x]  -- Nowy przypadek bazowy dla list jednoelementowych
qs (x:xs) = qs lesser ++ [x] ++ qs greater
  where
    (lesser, greater) = myPartition (<=x) xs

myPartition :: (a -> Bool) -> [a] -> ([a], [a])
myPartition _ [] = ([],[])
myPartition p (x:xs)
    |p x = (x:ys, ns)
    |otherwise = (ys, x:ns)
    where (ys, ns) = myPartition p xs
