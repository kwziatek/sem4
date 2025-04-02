module Z28 where
import Z25
optimalSort :: (Ord a) => [a] -> [a]
optimalSort xs
    |length xs <= 10 = insertionSort xs
    |otherwise = qs xs

insertionSort :: (Ord a) => [a] -> [a]
insertionSort [] = []
insertionSort (x:xs) = linearInsert x (insertionSort xs)

linearInsert :: (Ord a) => a -> [a] -> [a]
linearInsert x [] = [x]
linearInsert x (y:xs)
    |x > y = y:linearInsert x xs
    |otherwise = x:y:xs

