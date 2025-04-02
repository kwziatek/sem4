myFilter :: (a -> Bool) -> [a] -> [a]
myFilter p = concat . map box
    where
        box x
            |p x = [x]
            |otherwise = []
