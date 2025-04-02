import Data.List
longestCommonPrefix :: [[Char]] -> [Char]
longestCommonPrefix [] = []
longestCommonPrefix xs = linearCheck(transpose xs)

linearCheck :: [[Char]] -> [Char]
linearCheck [] = []
linearCheck (x:xs)
    |p x = head x : linearCheck xs
    |otherwise = []

p :: [Char] -> Bool
p [] = True
p (x:xs) = all (==x) xs

myLongestCommonPrefix :: [[Char]] -> [Char]
myLongestCommonPrefix [] = []
myLongestCommonPrefix strs = map head (takeWhile allEqual (transpose strs))
  where allEqual :: [Char] -> Bool
        allEqual []     = True
        allEqual (x:xs) = all (== x) xs
