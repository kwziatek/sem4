{- file = Z1.hs}
    author = KWz
    date = 11.03.2025
    -}
    module Z1 where
power :: Int -> Int -> Int
power x y = y ^ x

p2 = power 4 {-\y -> y ^ 4-}
p3 = power 3 {-\y -> y ^ 3-}

{- "." między funkcjami oznacza ich kompozycję. (p2 . p3) 2 <==> p2 (p3 2), p3 2 = 8, p2 8 = 4096 -}

    {- >ghci :t p2
    p2 :: Int -> Int
    >ghci :t p3
    p3 :: Int -> Int
    >ghci :t (p2 . p3)
    (p2 . p3) :: Int -> Int-}

    {-\y -> y ^ 4-}
    {-\y -> y ^ 3-}
    {-\y -> p2 (p3 y)}


