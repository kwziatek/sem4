mySum :: [Int] -> Int
mySum [] = 0
mySum xs = snd (foldl f (0,0) xs)
    where
        f (sign, acc) x
          |sign == 0 = (1, acc + x)
          |otherwise = (0, acc - x)

