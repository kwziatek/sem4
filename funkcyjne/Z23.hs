

myInsertion :: a -> [a] -> [[a]]
myInsertion x [] = [[x]]
myInsertion x (y:ys) = (x : y : ys) : map (y :) (myInsertion x ys)

myPermutations :: [Int] -> [[Int]]
myPermutations [] = [[]]
myPermutations (x:xs) = [zs| ys <- myPermutations xs, zs <- myInsertion x ys]

mySolution :: [Int] -> [[Int]]
mySolution [] = [[]]
mySolution xs = filter checkList (myPermutations xs)

checkList :: [Int] -> Bool
checkList xs = all (\(a, b, c, d) -> abs (a - c) /= abs (b - d)) pairs
  where
    indexed = zip [0..] xs          -- Lista par (indeks, wartość)
    pairs = [ (i, x, j, y) | (i, x) <- indexed, (j, y) <- indexed, i < j ]

mySolutionB :: [Int] -> Int
mySolutionB [] = 0
mySolutionB xs = length(mySolution xs)

