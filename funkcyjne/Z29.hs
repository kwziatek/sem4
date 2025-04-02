{-listy w haskellu są jednokierunkowe, więc aby dodać element na koniec listy, trzeba przejść przez całą listę, dodatkowo mamy n wywołań rekurencyjnych, każde z nich zawiera wspomieną wcześniej operację ++-}

{-standardowa funkcja reverse używa akumulatora i ma złożoność O(n), dlatego funkcja dana w zadaniu jest kiepska-}
