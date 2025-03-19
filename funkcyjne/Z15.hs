Pairs l = [(m,n) | m <- [2, l - 1], let n = sumDiv m, n > m, n < l, sumDivn == m]
