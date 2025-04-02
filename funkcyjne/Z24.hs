mySolution :: Integer -> Integer -> Integer
mySolution 0 _= 0
mySolution n m
    |m <= n = div n m + mySolution n (5*m)
    |otherwise = 0

myFact :: Integer -> Integer
myFact 0 = 1
myFact n = n * myFact (n - 1 )
