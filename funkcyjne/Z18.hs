inits :: [a] -> [[a]]
inits [] = [[]]
inits xs = [] : [take n xs | n <- [1..length xs]]
