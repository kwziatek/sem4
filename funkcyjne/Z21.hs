partition :: (a -> Bool) -> [a] -> ([a], [a])
partition p xs = (filter p xs, filter (not.p) xs)

myPartition :: (a -> Bool) -> [a] -> ([a], [a])
myPartition _ [] = ([],[])
myPartition p (x:xs)
    |p x = (x:ys, ns)
    |otherwise = (ys, x:ns)
    where (ys, ns) = myPartition p xs


