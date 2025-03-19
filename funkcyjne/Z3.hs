f :: Int -> Int
f x = x ^ 2

g :: Int -> Int -> Int
g x y = x + 2 * y

h :: Int -> Int -> Int {- 2 Int input, 1 int output-}
h x y = f (g x y) {- (x + 2 * y) ^ 2}

{- 2. Nie, funkcja g jest curried co znaczy, że po przyjęciu jedno argumentu zwróci funkcję, funkcja f nie może przyjąć funkcji, zatem nieprawdą jest, że h = f . g -}

{- 3. f (g x) jest błędem z tego samego powodu, zatem h x i f (g x ) nie są tym samym -}


