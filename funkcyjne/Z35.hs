import Data.List
xs = [1..200000000]

{-różnica między foldl1 i fold polega na tym, że fold1 może być zastosowania jedynie do niepustych struktur-}

{- foldl, foldr (1,2)s, jednakże foldr jest szybszy, podobne wyniki z foldl1, foldr1 -}

{-sum xs daje czas 0,1 s, po wcześniejszym puszczeniu foldów na liście, przy reloadzie i wywołaniu sumy jako pierwszej instrukcji zajmuje podobną ilość czasu co foldy-}

{- dla dużych list foldy zwracają stack overflow, a sum liczy do końca 20s -}

{- foldl' działa w podobnym czasie co sum i zwraca poprawny wynik, foldr' nie istnieje, bo nie da się nie ewaluować przed wyliczaniem konkretnych wyrażeń idąc od prawej strony -}
