myTakeWhile :: (a -> Bool) -> [a] -> [a]
myTakeWhile _ [] = []
myTakeWhile p (x:xs)
    |p x == True = x:myTakeWhile p xs
    |otherwise = []

myDropWhile :: (a -> Bool) -> [a] -> [a]
myDropWhile _  [] = []
myDropWhile p (x:xs)
    |p x == True = myDropWhile p xs
    |otherwise = x:xs
