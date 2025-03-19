{-konwertowanie Int i Integer na Float i Double-}

intToFloat :: Int -> Float
intToFloat = fromIntegral

intToDouble :: Int -> Double
intToDouble = fromIntegral

integerToFloat :: Integer -> Float
integerToFloat = fromIntegral

integerToDouble :: Integer -> Double
integerToDouble = fromIntegral

{- konwersja Float, Double na Int, Integer-}
{- Są różne rodzaje funkcji: round, floor, ceiling, truncate-}

roundDoubleToInt :: Double -> Int
roundDoubleToInt = round

floorDoubleToInt :: Double -> Int
floorDoubleToInt = floor

ceilingDoubleToInt :: Double -> Int
ceilingDoubleToInt = ceiling

truncateDoubleToInt :: Double -> Int
truncateDoubleToInt = truncate

{- fukcja realToFrac służy do konwersji między typami rzeczywistymi-}


