module Z36 where
import Data.List
reverse' :: [a] -> [a]
reverse' xs = go xs []
    where
        go [] acc = acc
        go (x:xs) acc = go xs (x:acc)

foldReverse :: [a] -> [a]
foldReverse = foldl' (\acc x -> x:acc) []

{-Dołączanie ostatniego elememtu do akumulatora ma stałą złożoność czasową, dołączamy n elementów, gdzie n do długość listy zatem O(n), algorytm z zadania 29 działał w O(n^2)-}
