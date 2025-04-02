

myInsertion :: a -> [a] -> [[a]]
myInsertion x [] = [[x]]
myInsertion x (y:ys) = (x : y : ys) : map (y :) (myInsertion x ys)

myPermutations :: [a] -> [[a]]
myPermutations [] = [[]]
myPermutations (x:xs) = [zs| ys <- myPermutations xs, zs <- myInsertion x ys]
