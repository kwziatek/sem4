bubbleSort :: (Ord a) => [a] -> [a]
bubbleSort xs = if isSorted xs == True
                then xs
                else bubbleSort(linearSwap xs)

linearSwap :: (Ord a) => [a] -> [a]
linearSwap [] = []
linearSwap [x] = [x]
linearSwap (x:y:xs)
    |x <= y = x:linearSwap(y:xs)
    |otherwise = y:linearSwap(x:xs)

isSorted :: (Ord a) => [a] -> Bool
isSorted [] = True
isSorted [x] = True
isSorted (x:y:xs) = x<= y && isSorted(y:xs)
