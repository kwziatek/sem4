inits'' :: [a] -> [[a]]
inits'' [] = [[]]
inits'' xs = [] : [take n xs | n <- [1..length xs]]

tails'' :: [a] -> [[a]]
tails'' [] = [[]]
tails'' xs = map reverse (inits''(reverse xs))

diff_tails :: [a] -> [[a]]
diff_tails [] = [[]]
diff_tails (x:xs) = (x:xs) : diff_tails xs

tails_rec :: [a] -> [[a]]
tails_rec xs = reverse (diff_tails xs)
