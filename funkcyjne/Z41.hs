remdupl :: (Ord a) => [a] -> [a]
remdupl  = reverse . foldl f []
    where
        f acc x
            |null acc = [x]
            |x == head acc = acc
            |otherwise = x: acc
