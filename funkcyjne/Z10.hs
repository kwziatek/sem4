myMap :: (a -> b) -> [a] -> [b]
myMap _ [] = [] {- oznacza dowolną wartość (w tym przypadku funkcję) -}
myMap f (x:xs) = f x : myMap f xs {- : oznacza łączenie w listę zmapowanej wartości x z resztą listy, która zostanie zmapowana później-}
{- e.g. myMap (\y -> y * 2) [1,2,3]
        myMap (\y -> y + 3) [2,6,3]
-}

{- zip łączy dwie listy w jedną -}
myZip :: [a] -> [b] -> [(a,b)]
myZip [] _ = []
myZip _ [] = []
myZip (x:xs) (y:ys) = (x,y) : myZip xs ys

{- zipWith łączy przy pomocy funkcji dwie listy w jedną-}
myZipWith :: (a -> a -> a) -> [a] -> [a] -> [a]
myZipWith _ [] _ = []
myZipWith _ _ [] = []
myZipWith f (x:xs) (y:ys) = f x y : myZipWith f xs ys

{- filter filtruje liste i tworzy kolejną zawierającą jedynie te elementy, które spełniają warunek funkcji f-}
myFilter :: (a -> Bool) -> [a] ->[a]
myFilter _ [] = []
myFilter f (x:xs)
        |f x = x : myFilter f xs
        |otherwise = myFilter f xs

{- take zwraca pierwszych n elementów z podanej listy-}
myTake :: Int -> [a] -> [a]
myTake _ [] = []
myTake n _ | n <= 0 = []
myTake n (x:xs) = x: myTake (n - 1) xs

{- drop usuwa pierwszych n elementów z podanej listy-}
myDrop :: Int -> [a] -> [a]
myDrop n xs | n <= 0 = xs
myDrop _ [] = []
myDrop n (_:xs) = myDrop (n - 1) xs


