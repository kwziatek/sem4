add :: (Int, Int) -> Int
add = \(x,y) -> x + y
switch :: (Int, Int) -> (Int, Int)
switch = \(x,y) -> (x * 2, y * 2)
add_pairs :: (Int, Int) -> (Int, Int) -> (Int, Int)
add_pairs = \(x,y) (a, b) -> (x + a, y + b)


