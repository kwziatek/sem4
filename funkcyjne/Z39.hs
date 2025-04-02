-- foldl (-) e xs jest równoważne ((((e - x1) - x2) - x3) ... -xn), co przekształca się w e - x1 - x2 - x3 - ... - xn = e - sum xs


-- foldr (-) e xs jest równoważne (x1 - (x2 - (x3 -) ... -(xn - e))), co przekształca się w (w zależności od parzystości n) x1 + x3 + x5 + ... + xn - (x2 + x4 + .. + xn-1)

